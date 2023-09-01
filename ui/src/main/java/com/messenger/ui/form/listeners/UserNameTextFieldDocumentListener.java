package com.messenger.ui.form.listeners;

import com.messenger.ui.form.StartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserNameTextFieldDocumentListener implements DocumentListener {
    private static final Logger LOG = LoggerFactory.getLogger(UserNameTextFieldDocumentListener.class);
    private final StartForm form;

    public UserNameTextFieldDocumentListener(StartForm form) {
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
