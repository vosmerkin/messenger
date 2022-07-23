package com.messenger.ui;

import com.messenger.ui.form.StartForm;

import javax.swing.*;

public class ApplicationGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Messenger");
        frame.setContentPane(new StartForm().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}