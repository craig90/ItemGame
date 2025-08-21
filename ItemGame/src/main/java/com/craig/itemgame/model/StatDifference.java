package com.craig.itemgame.model;

public class StatDifference {

    private ItemStat stat;
    private boolean isOldOnlyStat;
    private boolean isBetter;
    private double difference;

    public StatDifference (ItemStat stat) {
        this.stat = stat;
        this.isBetter = false;
        this.isOldOnlyStat = false;
        this.difference = 0.0;
    }

    public ItemStat getStat() {
        return this.stat;
    }

    public boolean isBetter() {
        return this.isBetter;
    }

    public double getDifference() {
        return this.difference;
    }

    public boolean isOldStatOnly() {
        return this.isOldOnlyStat;
    }

    public void setIsBetterStat(boolean isBetter) {
        this.isBetter = isBetter;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public void setIsOldStatOnly(boolean isOldOnlyStat) {
        this.isOldOnlyStat = isOldOnlyStat;
    }

}
