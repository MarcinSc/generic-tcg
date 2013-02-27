package com.gempukku.tcg.solforge.move;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.EffectsGameObjectActionPossibilitySource;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameEventCollector;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.solforge.Solforge;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MoveActionModifier implements ActionModifier {
    private EffectsGameObjectActionPossibilitySource _effects;

    public void setEffects(EffectsGameObjectActionPossibilitySource effects) {
        _effects = effects;
    }

    @Override
    public Collection<GameActionPossibility> getPossibleActions(final GameState gameState) {
        // Nothing is on stack, there is no waiting triggers
        if (SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE).getGameObjects().size() == 0
                && SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE).getGameObjects().size() == 0) {
            final Zone playZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);
            final KeywordManager keywordManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.KEYWORD_MANAGER);
            final GameEventCollector turnEventCollector = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_EVENT_COLLECTOR);

            final Set<GameActionPossibility> possibleMoveActions = new HashSet<GameActionPossibility>();
            SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER)
                    .visitGameObjects(playZone,
                            new GameObjectVisitor() {
                                @Override
                                public boolean visitGameObject(Zone zone, GameObject gameObject) {
                                    final String offensive = gameObject.getProperty(Solforge.Properties.OFFENSIVE);
                                    if (offensive != null && offensive.equals("true")
                                            && keywordManager.hasKeyword(gameState, gameObject, Solforge.Keywords.MOVE)
                                            && !hasMoved(turnEventCollector, gameObject)
                                            && _effects.isPlayable(gameState, gameObject))
                                        possibleMoveActions.add(_effects.getGameActionPossibility(gameState, gameObject));
                                    return false;
                                }
                            });

            return possibleMoveActions;
        }
        return null;
    }

    private boolean hasMoved(GameEventCollector turnEventCollector, GameObject gameObject) {
        for (GameEvent gameEvent : turnEventCollector.getGameEvents()) {
            if (gameEvent.getType().equals(MoveEvent.TYPE))
                if (((MoveEvent) gameEvent).getId().equals(gameObject.getIdentifier()))
                    return true;
        }
        return false;
    }
}
