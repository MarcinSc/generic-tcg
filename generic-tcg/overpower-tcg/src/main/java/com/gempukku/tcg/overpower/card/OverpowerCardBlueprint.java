package com.gempukku.tcg.overpower.card;

public class OverpowerCardBlueprint {
    private String _id;
    private String _cardType;
    private String _name;
    private int _energy;
    private int _fighting;
    private int _strength;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getCardType() {
        return _cardType;
    }

    public void setCardType(String cardType) {
        _cardType = cardType;
    }

    public int getEnergy() {
        return _energy;
    }

    public void setEnergy(int energy) {
        _energy = energy;
    }

    public int getFighting() {
        return _fighting;
    }

    public void setFighting(int fighting) {
        _fighting = fighting;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getStrength() {
        return _strength;
    }

    public void setStrength(int strength) {
        _strength = strength;
    }
}
