package com.messenger.ui.form;

import com.messenger.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MessageListUpdaterRest {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListUpdaterRest.class);
    private final Runnable messageListUpdaterRunnable;
    private ScheduledFuture<?> messageListUpdaterHandle;
    private final ScheduledExecutorService scheduler;

    public MessageListUpdaterRest(StartForm form) {
        LOG.info("creating and scheduling Runnable for updating messages");
        messageListUpdaterHandle = form.getMessageListUpdaterHandle();
        scheduler = form.getScheduler();
        messageListUpdaterRunnable = new Runnable() {
            public void run() {
                MessageListUpdaterSwingWorker messageListWorker = new MessageListUpdaterSwingWorker(form);
                messageListWorker.execute();
            }
        };
    }

    public void startUpdating() {
        messageListUpdaterHandle =
                scheduler.scheduleAtFixedRate(messageListUpdaterRunnable, 5, 20, SECONDS);
    }

    public void stopUpdating(){
        messageListUpdaterHandle.cancel(true);
        messageListUpdaterHandle = null;
    }
}
