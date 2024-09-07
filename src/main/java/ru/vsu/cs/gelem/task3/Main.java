package ru.vsu.cs.gelem.task3;

import ru.vsu.cs.gelem.task3.ui.MainWin;
import ru.vsu.cs.gelem.util.SwingUtils;

import java.util.*;

public class Main {
    public static ArrayList<Stack<Position>> options = findOption();

    public static void main(String[] args) throws Exception {
        winMain();
    }

    public static void winMain() throws Exception {
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWin().setVisible(true);
            }
        });

    }

    public static ArrayList<Stack<Position>> findOption() {
        ArrayList<Stack<Position>> allOptions = new ArrayList<>();
        Stack<Position> posStack = new Stack<>();
        ArrayList<Integer> vacantPoses = new ArrayList<>();
        HashSet<Integer> usedBegins = new HashSet<>();
        int count = 0;
        for (int i = 0; i < 64; i++) {
            vacantPoses.add(i);
        }
        for (int i = 0; i < vacantPoses.size() + 1; i++) {
            if (count != 8) {
                Position pos = posStack.isEmpty() ? new Position(vacantPoses.get(i), usedBegins) : new Position(vacantPoses.get(i), posStack.peek().getElimedPoses());
                posStack.push(pos);
                count++;
                vacantPoses.removeAll(pos.getPosToElim());
            }
            i = -1;
            while ((vacantPoses.isEmpty() && count < 8) || (count == 8)) {
                if (count == 8 && !compareMassives(allOptions, posStack)) {
                    Stack<Position> newStack = new Stack<>();
                    for (Position v : posStack) {
                        newStack.push(v);
                    }
                    allOptions.add(newStack);
                    if (allOptions.size() == 92) {
                        return allOptions;
                    }
                    break;
                }
                if (posStack.size() == 1) {
                    vacantPoses.addAll(posStack.peek().getUsedVar());
                    vacantPoses.addAll(posStack.peek().getPosToElim());
                    usedBegins.add(posStack.pop().getPos());
                    vacantPoses.removeAll(usedBegins);
                    count--;
                    break;
                }
                int used = posStack.peek().getPos();
                vacantPoses.addAll(posStack.peek().getUsedVar());
                vacantPoses.addAll(posStack.pop().getPosToElim());
                posStack.peek().addUsedVar(used);
                vacantPoses.removeAll(posStack.peek().getUsedVar());
                vacantPoses.removeAll(posStack.peek().getElimedPoses());
                count--;
            }
        }
        return allOptions;
    }

    public static boolean compareMassives(ArrayList<Stack<Position>> usedOptions, Stack<Position> posStack) {
        if (!usedOptions.isEmpty()) {
            for (Stack<Position> v : usedOptions) {
                if (Arrays.deepEquals(stackToMassive(v), stackToMassive(posStack))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] stackToMassive(Stack<Position> posStack) {
        int[][] poses = new int[8][8];
        for (Position v : posStack) {
            poses[v.getPos() / 8][v.getPos() % 8] = 1;
        }
        return poses;
    }
}
