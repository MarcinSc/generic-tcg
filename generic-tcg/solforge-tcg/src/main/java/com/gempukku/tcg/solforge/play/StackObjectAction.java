package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.solforge.PlayedCardEvent;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class StackObjectAction implements GameAction {
    private boolean _resolved;
    private GameObject _gameObject;

    public StackObjectAction(GameObject gameObject) {
        _gameObject = gameObject;
    }

    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return !_resolved;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        _resolved = true;

        // Reset pass count
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PASS_COUNTER).setValue(0);

        // Move cards from hand to stack
        final Zone hand = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.HAND_ZONE, _gameObject.getProperty("owner"));
        final Zone stack = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE);
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER)
                .moveObjectBetweenZones(
                        hand, stack, _gameObject);
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE)
                .emitGameEvent(new PlayedCardEvent());
    }
}
