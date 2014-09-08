package com.gempukku.tcg.overpower.card;

import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.overpower.filter.factory.OverpowerCardBlueprintFilterFactory;
import org.springframework.beans.factory.InitializingBean;

public class OverpowerCardBlueprint implements InitializingBean {
    private String _cardType;
    private String _name;
    private int _energy;
    private int _fighting;
    private int _strength;
    private OverpowerCardBlueprintFilterFactory _placeOnFilterFactory;
    private DigitalObjectFilter _placeOnFilter;

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

    public DigitalObjectFilter getPlaceOnFilter() {
        return _placeOnFilter;
    }

    public void setPlaceOnFilterFactory(OverpowerCardBlueprintFilterFactory placeOnFilterFactory) {
        _placeOnFilterFactory = placeOnFilterFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (_placeOnFilterFactory != null)
            _placeOnFilter = _placeOnFilterFactory.createFor(this);
    }
}
