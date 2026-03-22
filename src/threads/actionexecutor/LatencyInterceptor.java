package threads.actionexecutor;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

public class LatencyInterceptor {

    private final LatencyRecorder recorder;
    private Instant batchStart;

    public LatencyInterceptor(LatencyRecorder recorder) {
        this.recorder = recorder;
    }

    public void startBatch() {
        batchStart = Instant.now();
    }

    public void stopBatch() {
        if (batchStart != null) {
            recorder.recordTotal(Duration.between(batchStart, Instant.now()));
        }
    }

    /**
     * Times the given callable and records the elapsed duration under {@code dimension}.
     * The callable's checked exception is wrapped in a RuntimeException if thrown.
     */
    public <T> T recordLatency(String dimension, Callable<T> callable) {
        Instant splitStart = Instant.now();
        try {
            return callable.call();
        } catch (Exception e) {
            throw (e instanceof RuntimeException re) ? re : new RuntimeException(e);
        } finally {
            recorder.recordSplit(dimension, Duration.between(splitStart, Instant.now()));
        }
    }

    public LatencyMetrics getMetrics() {
        return recorder.snapshot();
    }
}
