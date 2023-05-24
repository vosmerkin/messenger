package com.messenger.ui.form;

import com.messenger.common.dto.MessageDto;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessageListUpdaterSwingWorker extends SwingWorker<Object, Object> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListUpdaterSwingWorker.class);
    private final StartForm form;
    private final UiAction uiAction;    private List<MessageDto> updatedMessageList;
    private final List<MessageDto> currentRoomMessageList;

    public MessageListUpdaterSwingWorker(StartForm form, List<MessageDto> currentRoomMessageList) {
        this.form = form;
        uiAction = form.getUiAction();
        this.currentRoomMessageList= currentRoomMessageList;
    }

    @Override
    protected Void doInBackground() {
        var currentRoom = form.getCurrentRoom();
        LOG.info("Requesting new messages");
        updatedMessageList = uiAction.requestRoomMessages(currentRoom.getId());
        return null;
    }

    @Override
    protected void done() {
        var messageListModel = form.getMessageListModel();
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
