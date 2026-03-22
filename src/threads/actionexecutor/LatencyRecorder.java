package threads.actionexecutor;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class LatencyRecorder {

    private final ConcurrentHashMap<String, Duration> splitMap = new ConcurrentHashMap<>();
    private final AtomicReference<Duration> totalLatency = new AtomicReference<>(Duration.ZERO);

    public void recordSplit(String action, Duration duration) {
        splitMap.merge(action, duration, Duration::plus);
    }

    public void recordTotal(Duration duration) {
        totalLatency.set(duration);
    }

    public LatencyMetrics snapshot() {
        return new LatencyMetrics(totalLatency.get(), splitMap);
    }
}
