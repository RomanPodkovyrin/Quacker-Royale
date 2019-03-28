package com.anotherworld.view;

import com.anotherworld.tools.Action;
import com.anotherworld.view.input.BindableKeyListener;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BindableKeyManager implements Runnable {

    private static Logger logger = LogManager.getLogger(BindableKeyManager.class);

    private Optional<Action<Integer>> waiting;

    private BindableKeyListener keyListener;

    private boolean running;

    private boolean wait = true;

    /**
     * Creates a wrapper for the bindable key listener to release control.
     * @param keyListener The key listener to use
     */
    public BindableKeyManager(BindableKeyListener keyListener) {
        waiting = Optional.empty();
        running = true;
        this.keyListener = keyListener;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Optional<Action<Integer>> current;
                synchronized (waiting) {
                    current = waiting;
                    waiting = Optional.empty();
                    wait = true;
                }

                if (current.isPresent()) {
                    current.get().perform(tryAction());
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.debug("Interupted");
            }
        }
    }

    public boolean hasWaitingKeys() {
        return true;
    }

    public void clear() {
        waiting = Optional.empty();
    }

    public void close() {
        running = false;
    }

    /**
     * Queues an action to collect a button for.
     * @param action The action to perform with the button
     */
    public void queue(Action<Integer> action) {
        System.out.println("queued");
        synchronized (waiting) {
            wait = false;
            waiting = Optional.of(action);
        }
    }

    private int tryAction() throws InterruptedException {
        ArrayList<Integer> downKeys;
        logger.debug("Waiting for key");
        do {
            downKeys = keyListener.getBindableKey();
            Thread.sleep(10);
            if (!wait) {
                throw new InterruptedException("");
            }
        } while (downKeys.size() == 0);
        return downKeys.get(0);
    }

}
