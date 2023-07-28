package com.messenger.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MessageTextFieldDocumentListener implements DocumentListener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageTextFieldDocumentListener.class);
    private final StartForm form;

    public MessageTextFieldDocumentListener(StartForm form) {
        this.form = form;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        form.changeSendButtonEnabledState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        form.changeSendButtonEnabledState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        form.changeSendButtonEnabledState();
    }
}
