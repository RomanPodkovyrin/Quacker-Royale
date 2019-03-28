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
                int action = tryAction();

                synchronized (waiting) {
                    if (waiting.isPresent()) {
                        waiting.get().perform(action);
                        waiting = Optional.empty();
                    }
                }
            } catch (InterruptedException e) {
                logger.debug("Interupted");
            }
        }
    }

    /**
     * Clears the current bindable key action.
     */
    public void clear() {
        synchronized (waiting) {
            waiting = Optional.empty();
        }
    }

    public void close() {
        running = false;
    }

    /**
     * Queues an action to collect a button for.
     * @param action The action to perform with the button
     */
    public void queue(Action<Integer> action) {
        synchronized (waiting) {
            waiting = Optional.of(action);
        }
    }

    private int tryAction() throws InterruptedException {
        ArrayList<Integer> downKeys;
        logger.debug("Waiting for key");
        do {
            downKeys = keyListener.getBindableKey();
            Thread.sleep(50);
            if (!running) {
                throw new InterruptedException();
            }
        } while (downKeys.size() == 0);
        return downKeys.get(0);
    }

}
