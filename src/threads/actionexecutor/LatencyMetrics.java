package threads.actionexecutor;

import java.time.Duration;
import java.util.Map;

public record LatencyMetrics(
        Duration totalLatency,
        Map<String, Duration> splitLatencies
) {
    public LatencyMetrics {
        splitLatencies = Map.copyOf(splitLatencies);
    }
}
