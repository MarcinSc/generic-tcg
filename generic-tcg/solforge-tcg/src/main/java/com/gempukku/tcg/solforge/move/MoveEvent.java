package com.gempukku.tcg.solforge.move;

import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;

public class MoveEvent extends GameEvent {
    public static final String TYPE = "move";
    private GameObject _gameObject;
    private String _id;

    public MoveEvent(GameObject gameObject) {
        super(TYPE);
        _gameObject = gameObject;
        _id = gameObject.getIdentifier();
    }

    public String getId() {
        return _id;
    }
}
