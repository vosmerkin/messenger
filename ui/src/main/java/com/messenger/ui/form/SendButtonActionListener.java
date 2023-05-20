package com.messenger.ui.form;

import com.messenger.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Date;

public class SendButtonActionListener implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger(SendButtonActionListener.class);
    private StartForm form;

    public SendButtonActionListener(StartForm form) {
        this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                var currentUser = form.getCurrentUser();
                var currentRoom = form.getCurrentRoom();
                var messageTextField = form.getMessageTextField();
                var uiAction = form.getUiAction();
                var messageListWorker = form.getMessageListWorker();

                if (form.getCurrentUser() != null && form.getCurrentRoom() != null) {
                    MessageDto message = new MessageDto(null, Date.from(Instant.now()), messageTextField.getText(), currentRoom, currentUser);
                    uiAction.sendMessage(message);

                    //run messageListUpdaterSwingWorker
                    messageListWorker.execute();

                    messageTextField.setText("");
                }
                return null;
            }
        }.execute();
    }
}
