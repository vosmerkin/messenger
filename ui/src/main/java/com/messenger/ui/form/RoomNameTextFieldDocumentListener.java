package com.messenger.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RoomNameTextFieldDocumentListener implements DocumentListener {
    private static final Logger LOG = LoggerFactory.getLogger(RoomNameTextFieldDocumentListener.class);
    private final StartForm form;

    public RoomNameTextFieldDocumentListener(StartForm form) {
        this.form = form;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        form.changeRoomCreateConnectButtonEnabledState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        form.changeRoomCreateConnectButtonEnabledState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        form.changeRoomCreateConnectButtonEnabledState();
    }
}
