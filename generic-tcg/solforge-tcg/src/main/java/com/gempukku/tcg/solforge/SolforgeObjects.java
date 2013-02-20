package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.PerPlayerObject;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.event.GameEventCollector;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.modifier.GameModifierEngine;
import com.gempukku.tcg.generic.object.Counter;
import com.gempukku.tcg.generic.object.Property;
import com.gempukku.tcg.generic.object.Zone;

public class SolforgeObjects {
    public static final SolforgeObject<GameEventEngine> GAME_EVENT_ENGINE = new SolforgeObject<GameEventEngine>("gameEventEngine");
    public static final SolforgeObject<GameModifierEngine> GAME_MODIFIER_ENGINE = new SolforgeObject<GameModifierEngine>("gameModifierEngine");
    public static final SolforgeObject<PerPlayerObject<Counter>> HEALTH_COUNTER = new SolforgeObject<PerPlayerObject<Counter>>("healthCounter");
    public static final SolforgeObject<PerPlayerObject<Counter>> LEVEL_COUNTER = new SolforgeObject<PerPlayerObject<Counter>>("levelCounter");

    public static final SolforgeObject<PerPlayerObject<Zone>> DECK_ZONE = new SolforgeObject<PerPlayerObject<Zone>>("deck");
    public static final SolforgeObject<PerPlayerObject<Zone>> DISCARD_ZONE = new SolforgeObject<PerPlayerObject<Zone>>("discard");
    public static final SolforgeObject<PerPlayerObject<Zone>> HAND_ZONE = new SolforgeObject<PerPlayerObject<Zone>>("hand");
    public static final SolforgeObject<Zone> PLAY_ZONE = new SolforgeObject<Zone>("play");

    public static final SolforgeObject<Property> PLAYER_TURN = new SolforgeObject<Property>("playerTurn");

    public static final SolforgeObject<GameEventCollector> TURN_EVENT_COLLECTOR = new SolforgeObject<GameEventCollector>("turnEventCollector");
    public static final SolforgeObject<PerPlayerObject<DecisionHolder>> DECISION_HOLDER = new SolforgeObject<PerPlayerObject<DecisionHolder>>("decisionHolder");

    public static <T> T extractGameObject(GameState gameState, SolforgeObject<T> object) {
        return (T) gameState.getGameObject(object._name);
    }

    public static <T> T extractPlayerObject(GameState gameState, SolforgeObject<PerPlayerObject<T>> object, String player) {
        return (T) gameState.getPlayerObject(player, object._name);
    }

    private static class SolforgeObject<T> {
        private String _name;

        private SolforgeObject(String name) {
            _name = name;
        }
    }
}
