package com.vanderkast.smsforwardapp.helper;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Simple realisation for handling tasks that consumes results for ui thread in background.
 *
 * @param <T> type of result that is returned by task and consumed by ui thread.
 */
public class BackgroundExecutor<T> extends Thread {

    /**
     * <p>Main purpose for this class is run a task in background thread and then pass result to ui thread.</p>
     * <p>You can achieve it via passing <code>(T) -> Activity#runOnUiThread(Runnable)</code> as <code>resultHandler</code> argument in your activity.</p>
     *
     * @param task          that returns an T result and should be proceeded in background thread
     * @param resultHandler that consumes T result from task
     */
    public BackgroundExecutor(Supplier<T> task, Consumer<T> resultHandler) {
        super(() -> resultHandler.accept(task.get()));
    }
}
