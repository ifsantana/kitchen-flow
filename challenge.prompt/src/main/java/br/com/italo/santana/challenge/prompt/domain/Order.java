package br.com.italo.santana.challenge.prompt.domain;

import br.com.italo.santana.challenge.prompt.util.DateTimeUtil;
import br.com.italo.santana.challenge.prompt.util.JsonParserUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private String name;
    private String temp;
    private int shelfLife;
    private Double decayRate;
    private LocalDateTime createDate;
    private double shelfLifeValue;

    public Order() {
        this.setCreateDate(LocalDateTime.now());
    }

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

    private void setDecayRate(Double decayRate) {
        this.decayRate = Objects.requireNonNull(decayRate, "decayRate must not be null!!!");
    }

    LocalDateTime getCreateDate() {
        return createDate;
    }

    private void setCreateDate(LocalDateTime createDate) { this.createDate = Objects.requireNonNull(createDate, "createDate must not be null!!!"); }

    public double getShelfLifeValue() {
        return shelfLifeValue;
    }

    private void setShelfLifeValue(double shelfLifeValue) { this.shelfLifeValue = Objects.requireNonNull(shelfLifeValue, "shelfLifeValue must not be null!!!"); }

    public boolean isValidValidForDelivery(int shelfDecayModifier) {

        long orderAge = DateTimeUtil.calculateAgeInSeconds(this.getCreateDate(), LocalDateTime.now());

        Double value = (((this.getShelfLife() - this.getDecayRate() *  orderAge) * shelfDecayModifier) / this.getShelfLife());

        this.setShelfLifeValue(value);

        return value > 0;
    }

    @Override
    public String toString() {
        return JsonParserUtil.toJson(this);
        /*return "{ id: "+ this.id + "," +
                "name: "+ this.name + "," +
                "temp: "+ this.temp + "," +
                "shelfLife: "+ this.shelfLife + "," +
                "decayRate: "+ this.decayRate + "," +
                "createDate: "+ this.createDate + "," +
                "shelfLifeValue: "+ this.shelfLifeValue +" }";*/
    }
}
