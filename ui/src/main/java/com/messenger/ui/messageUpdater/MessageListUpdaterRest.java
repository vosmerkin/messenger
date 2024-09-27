package com.messenger.ui.messageUpdater;

import com.messenger.ui.form.StartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MessageListUpdaterRest implements MessageListUpdater {
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
                MessageListUpdaterRestSwingWorker messageListWorker = new MessageListUpdaterRestSwingWorker(form);
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
