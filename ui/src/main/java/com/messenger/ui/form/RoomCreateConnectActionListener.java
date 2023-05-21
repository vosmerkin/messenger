package com.messenger.ui.form;

import com.messenger.common.dto.RoomDto;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RoomCreateConnectActionListener implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger(RoomCreateConnectActionListener.class);
    private StartForm form;
    private UiAction uiAction;

    public RoomCreateConnectActionListener(StartForm form) {
        this.form=form;
        uiAction = form.getUiAction();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                roomCreateConnectButton.setEnabled(false);
                if (roomConnectedStatus) {
                    //stop message update
                    if (currentRoom.getRoomUsers().contains(currentUser))
                        currentRoom.getRoomUsers().remove(currentUser);
                    RoomDto room = uiAction.updateRoom(currentRoom);
                    if (room != RoomDto.EMPTY_ENTITY) {
                        currentRoom = RoomDto.EMPTY_ENTITY;
                        roomCreateConnectButton.setText("Enter Room");
                        roomNameTextField.setEnabled(true);
                        roomConnectedStatus = false;
                        //clear controls - usersList,msgList, etc
                        roomUserListModel.removeAllElements();
                        if (messageListUpdaterHandle != null) messageListUpdaterHandle.cancel(true);
                    }
                } else {
                    String roomName = roomNameTextField.getText();
                    currentRoom = uiAction.roomEnter(roomName, currentUser);
                    if (currentRoom != RoomDto.EMPTY_ENTITY) {
                        roomCreateConnectButton.setText("Leave Room");
                        roomNameTextField.setEnabled(false);
                        roomConnectedStatus = true;
//                                fillRoomUserList
                        roomUserListModel.addAll(currentRoom.getRoomUserNames());
                        roomUserList.setModel(roomUserListModel);
//                                fillMessagesFromHistory
                        currentRoomMessageList = new CopyOnWriteArrayList<>();
                        LOG.info("creating and scheduling Runnable for updating messages");
                        final Runnable messageListUpdater = new Runnable() {
                            public void run() {
                                MessageListUpdaterSwingWorker messageListWorker = new MessageListUpdaterSwingWorker(StartForm.this);
                                messageListWorker.execute();
                            }
                        };
                        messageListUpdaterHandle =
                                scheduler.scheduleAtFixedRate(messageListUpdater, 5, 20, SECONDS);
                    }
                }
                roomCreateConnectButton.setEnabled(true);
                changeSendButtonEnabledState();
                return null;
            }
        }.execute();
    }
}
