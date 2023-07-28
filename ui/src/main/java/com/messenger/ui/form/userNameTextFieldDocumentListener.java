package com.messenger.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class userNameTextFieldDocumentListener implements DocumentListener {
    private static final Logger LOG = LoggerFactory.getLogger(userNameTextFieldDocumentListener.class);
    private final StartForm form;

    public userNameTextFieldDocumentListener(StartForm form) {
        this.form = form;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        form.changeUserLoginButtonEnabledState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        form.changeUserLoginButtonEnabledState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        form.changeUserLoginButtonEnabledState();
    }

}
