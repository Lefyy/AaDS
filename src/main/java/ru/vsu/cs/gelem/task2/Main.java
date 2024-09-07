package ru.vsu.cs.gelem.task2;

import ru.vsu.cs.gelem.task2.ui.MainWin;
import ru.vsu.cs.gelem.util.SwingUtils;

import java.util.Locale;

public class Main {
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
}
