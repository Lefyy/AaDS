package ru.vsu.cs.gelem.task2.ui;

import ru.vsu.cs.gelem.task2.Student;
import ru.vsu.cs.gelem.task2.StudentList;
import ru.vsu.cs.gelem.util.ArrayUtils;
import ru.vsu.cs.gelem.util.JTableUtils;
import ru.vsu.cs.gelem.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWin extends JFrame {
    private JPanel MainPanel;
    private JButton ОтсортироватьButton;
    private JButton загрузитьМассивИзФайлаButton;
    private JButton выбратьКудаВыгрузитсяОтветButton;
    private JButton загрузитьМассивВФайлButton;
    private JTable tableInfo;

    public MainWin() {
        this.setTitle("MainWin");
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();


        JTableUtils.initJTableForStudentsArray(tableInfo, 350, false, true, true, false);
        tableInfo.setRowHeight(25);

        JFileChooser fileChooserOpen = new JFileChooser();
        JFileChooser fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        JMenuBar menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        JMenu menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        JTableUtils.writeArrayToJTable(tableInfo, new String[][]{
                {"Гелемеев Илья Николаевич", "1"}
        });

        this.pack();




        загрузитьМассивИзФайлаButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String[] arr = ArrayUtils.readLinesFromFile(fileChooserOpen.getSelectedFile().getPath());
                        String[][] students = new String[arr.length][2];
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < arr.length; i++) {
                            sb.append(arr[i]);
                            students[i][1] = sb.substring(arr[i].length()-1);
                            students[i][0] = sb.substring(0, arr[i].length()-2);
                            sb = new StringBuilder();
                        }
                        JTableUtils.writeArrayToJTable(tableInfo, students);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Неправильный файлик выбран");
                }
            }
        });
        загрузитьМассивВФайлButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String[][] matrix = JTableUtils.readStringMatrixFromJTable(tableInfo);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, matrix);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Что-то не то выбрал");
                }
            }
        });
        выбратьКудаВыгрузитсяОтветButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String[][] students = JTableUtils.readStringMatrixFromJTable(tableInfo);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, students);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Что-то не то выбрал");
                }
            }
        });
        ОтсортироватьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    StudentList students = new StudentList();
                    String[][] studentsStart = JTableUtils.readStringMatrixFromJTable(tableInfo);
                    for (int i = 0; i < studentsStart.length; i++) {
                        Student student = new Student(studentsStart[i][0], Integer.parseInt(studentsStart[i][1]));
                        students.addFirst(student);
                    }
                    students.sort();
                    String[][] studentsEnd = new String[studentsStart.length][2];
                    for (int i = 0; i < students.size(); i++) {
                        studentsEnd[i][1] = Integer.toString(students.get(i).getCourse());
                        studentsEnd[i][0] = students.get(i).getName();
                    }
                    JTableUtils.writeArrayToJTable(tableInfo, studentsEnd);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Неверный ввод");
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
