package com.messenger.ui.form.listeners;

import com.messenger.common.dto.MessageDto;
import com.messenger.ui.form.StartForm;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Date;

public class SendButtonActionListener implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger(SendButtonActionListener.class);
    private final StartForm form;
    private final UiAction uiAction;

    public SendButtonActionListener(StartForm form) {
        this.form = form;
        uiAction = form.getUiAction();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                var currentUser = form.getCurrentUser();
                var currentRoom = form.getCurrentRoom();
                var messageTextField = form.getMessageTextField();
                if (form.getCurrentUser() != null && form.getCurrentRoom() != null) {
                    MessageDto message = new MessageDto(null, Date.from(Instant.now()), messageTextField.getText(), currentRoom, currentUser);
                    uiAction.sendMessage(message);
                    messageTextField.setText("");
                }
                return null;
            }
        }.execute();
    }
}
