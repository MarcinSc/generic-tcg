package com.gempukku.tcg.solforge.damage;

import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;

public class DamagePlayerEvent extends GameEvent {
    public static final String TYPE = "damagePlayer";

    private GameObject _from;
    private String _player;
    private int _amount;

    public DamagePlayerEvent(GameObject from, String player, int amount) {
        super(TYPE);
        _from = from;
        _player = player;
        _amount = amount;
    }
}
