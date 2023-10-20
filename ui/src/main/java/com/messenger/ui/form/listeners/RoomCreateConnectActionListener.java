package com.messenger.ui.form.listeners;

import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.ui.form.StartForm;
import com.messenger.ui.messageUpdater.MessageListUpdater;
import com.messenger.ui.messageUpdater.MessageListUpdaterGrpc;
import com.messenger.ui.messageUpdater.MessageListUpdaterRest;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomCreateConnectActionListener implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger(RoomCreateConnectActionListener.class);
    private final StartForm form;
    private final UiAction uiAction;
    private final JButton roomCreateConnectButton;
    private final JTextField roomNameTextField;
    private final JList roomUserList;
    //    private final ScheduledExecutorService scheduler;
//    private ScheduledFuture<?> messageListUpdaterHandle;
    private final DefaultListModel<String> roomUserListModel;
    private final List<MessageDto> currentRoomMessageList;

//    private final MessageListUpdaterRest messageListUpdater;
    private final MessageListUpdater messageListUpdater;

    public RoomCreateConnectActionListener(StartForm form) {
        this.form = form;
        uiAction = form.getUiAction();
        roomCreateConnectButton = form.getRoomCreateConnectButton();
        roomNameTextField = form.getRoomNameTextField();
        roomUserListModel = form.getRoomUserListModel();
        roomUserList = form.getRoomUserList();
//        scheduler = form.getScheduler();
//        messageListUpdaterHandle = form.getMessageListUpdaterHandle();
        currentRoomMessageList = form.getCurrentRoomMessageList();
//        messageListUpdater = new MessageListUpdaterRest(form);
        messageListUpdater = new MessageListUpdaterRest(form);
//        messageListUpdater = new MessageListUpdaterGrpc(form);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                LOG.info("RoomCreateConnectButton clicked");
                var roomConnectedStatus = form.isRoomConnectedStatus();
                var currentUser = form.getCurrentUser();
                var currentRoom = form.getCurrentRoom();
                roomCreateConnectButton.setEnabled(false);
                if (roomConnectedStatus) {
                    //stop message update
                    currentRoom.getRoomUsers().remove(currentUser);
                    RoomDto room = uiAction.updateRoom(currentRoom);
                    if (room != RoomDto.EMPTY_ENTITY) {
                        form.setCurrentRoom(currentRoom = RoomDto.EMPTY_ENTITY);
                        roomCreateConnectButton.setText("Enter Room");
                        roomNameTextField.setEnabled(true);
                        form.setRoomConnectedStatus(roomConnectedStatus = false);
                        //clear controls - usersList,msgList, etc
                        roomUserListModel.removeAllElements();
                        currentRoomMessageList.clear();

                        LOG.info("STOP message updating");
                        messageListUpdater.stopUpdating();
                        roomCreateConnectButton.setEnabled(true);
                        form.changeSendButtonEnabledState();
                    }
                } else {
                    String roomName = roomNameTextField.getText();
                    form.setCurrentRoom(currentRoom = uiAction.roomEnter(roomName, currentUser));
                    if (currentRoom != RoomDto.EMPTY_ENTITY) {
                        roomCreateConnectButton.setText("Leave Room");
                        roomNameTextField.setEnabled(false);
                        form.setRoomConnectedStatus(roomConnectedStatus = true);
//                                fillRoomUserList
                        roomUserListModel.addAll(currentRoom.getRoomUserNames());
                        roomUserList.setModel(roomUserListModel);
                        roomCreateConnectButton.setEnabled(true);
                        form.changeSendButtonEnabledState();
//                                fillMessagesFromHistory
                        LOG.info("Start message updating");
                        messageListUpdater.startUpdating();
                    }
                }
                roomCreateConnectButton.setEnabled(true);
                form.changeSendButtonEnabledState();
                return null;
            }
        }.execute();
    }
}
