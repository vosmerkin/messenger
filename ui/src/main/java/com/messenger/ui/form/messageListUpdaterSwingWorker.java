package com.messenger.ui.form;

import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class messageListUpdaterSwingWorker extends SwingWorker<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(messageListUpdaterSwingWorker.class);
    private StartForm form;

    private UiAction uiAction;
    private RoomDto currentRoom;
    private List<MessageDto> currentRoomMessageList;
    private final DefaultTableModel messageListModel;

    private List<MessageDto> updatedMessageList;

    public messageListUpdaterSwingWorker(StartForm form) {
        this.form = form;
        uiAction = form.getUiAction();
        currentRoom = form.getCurrentRoom();
        currentRoomMessageList = form.getCurrentRoomMessageList();
        messageListModel = form.getMessageListModel();
    }


    @Override
    protected Void doInBackground() throws Exception {
        LOG.info("Requesting new messages");
        updatedMessageList = uiAction.requestRoomMessages(currentRoom.getId());
//                Thread.sleep(1500);
        return null;
    }

    @Override
    protected void done() {
        LOG.info("Received {} new messages, updating JTable", currentRoomMessageList.size() - updatedMessageList.size());
        //add messages difference to messagesModelList

        updatedMessageList.removeAll(currentRoomMessageList);
        if (updatedMessageList.size() > 0) {
            for (MessageDto message : updatedMessageList) {
                messageListModel.addRow(
                        new String[]{message.getUser().getUserName(),
                                new SimpleDateFormat("HH:mm:ss").format(message.getMessageDateTime()),
                                message.getMessageText()});
                currentRoomMessageList.add(message);
            }
        }
    }
}
