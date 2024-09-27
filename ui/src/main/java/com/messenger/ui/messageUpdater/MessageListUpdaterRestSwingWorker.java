package com.messenger.ui.messageUpdater;

import com.messenger.common.dto.MessageDto;
import com.messenger.ui.form.StartForm;
import com.messenger.ui.services.UiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.List;

public class MessageListUpdaterRestSwingWorker extends SwingWorker<Object, Object> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListUpdaterRestSwingWorker.class);
    private final StartForm form;
    private final UiAction uiAction;
    private List<MessageDto> updatedMessageList;
    private final List<MessageDto> currentRoomMessageList;

    public MessageListUpdaterRestSwingWorker(StartForm form) {
        this.form = form;
        uiAction = form.getUiAction();
        this.currentRoomMessageList = form.getCurrentRoomMessageList();
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
        for (MessageDto message : updatedMessageList) {
            form.addMessage(message);
        }
    }
}
