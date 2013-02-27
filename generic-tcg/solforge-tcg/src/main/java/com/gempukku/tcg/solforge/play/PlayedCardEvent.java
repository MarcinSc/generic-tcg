package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;

public class PlayedCardEvent extends GameEvent {
    public static final String TYPE = "playedCard";
    private GameObject _gameObject;
    private String _player;

    public PlayedCardEvent(GameObject gameObject, String player) {
        super(TYPE);
        _gameObject = gameObject;
        _player = player;
    }

    public GameObject getGameObject() {
        return _gameObject;
    }

    public String getPlayer() {
        return _player;
    }
}
