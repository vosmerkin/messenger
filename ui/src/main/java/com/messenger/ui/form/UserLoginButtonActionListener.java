package com.messenger.ui.form;

import com.messenger.common.dto.UserDto;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLoginButtonActionListener implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger(UserLoginButtonActionListener.class);
    private final StartForm form;
    private final UiAction uiAction;
    private final JButton userLoginButton;
    private final JTextField userNameTextField;
    private final DefaultListModel<UserDto> contactListModel;

    public UserLoginButtonActionListener(StartForm form) {
        this.form = form;
        uiAction = form.getUiAction();
        userLoginButton = form.getUserLoginButton();
        userNameTextField = form.getUserNameTextField();
        contactListModel = form.getContactListModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                var userLoggedInStatus = form.isUserLoggedInStatus();
                var currentUser = form.getCurrentUser();
                userLoginButton.setEnabled(false);
                if (userLoggedInStatus) {
                    UserDto user = uiAction.userLogOffAction(currentUser);
                    if (user != UserDto.EMPTY_ENTITY) {
                        form.setCurrentUser(currentUser = UserDto.EMPTY_ENTITY);
                        userLoginButton.setText("User Login");
                        userNameTextField.setEnabled(true);
                        form.setUserLoggedInStatus(userLoggedInStatus = false);
                        //clear controls - contactList,msgList, etc
                        contactListModel.removeAllElements();
                    }
                } else {
                    String userName = userNameTextField.getText();
                    form.setCurrentUser(currentUser = uiAction.userLogInAction(userName));
                    if (currentUser != UserDto.EMPTY_ENTITY) {
                        userLoginButton.setText("User Logoff");
                        userNameTextField.setEnabled(false);
                        form.setUserLoggedInStatus(userLoggedInStatus = true);
//                                contactListModel.addAll(currentUser.getContactList());
//                                contactList.setModel(contactListModel);
                        //fill Contact List from UserEntity
                    }
                }
                userLoginButton.setEnabled(true);
                form.changeRoomCreateConnectButtonEnabledState();
                return null;
            }
        }.execute();
    }
}
