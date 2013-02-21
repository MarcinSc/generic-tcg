package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.action.GameObjectActionSource;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class PlayCardActionSource implements GameObjectActionSource {
    @Override
    public GameActionPossibility getGameActionPossibility(GameState gameState, GameObject fromObject) {
        final String name = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(fromObject.getProperty("blueprintId")).getName();
        return new PlayCardActionPossibility(fromObject, "Play " + name);
    }

    @Override
    public boolean isPlayable(GameState gameState, GameObject fromObject) {
        return true;
    }
}
