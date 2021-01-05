package br.com.italo.santana.challenge.prompt.factories;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ForkJoin factory implementation to create customs thread pools.
 *
 * @author italosantana
 */
public class NamedForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

    private AtomicInteger counter = new AtomicInteger(0);
    private final String name;
    private final boolean daemon;

    public NamedForkJoinWorkerThreadFactory(String name, boolean daemon) {
        this.name = name;
        this.daemon = daemon;
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread t = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        t.setName(name + "-" + counter.incrementAndGet());
        t.setDaemon(daemon);
        return t;
    }
}
