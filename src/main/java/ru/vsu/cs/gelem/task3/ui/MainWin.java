package ru.vsu.cs.gelem.task3.ui;

import ru.vsu.cs.gelem.task3.Main;
import ru.vsu.cs.gelem.task3.Position;
import ru.vsu.cs.gelem.task3.Stack;
import ru.vsu.cs.gelem.util.ArrayUtils;
import ru.vsu.cs.gelem.util.JTableUtils;
import ru.vsu.cs.gelem.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MainWin extends JFrame {
    private JPanel MainPanel;
    private JButton ОтсортироватьButton;
    private JButton загрузитьМассивИзФайлаButton;
    private JButton выбратьКудаВыгрузитсяОтветButton;
    private JButton загрузитьМассивВФайлButton;
    private JTable table;
    private int i = -1;

    public MainWin() {
        this.setTitle("MainWin");
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(table, 40, false, true, false, false);
        table.setRowHeight(40);

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

        JTableUtils.writeArrayToJTable(table, new int[8][8]);

        this.pack();

        загрузитьМассивВФайлButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        int[][] matrix = JTableUtils.readIntMatrixFromJTable(table);
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

        ОтсортироватьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (i == 92) {
                        i = -1;
                    }
                    i++;
                    JTableUtils.writeArrayToJTable(table, Main.stackToMassive(Main.options.get(i)));
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
