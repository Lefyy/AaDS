package ru.vsu.cs.gelem.task6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Obfuscator {
    static String[] dataTypes = new String[]{"Integer", "Integer[]", "Integer[][]", "int", "int[]", "int[][]", "Double",
            "Double[]", "Double[][]", "double", "double[]", "double[][]", "String", "String[]", "String[][]", "byte",
            "byte[]", "byte[][]", "Byte", "Byte[]", "Byte[][]", "short", "short[]", "short[][]", "Short", "Short[]", "Short[][]",
            "long", "long[]", "long[][]", "Long", "Long[]", "Long[][]", "float", "float[]", "float[][]", "Float", "Float[]", "Float[][]",
            "boolean", "boolean[]", "boolean[][]", "Boolean", "Boolean[]", "Boolean[][]", "char", "char[]", "char[][]", "Char", "Char[]", "Char[][]"};
    static String[] trash = new String[]{";", ")", "(", "++", "--", ",", "[", "]"};
    static String functionSignature = "[a-z].*\\(.*";

    public static List<String> getCode(String inputFilename) {
        List<String> list = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(inputFilename)) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                list.add(str);
            }
            if (list.isEmpty()) {
                System.err.println("Где текст?????😋😋😋");
                return null;
            } else {
                return list;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Где файл?????😱😱😱");
            return null;
        } catch (IOException e) {
            System.err.println("ЧТО🤕🤕🤕");
            return null;
        }
    }

    public static List<String> obfuscate(List<String> code) {
        List<String> newCode = new ArrayList<>(code);
        Stack<Map<String, String>> visionAreas = new Stack<>();
        Map<String, String> obfValues = new SimpleHashMap<>(1000);
        visionAreas.push(obfValues);
        for (int i = 0; i < newCode.size(); i++) {
            if (newCode.get(i).equals("")) {
                continue;
            }
            String[] line = newCode.get(i).split(" ");
            for (int j = 0; j < line.length; j++) {
                if (j == 0) {
                    if (line[j].matches(functionSignature)) {
                        String[] func = line[j].split("\\(");
                        if (func[0].equals("main")) {
                            visionAreas.push(new HashMap<>(obfValues));
                            break;
                        }
                        if (!visionAreas.peek().containsKey(func[0])) {
                            visionAreas.peek().put(func[0], "v" + visionAreas.peek().size());
                        }
                        func[0] = visionAreas.peek().get(func[0]);
                        if (!checkAd(func[1]) && !isNumberOrString(getClearWord(func[1]))) {
                            if (!visionAreas.peek().containsKey(func[1])) {
                                visionAreas.peek().put(func[1], "v" + visionAreas.peek().size());
                            }
                            func[1] = func[1].replaceFirst(getClearWord(func[1]), visionAreas.peek().get(getClearWord(func[1])));
                        }
                        line[j] = joinStrings(func, "(");
                    } else if (line[j].split("\\(").length > 1) {
                        String[] str = line[j].split("\\(");
                        if (visionAreas.peek().containsKey(getClearWord(str[1]))) {
                            str[1] = str[1].replaceFirst(getClearWord(str[1]), visionAreas.peek().get(getClearWord(str[1])));
                            line[j] = joinStrings(str, "(");
                        }
                    } else if (visionAreas.peek().containsKey(getClearWord(line[j]))) {
                        line[j] = line[j].replaceFirst(getClearWord(line[j]), visionAreas.peek().get(getClearWord(line[j])));
                    } else if (Objects.equals(line[j], "{")) {
                        visionAreas.push(new HashMap<>(obfValues));
                    } else if (Objects.equals(line[j], "}")) {
                        visionAreas.pop();
                    }
                } else {
                    if (getClearWord(line[j]).matches(functionSignature)) {
                        String[] func = line[j].split("\\(");
                        if (func[0].equals("main")) {
                            visionAreas.push(new HashMap<>(obfValues));
                            break;
                        }
                        if (!visionAreas.peek().containsKey(func[0])) {
                            visionAreas.peek().put(func[0], "v" + visionAreas.peek().size());
                        }
                        func[0] = visionAreas.peek().get(func[0]);
                        if (!checkAd(func[1]) && !isNumberOrString(getClearWord(func[1]))) {
                            if (!visionAreas.peek().containsKey(func[1])) {
                                visionAreas.peek().put(func[1], "v" + visionAreas.peek().size());
                            }
                            func[1] = func[1].replaceFirst(getClearWord(func[1]), visionAreas.peek().get(getClearWord(func[1])));
                        } else if (checkAd(func[1])) {
                            visionAreas.peek().put(getClearWord(line[j+1]), "v" + visionAreas.peek().size());

                        }
                        line[j] = joinStrings(func, "(");
                    } else if (checkAd(line[j - 1])) {
                        String word = getClearWord(line[j]);
                        if (!visionAreas.peek().containsKey(word)) {
                            visionAreas.peek().put(word, "v" + visionAreas.peek().size());
                        }
                        String newWord = visionAreas.peek().get(word);
                        line[j] = line[j].replaceFirst(word, newWord);
                    } else if (line[j].split("\\(").length > 1) {
                        String[] str = line[j].split("\\(");
                        if (visionAreas.peek().containsKey(getClearWord(str[1]))) {
                            str[1] = str[1].replaceFirst(getClearWord(str[1]), visionAreas.peek().get(getClearWord(str[1])));
                            line[j] = joinStrings(str, "(");
                        }
                    } else if (visionAreas.peek().containsKey(getClearWord(line[j]))) {
                        line[j] = line[j].replaceFirst(getClearWord(line[j]), visionAreas.peek().get(getClearWord(line[j])));
                    } else if (Objects.equals(line[j], "{")) {
                        visionAreas.push(new HashMap<>(obfValues));
                    } else if (Objects.equals(line[j], "}")) {
                        visionAreas.pop();
                    }
                }
            }
            newCode.set(i, joinStrings(line, " "));
        }
        return newCode;
    }

    private static String getClearWord(String word) {
        StringBuilder builder = new StringBuilder();
        builder.append(word);
        while (isIn(builder.substring(builder.length() - 1, builder.length()), trash)) {
            builder.delete(builder.length() - 1, builder.length());
        }
        while (isIn(builder.substring(0, 1), trash)) {
            builder.delete(0, 1);
        }
        return builder.toString();
    }

    private static boolean isIn(String word, String[] arr) {
        for (String i : arr) {
            if (i.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkAd(String word) {
        return isIn(getClearWord(word), dataTypes);
    }

    private static String joinStrings(String[] word, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (String i : word) {
            joiner.add(i);
        }
        return joiner.toString();
    }

    private static boolean isNumberOrString(String word) {
        if (!word.matches("\".*\"")) {
            try {
                Integer.parseInt(word);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
