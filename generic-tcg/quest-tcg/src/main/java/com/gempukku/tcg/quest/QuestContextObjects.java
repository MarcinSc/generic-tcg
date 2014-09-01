package com.gempukku.tcg.quest;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.decision.DecisionHolder;

public class QuestContextObjects {
    public static final QuestObject<DigitalEnvironment> DIGITAL_ENVIRONMENT = new QuestObject<DigitalEnvironment>("digitalEnvironment");
    public static final QuestObject<DecisionHolder> DECISION_HOLDER = new QuestObject<DecisionHolder>("decisionHolder");
    public static final QuestObject<TurnOrder> TURN_ORDER = new QuestObject<TurnOrder>("turnOrder");

    public static <T> T extractGameObject(GameState gameState, QuestObject<T> object) {
        return (T) gameState.getGameObject(object._name);
    }

    private static class QuestObject<T> {
        private String _name;

        private QuestObject(String name) {
            _name = name;
        }
    }
}
