package br.com.italo.santana.challenge.prompt.domain;

import br.com.italo.santana.challenge.prompt.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity that represents a client Order.
 *
 * @author italosantana
 */
public class Order {
    /**
     * unique id
     */
    private UUID id;
    /**
     * order item name
     */
    private String name;

    /**
     * order item ideal temperature.
     */
    private String temp;

    /**
     * order item lifetime in shelf
     */
    private int shelfLife;

    /**
     * order item decay rate
     */
    private Double decayRate;

    /**
     * order item create datetime
     */
    private LocalDateTime createDate;

    /**
     *
     */
    private double shelfLifeValue;

    /**
     * Class default constructor
     */
    public Order() {
        this.setCreateDate(LocalDateTime.now());
    }

    /**
     * Class constructor that specifies properties
     */
    public Order(UUID id, String name, String temp, int shelfLife, Double decayRate) {
        this.setId(id);
        this.setName(name);
        this.setTemp(temp);
        this.setShelfLife(shelfLife);
        this.setDecayRate(decayRate);
        this.setCreateDate(LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "id must not be null!!! ");
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null!!!");
    }

    public String getTemp() {
        return temp;
    }

    private void setTemp(String temp) {
        this.temp = Objects.requireNonNull(temp, "temperaqture must not be null!!!");
    }

    public int getShelfLife() {
        return shelfLife;
    }

    private void setShelfLife(int shelfLife) { this.shelfLife = Objects.requireNonNull(shelfLife, "shelfLife must not be null!!!"); }

    public Double getDecayRate() {
        return decayRate;
    }

    private void setDecayRate(Double decayRate) { this.decayRate = Objects.requireNonNull(decayRate, "decayRate must not be null!!!"); }

    LocalDateTime getCreateDate() {
        return createDate;
    }

    private void setCreateDate(LocalDateTime createDate) { this.createDate = Objects.requireNonNull(createDate, "createDate must not be null!!!"); }

    public double getShelfLifeValue() {
        return shelfLifeValue;
    }

    private void setShelfLifeValue(double shelfLifeValue) { this.shelfLifeValue = Objects.requireNonNull(shelfLifeValue, "shelfLifeValue must not be null!!!"); }

    /**
     * This method checks and returns whether the order is valid (should be collected to delivery) or is not valid (should be discarded).
     * @param shelfDecayModifier
     * @return
     */
    public boolean isValidValidForDelivery(int shelfDecayModifier)
    {
        long orderAge = DateTimeUtil.calculateAgeInSeconds(this.getCreateDate(), LocalDateTime.now());

        this.setShelfLifeValue(calculateShelfLifeValue(orderAge, shelfDecayModifier));

        return this.shelfLifeValue > 0;
    }

    private Double calculateShelfLifeValue(long orderAge, int shelfDecayModifier) {

        return (((this.getShelfLife() - this.getDecayRate() *  orderAge) * shelfDecayModifier) / this.getShelfLife());
    }
}
