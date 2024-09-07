package ru.vsu.cs.gelem.task6.ui;

import ru.vsu.cs.gelem.task6.Obfuscator;
import ru.vsu.cs.gelem.util.ArrayUtils;
import ru.vsu.cs.gelem.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class MainWin extends JFrame {
    private JPanel MainPanel;
    private JButton Find;
    private JButton LoadTextFromFile;
    private JButton ChoosePath;
    private JButton LoadTextToFile;
    private JTextArea DistTextField;
    private JTextArea SourceTextField;

    public MainWin() {
        this.setTitle("MainWin");
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

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

        this.pack();


        LoadTextFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        List<String> text = Obfuscator.getCode(fileChooserOpen.getSelectedFile().getPath());
                        StringBuilder builder = new StringBuilder();
                        assert text != null;
                        for (String i : text) {
                            builder.append(i).append(System.lineSeparator());
                        }
                        SourceTextField.setText(builder.toString());
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Неправильный файлик выбран");
                }
            }
        });
        LoadTextToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String text = DistTextField.getText();
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeTextToFile(file, text);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Что-то не то выбрал");
                }
            }
        });
        ChoosePath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(MainPanel) == JFileChooser.APPROVE_OPTION) {
                        String text = DistTextField.getText();
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeTextToFile(file, text);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox("Что-то не то выбрал");
                }
            }
        });
        Find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String text = SourceTextField.getText();
                    List<String> list = List.of(text.split(System.lineSeparator()));
                    list = Obfuscator.obfuscate(list);
                    StringBuilder builder = new StringBuilder();
                    for (String i : list) {
                        builder.append(i).append(System.lineSeparator());
                    }
                    DistTextField.setText(builder.toString());
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
