package br.com.italo.santana.challenge.prompt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProperties {
    /**
     * number of parallelism processing.
     */
    private int parallelism;

    /**
     * batch processing time in seconds. eg: 2 orders per second
     */
    private int throttle;

    /**
     * filename that will be processed.
     */
    private String filename;

    /**
     * cold shelf queue capacity.
     */
    private int coldShelfCapacity;

    /**
     * hot shelf queue capacity.
     */
    private int hotShelfCapacity;

    /**
     * frozen shelf queue capacity.
     */
    private int frozenShelfCapacity;

    /**
     * overflow shelf queue capacity.
     */
    private int overflowShelfCapacity;

    /**
     * regular shelf (hot, frozen, cold) decay modifier.
     */
    private int regularShelfDecayModifier;

    /**
     * overflow shelf decay modifier.
     */
    private int overflowShelfDecayModifier;

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getColdShelfCapacity() {
        return coldShelfCapacity;
    }

    public void setColdShelfCapacity(int coldShelfCapacity) {
        this.coldShelfCapacity = coldShelfCapacity;
    }

    public int getHotShelfCapacity() {
        return hotShelfCapacity;
    }

    public void setHotShelfCapacity(int hotShelfCapacity) {
        this.hotShelfCapacity = hotShelfCapacity;
    }

    public int getFrozenShelfCapacity() {
        return frozenShelfCapacity;
    }

    public void setFrozenShelfCapacity(int frozenShelfCapacity) {
        this.frozenShelfCapacity = frozenShelfCapacity;
    }

    public int getOverflowShelfCapacity() {
        return overflowShelfCapacity;
    }

    public void setOverflowShelfCapacity(int overflowShelfCapacity) {
        this.overflowShelfCapacity = overflowShelfCapacity;
    }

    public int getRegularShelfDecayModifier() {
        return regularShelfDecayModifier;
    }

    public void setRegularShelfDecayModifier(int regularShelfDecayModifier) {
        this.regularShelfDecayModifier = regularShelfDecayModifier;
    }

    public int getOverflowShelfDecayModifier() {
        return overflowShelfDecayModifier;
    }

    public void setOverflowShelfDecayModifier(int overflowShelfDecayModifier) {
        this.overflowShelfDecayModifier = overflowShelfDecayModifier;
    }
}
