package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.object.GameObject;

public class PlayCardActionPossibility implements GameActionPossibility {
    private GameObject _object;
    private String _text;

    public PlayCardActionPossibility(GameObject object, String text) {
        _object = object;
        _text = text;
    }

    @Override
    public GameAction createGameAction(GameState gameState) {
        return new StackObjectAction(_object);
    }

    @Override
    public String getText(GameState gameState) {
        return _text;
    }

    @Override
    public String getAttachedObjectId(GameState gameState) {
        return _object.getIdentifier();
    }
}
