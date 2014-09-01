package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.keyword.KeywordManager;

public class GenericContextObjects {
    public static final GenericObject<DigitalEnvironment> DIGITAL_ENVIRONMENT = new GenericObject<DigitalEnvironment>("digitalEnvironment");
    public static final GenericObject<DecisionHolder> DECISION_HOLDER = new GenericObject<DecisionHolder>("decisionHolder");
    public static final GenericObject<GameEventEngine> GAME_EVENT_ENGINE = new GenericObject<GameEventEngine>("gameEventEngine");
    public static final GenericObject<KeywordManager> KEYWORD_MANAGER = new GenericObject<KeywordManager>("keywordManager");
    public static final GenericObject<TurnOrder> TURN_ORDER = new GenericObject<TurnOrder>("turnOrder");

    public static <T> T extractGameObject(GameState gameState, GenericObject<T> object) {
        return (T) gameState.getGameObject(object._name);
    }

    private static class GenericObject<T> {
        private String _name;

        private GenericObject(String name) {
            _name = name;
        }
    }
}
