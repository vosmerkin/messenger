package com.messenger.ui;

import com.messenger.ui.form.StartForm;
import com.messenger.ui.services.PropertyManager;

import javax.swing.*;

public class ApplicationGUI {

    public static void main(String[] args) {
        //setting address for local testing
        if (args.length > 0 && "local".equals(args[0]))
            PropertyManager.setIsLocalFlag(true);

        JFrame frame = new JFrame("Messenger");
        frame.setContentPane(new StartForm().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 500);


    }
}