package br.com.italo.santana.challenge.prompt.domain;

import br.com.italo.santana.challenge.prompt.util.DateTimeUtil;
import br.com.italo.santana.challenge.prompt.util.JsonParserUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity that represents an Order.
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
     * Preferred shelf storage temperature
     */
    private String temp;

    /**
     * Shelf wait max duration (seconds)
     */
    private int shelfLife;

    /**
     * Value deterioration modifier
     */
    private Double decayRate;

    /**
     * order item create datetime to get order age
     */
    private LocalDateTime createDate;

    /**
     * Orders have an inherent value that will deteriorate over time, based on the order’s shelfLife and decayRate fields
     * that calculates by {@code calculateShelfLifeValue()}
     */
    private Double shelfLifeValue;

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

    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "id must not be null!!! ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null!!!");
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = Objects.requireNonNull(temp, "temperaqture must not be null!!!");
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) { this.shelfLife = Objects.requireNonNull(shelfLife, "shelfLife must not be null!!!"); }

    public Double getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(Double decayRate) { this.decayRate = Objects.requireNonNull(decayRate, "decayRate must not be null!!!"); }

    LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) { this.createDate = Objects.requireNonNull(createDate, "createDate must not be null!!!"); }

    public Double getShelfLifeValue() {
        return shelfLifeValue;
    }

    public void setShelfLifeValue(double shelfLifeValue) { this.shelfLifeValue = Objects.requireNonNull(shelfLifeValue, "shelfLifeValue must not be null!!!"); }

    public String toString() {
        return JsonParserUtil.toJson(this);
    }

    /**
     * This method checks and returns whether the order is valid (should be collected to delivery) or is not valid (should be discarded).
     * @param shelfDecayModifier
     * @return
     */
    public boolean isValidToDelivery(int shelfDecayModifier) {

        long orderAge = DateTimeUtil.calculateAgeInSeconds(this.getCreateDate(), LocalDateTime.now());

        this.setShelfLifeValue(calculatesShelfLifeValue(orderAge, shelfDecayModifier));

        return this.shelfLifeValue > 0;
    }

    /**
     * This method calculates the shelf life value.
     * @param orderAge
     * @param shelfDecayModifier
     * @return
     */
    private Double calculatesShelfLifeValue(long orderAge, int shelfDecayModifier) {

        return (((this.getShelfLife() - this.getDecayRate() *  orderAge) * shelfDecayModifier) / this.getShelfLife());
    }
}
