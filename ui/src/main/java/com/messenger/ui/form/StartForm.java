package com.messenger.ui.form;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import com.messenger.ui.services.UiAction;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


public class StartForm {
    private static final Logger LOG = LoggerFactory.getLogger(StartForm.class);
    @Getter
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Getter
    private final DefaultListModel<UserDto> contactListModel = new DefaultListModel<>();
    @Getter
    private final DefaultListModel<String> roomUserListModel = new DefaultListModel<>();
    @Getter
    private final DefaultTableModel messageListModel = new DefaultTableModel(new String[][]{{"User", "Date", "Text"}}, new String[]{"User", "Date", "Text"});
    @Getter
    private final UiAction uiAction = new UiAction();
    @Getter
    @Setter
    private UserDto currentUser;
    @Getter
    @Setter
    private RoomDto currentRoom;
    @Getter
    private final List<MessageDto> currentRoomMessageList = new CopyOnWriteArrayList<>();
    private JButton sendButton;
    @Getter
    private JTextField messageTextField;
    private JPanel mainPanel;
    @Getter
    private JList roomUserList;
    @Getter
    private JButton userLoginButton;
    private JList contactList;
    @Getter
    private JButton roomCreateConnectButton;
    @Getter
    private JTextField userNameTextField;
    @Getter
    private JTextField roomNameTextField;
    private JTable roomChatTable;
    @Getter
    @Setter
    private boolean userLoggedInStatus;
    @Getter
    @Setter
    private boolean roomConnectedStatus;
    @Getter
    private ScheduledFuture<?> messageListUpdaterHandle;


    public StartForm() {
        ActionListener sendButtonActionListener = new SendButtonActionListener(this);
        messageTextField.addActionListener(sendButtonActionListener);
        sendButton.addActionListener(sendButtonActionListener);
        messageTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSendButtonEnabledState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSendButtonEnabledState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSendButtonEnabledState();
            }
        });

        ActionListener userLoginButtonActionListener = new UserLoginButtonActionListener(this);
        userLoginButton.addActionListener(userLoginButtonActionListener);
        userNameTextField.addActionListener(userLoginButtonActionListener);
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
        ActionListener roomCreateConnectActionListener = new RoomCreateConnectActionListener(this);
        roomCreateConnectButton.addActionListener(roomCreateConnectActionListener);
        roomNameTextField.addActionListener(roomCreateConnectActionListener);
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

        changeUserLoginButtonEnabledState();
        changeRoomCreateConnectButtonEnabledState();
        changeSendButtonEnabledState();
        roomChatTable.setModel(messageListModel);
        roomChatTable.getColumnModel().getColumn(0).setPreferredWidth((int) (roomChatTable.getWidth() * 0.2));
        roomChatTable.getColumnModel().getColumn(1).setPreferredWidth((int) (roomChatTable.getWidth() * 0.2));
        roomChatTable.getColumnModel().getColumn(2).setWidth((int) (roomChatTable.getWidth() * 0.6));

    }

    private void changeUserLoginButtonEnabledState() {
        userLoginButton.setEnabled(!userNameTextField.getText().isBlank());
    }

    void changeRoomCreateConnectButtonEnabledState() {
        roomNameTextField.setEnabled(userLoggedInStatus);
        roomCreateConnectButton.setEnabled(!roomNameTextField.getText().isBlank()
                & userLoggedInStatus);
    }

    void changeSendButtonEnabledState() {
        messageTextField.setEnabled(roomConnectedStatus);
        sendButton.setEnabled(!messageTextField.getText().isBlank() & roomConnectedStatus);
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
        roomChatTable = new JTable();
        mainPanel.add(roomChatTable, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here


    }


}
