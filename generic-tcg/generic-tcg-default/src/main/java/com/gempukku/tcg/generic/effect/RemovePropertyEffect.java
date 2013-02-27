package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class RemovePropertyEffect extends GameObjectEffect {
    private String _property;

    public void setProperty(String property) {
        _property = property;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObject) {
        gameObject.removeProperty(_property);
    }
}
