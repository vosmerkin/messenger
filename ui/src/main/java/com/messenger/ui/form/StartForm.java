package com.messenger.ui.form;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import com.messenger.ui.services.HttpBackendClient;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm {
    private static final Logger log = LoggerFactory.getLogger(StartForm.class);

    private DefaultListModel<UserDto> contactListModel = new DefaultListModel<UserDto>();

    private DefaultListModel<UserDto> roomUserListModel = new DefaultListModel<UserDto>();

    private HttpBackendClient backendClient;
    private UiAction uiAction = new UiAction();
    private UserDto currentUser;
    private RoomDto currentRoom;
    private JButton sendButton;
    private JTextField messageTextField;
    private JPanel mainPanel;
    private JTextField roomChatTextField;
    private JList roomUserList;
    private JButton userLoginButton;
    private JList contactList;
    private JButton roomCreateConnectButton;
    private JTextField userNameTextField;
    private JTextField roomNameTextField;
    private boolean userLoggedInStatus;
    private boolean roomConnectedStatus;

    public StartForm() {
        sendButton.addActionListener(e -> System.out.println("Going to send a message"));
        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        userLoginButton.setEnabled(false);
                        if (userLoggedInStatus) {
                            UserDto user = uiAction.userLogOffAction(currentUser);
                            if (user != UserDto.EMPTY_ENTITY) {
                                currentUser = UserDto.EMPTY_ENTITY;
                                userLoginButton.setText("User Login");
                                userNameTextField.setEnabled(true);
                                userLoggedInStatus = false;
                                //clear controls - contactList,msgList, etc
                                contactListModel.removeAllElements();
                            }
                        } else {
                            String userName = userNameTextField.getText();
                            currentUser = uiAction.userLogInAction(userName);
                            if (currentUser != UserDto.EMPTY_ENTITY) {
                                userLoginButton.setText("User Logoff");
                                userNameTextField.setEnabled(false);
                                userLoggedInStatus = true;
//                                contactListModel.addAll(currentUser.getContactList());
//                                contactList.setModel(contactListModel);
                                //fill Contact List from UserEntity
                            }
                        }
                        userLoginButton.setEnabled(true);
                        changeRoomCreateConnectButtonEnabledState();
                        return null;
                    }
                }.execute();
            }
        });
        userNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeUserLoginButtonEnabledState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeUserLoginButtonEnabledState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeUserLoginButtonEnabledState();
            }
        });
        roomCreateConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        roomCreateConnectButton.setEnabled(false);
                        if (roomConnectedStatus) {
                            if (currentRoom.getRoomUsers().contains(currentUser))
                                currentRoom.getRoomUsers().remove(currentUser);
                            RoomDto room = uiAction.leaveRoom(currentRoom);
                            if (room != RoomDto.EMPTY_ENTITY) {
                                currentRoom = RoomDto.EMPTY_ENTITY;
                                roomCreateConnectButton.setText("Enter Room");
                                roomNameTextField.setEnabled(true);
                                roomConnectedStatus = false;
                                //clear controls - usersList,msgList, etc
                                roomUserListModel.removeAllElements();
                            }
                        } else {
                            String roomName = roomNameTextField.getText();
                            currentRoom = uiAction.roomEnter(roomName);
                            if (currentRoom != RoomDto.EMPTY_ENTITY) {
                                roomCreateConnectButton.setText("Leave Room");
                                roomNameTextField.setEnabled(false);
                                roomConnectedStatus = true;
//                                roomUserListModel.addAll(currentRoom.getRoomUsers());
//                                roomUserList.setModel(roomUserListModel);
//                                fillRoomUserList
//                                fillMessagesFromHistory
                            }
                        }
                        roomCreateConnectButton.setEnabled(true);
                        return null;
                    }
                }.execute();
            }
        });
        roomNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeRoomCreateConnectButtonEnabledState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeRoomCreateConnectButtonEnabledState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeRoomCreateConnectButtonEnabledState();
            }
        });

    }

    private void changeUserLoginButtonEnabledState() {
        userLoginButton.setEnabled(!userNameTextField.getText().isBlank());
    }

    private void changeRoomCreateConnectButtonEnabledState() {
        roomNameTextField.setEnabled(userLoggedInStatus);
        roomCreateConnectButton.setEnabled(!roomNameTextField.getText().isBlank()
                & userLoggedInStatus);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setName("");
        roomChatTextField = new JTextField();
        roomChatTextField.setText("");
        mainPanel.add(roomChatTextField, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        sendButton = new JButton();
        sendButton.setText("Send");
        mainPanel.add(sendButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messageTextField = new JTextField();
        messageTextField.setText("");
        messageTextField.setToolTipText("Message text");
        mainPanel.add(messageTextField, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(416, 30), null, 0, false));
        roomUserList = new JList();
        mainPanel.add(roomUserList, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        userLoginButton = new JButton();
        userLoginButton.setLabel("User Login");
        userLoginButton.setText("User Login");
        mainPanel.add(userLoginButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contactList = new JList();
        mainPanel.add(contactList, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        roomCreateConnectButton = new JButton();
        roomCreateConnectButton.setActionCommand("Enter Room");
        roomCreateConnectButton.setLabel("Enter Room");
        roomCreateConnectButton.setText("Enter Room");
        mainPanel.add(roomCreateConnectButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userNameTextField = new JTextField();
        mainPanel.add(userNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        roomNameTextField = new JTextField();
        mainPanel.add(roomNameTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("UserName");
        mainPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("RoomName");
        mainPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        changeUserLoginButtonEnabledState();
        changeRoomCreateConnectButtonEnabledState();
    }
}
