package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.player.PlayerManager;

import java.util.Map;

public class StartPhaseGameFlow implements GameFlow {
    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        String subPhase = DigitalObjects.getSimpleFlag(gameObjects, "subPhase");
        if (subPhase == null) {
            subPhase = "drawCards";
            DigitalObjects.setSimpleFlag(gameObjects, "subPhase", subPhase);
        }

        if (subPhase.equals("drawCards")) {
            processDrawCards(gameObjects);
        } else if (subPhase.equals("discardingCards")) {
            processDiscardingCards(gameObjects);
        }

        return null;
    }

    private void processDrawCards(GameObjects gameObjects) {
        final PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);



    }

    private void processDiscardingCards(GameObjects gameObjects) {
    }
}
