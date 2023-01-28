package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.*;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ThreeSumBenchmark {
    public ThreeSumBenchmark(int runs, int n, int m) {
        this.runs = runs;
        this.supplier = new Source(n, m).intsSupplier(10);
        this.n = n;
    }

    public void runBenchmarks() {
        System.out.println("ThreeSumBenchmark: N=" + n);
        benchmarkThreeSum("ThreeSumQuadratic", (xs) -> new ThreeSumQuadratic(xs).getTriples(), n, timeLoggersQuadratic);
        benchmarkThreeSum("ThreeSumQuadrithmic", (xs) -> new ThreeSumQuadrithmic(xs).getTriples(), n, timeLoggersQuadrithmic);
        benchmarkThreeSum("ThreeSumQuadraticWithCalipers", (xs) -> new ThreeSumQuadraticWithCalipers(xs).getTriples(), n, timeLoggersQuadraticWithCalipers);
        benchmarkThreeSum("ThreeSumCubic", (xs) -> new ThreeSumCubic(xs).getTriples(), n, timeLoggersCubic);
    }

    public static void main(String[] args) {
        new ThreeSumBenchmark(100, 250, 250).runBenchmarks();
        new ThreeSumBenchmark(50, 500, 500).runBenchmarks();
        new ThreeSumBenchmark(20, 1000, 1000).runBenchmarks();
        new ThreeSumBenchmark(10, 2000, 2000).runBenchmarks();
        new ThreeSumBenchmark(5, 4000, 4000).runBenchmarks();
        new ThreeSumBenchmark(3, 8000, 8000).runBenchmarks();
        new ThreeSumBenchmark(2, 16000, 16000).runBenchmarks();
    }

    private void benchmarkThreeSum(final String description, final Consumer<int[]> function, int n, final TimeLogger[] timeLoggers) {
        if (description.equals("ThreeSumCubic") && n > 4000) return;

        Stopwatch stopwatch = new Stopwatch();
        double milliseconds = 0;
        for (int i = 0; i < runs; i++) {
            int[] xs = supplier.get();
            function.accept(xs);
            milliseconds += stopwatch.lap();
        }
        double averageMilliseconds = milliseconds / runs;
        for (TimeLogger timeLogger : timeLoggers) timeLogger.log(averageMilliseconds, n);

        stopwatch.close();
        // END 
    }

    private final static TimeLogger[] timeLoggersCubic = {
            new TimeLogger("Cubic Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Cubic Normalized time per run (n^3): ", (time, n) -> time / n / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadrithmic = {
            new TimeLogger("Quadrithmic Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Quadrithmic Normalized time per run (n^2 log n): ", (time, n) -> time / n / n / Utilities.lg(n) * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("Quadratic Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Quadratic Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadraticWithCalipers = {
            new TimeLogger("QuadraticWithCalipers Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("QuadraticWithCalipers Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };
    private final int runs;
    private final Supplier<int[]> supplier;
    private final int n;
}
