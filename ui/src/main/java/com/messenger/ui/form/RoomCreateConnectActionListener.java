package com.messenger.ui.form;

import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

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

    private MessageListUpdaterRest messageListUpdater;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                var roomConnectedStatus = form.isRoomConnectedStatus();
                var currentUser = form.getCurrentUser();
                var currentRoom = form.getCurrentRoom();
//                var currentRoomMessageList = form.getCurrentRoomMessageList();
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
                        if (messageListUpdater != null) {
                            messageListUpdater.stopUpdating();
                            messageListUpdater = null;
                        }
//                        if (messageListUpdaterHandle != null) {
//                            messageListUpdaterHandle.cancel(true);
//                            messageListUpdaterHandle = null;
//                        }
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
//                                fillMessagesFromHistory
                        LOG.info("Start message updating");
                        messageListUpdater = new MessageListUpdaterRest(form, currentRoomMessageList);
                        messageListUpdater.startUpdating();
//                        final Runnable messageListUpdaterRunnuble = new Runnable() {
//                            public void run() {
//                                MessageListUpdaterSwingWorker messageListWorker = new MessageListUpdaterSwingWorker(form, currentRoomMessageList);
//                                messageListWorker.execute();
//                            }
//                        };
//                        messageListUpdaterHandle =
//                                scheduler.scheduleAtFixedRate(messageListUpdaterRunnuble, 5, 20, SECONDS);
                    }
                }
                roomCreateConnectButton.setEnabled(true);
                form.changeSendButtonEnabledState();
                return null;
            }
        }.execute();
    }
}
