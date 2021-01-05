package br.com.italo.santana.challenge.prompt.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProperties {
    /**
     * number of parallelism processing.
     */
    private Integer parallelism;

    /**
     * batch processing time in seconds. eg: 'x' parallels orders per 'y' second
     */
    private int throttle;

    /**
     * custom thread pool name.
     */
    private String threadPoolName;

    /**
     * orders file base path.
     */
    private String fileBasePath;

    /**
     * filename that will be processed.
     */
    private String filename;

    /**
     * cold shelf queue capacity.
     */
    private Integer coldShelfCapacity;

    /**
     * hot shelf queue capacity.
     */
    private Integer hotShelfCapacity;

    /**
     * frozen shelf queue capacity.
     */
    private Integer frozenShelfCapacity;

    /**
     * overflow shelf queue capacity.
     */
    private Integer overflowShelfCapacity;

    /**
     * regular shelf (hot, frozen, cold) decay modifier.
     */
    private Integer regularShelfDecayModifier;

    /**
     * overflow shelf decay modifier.
     */
    private Integer overflowShelfDecayModifier;

    public Integer getParallelism() {
        return parallelism;
    }

    public void setParallelism(Integer parallelism) {
        this.parallelism = parallelism;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public String getFileBasePath() {
        return fileBasePath;
    }

    public void setFileBasePath(String fileBasePath) {
        this.fileBasePath = fileBasePath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getColdShelfCapacity() {
        return coldShelfCapacity;
    }

    public void setColdShelfCapacity(Integer coldShelfCapacity) {
        this.coldShelfCapacity = coldShelfCapacity;
    }

    public Integer getHotShelfCapacity() {
        return hotShelfCapacity;
    }

    public void setHotShelfCapacity(Integer hotShelfCapacity) {
        this.hotShelfCapacity = hotShelfCapacity;
    }

    public Integer getFrozenShelfCapacity() {
        return frozenShelfCapacity;
    }

    public void setFrozenShelfCapacity(Integer frozenShelfCapacity) {
        this.frozenShelfCapacity = frozenShelfCapacity;
    }

    public Integer getOverflowShelfCapacity() {
        return overflowShelfCapacity;
    }

    public void setOverflowShelfCapacity(Integer overflowShelfCapacity) {
        this.overflowShelfCapacity = overflowShelfCapacity;
    }

    public Integer getRegularShelfDecayModifier() {
        return regularShelfDecayModifier;
    }

    public void setRegularShelfDecayModifier(Integer regularShelfDecayModifier) {
        this.regularShelfDecayModifier = regularShelfDecayModifier;
    }

    public Integer getOverflowShelfDecayModifier() {
        return overflowShelfDecayModifier;
    }

    public void setOverflowShelfDecayModifier(Integer overflowShelfDecayModifier) {
        this.overflowShelfDecayModifier = overflowShelfDecayModifier;
    }
}
