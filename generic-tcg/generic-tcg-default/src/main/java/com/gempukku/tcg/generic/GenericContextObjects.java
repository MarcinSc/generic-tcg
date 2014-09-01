package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.card.CardManager;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.phase.PhaseManager;
import com.gempukku.tcg.generic.player.PlayerManager;

public class GenericContextObjects {
    public static final GenericObject<DigitalEnvironment> DIGITAL_ENVIRONMENT = new GenericObject<DigitalEnvironment>("digitalEnvironment");

    public static final GenericObject<CardManager> CARD_MANAGER = new GenericObject<CardManager>("cardManager");
    public static final GenericObject<DecisionHolder> DECISION_HOLDER = new GenericObject<DecisionHolder>("decisionHolder");
    public static final GenericObject<GameEventEngine> GAME_EVENT_ENGINE = new GenericObject<GameEventEngine>("gameEventEngine");
    public static final GenericObject<KeywordManager> KEYWORD_MANAGER = new GenericObject<KeywordManager>("keywordManager");
    public static final GenericObject<PlayerManager> PLAYER_MANAGER = new GenericObject<PlayerManager>("playerManager");
    public static final GenericObject<TurnOrder> TURN_ORDER = new GenericObject<TurnOrder>("turnOrder");
    public static final GenericObject<PhaseManager> PHASE_MANAGER = new GenericObject<PhaseManager>("phaseManager");

    public static <T> T extractGameObject(GameObjects gameObjects, GenericObject<T> object) {
        return (T) gameObjects.getGameObject(object._name);
    }

    private static class GenericObject<T> {
        private String _name;

        private GenericObject(String name) {
            _name = name;
        }
    }
}
