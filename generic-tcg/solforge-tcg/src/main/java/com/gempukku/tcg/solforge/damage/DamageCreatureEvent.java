package com.gempukku.tcg.solforge.damage;

import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;

public class DamageCreatureEvent extends GameEvent {
    public static final String TYPE = "damageCreature";

    private GameObject _from;
    private GameObject _to;
    private int _amount;

    public DamageCreatureEvent(GameObject from, GameObject to, int amount) {
        super(TYPE);
        _from = from;
        _to = to;
        _amount = amount;
    }
}
