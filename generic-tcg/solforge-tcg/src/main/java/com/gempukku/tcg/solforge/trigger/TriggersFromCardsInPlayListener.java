package com.gempukku.tcg.solforge.trigger;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameEventListener;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.solforge.SolforgeCardBlueprint;
import com.gempukku.tcg.solforge.SolforgeCardLevelBlueprint;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.List;

public class TriggersFromCardsInPlayListener implements GameEventListener {
    @Override
    public void processGameEvent(final GameState gameState, final GameEvent gameEvent) {
        final Zone inPlay = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER)
                .visitGameObjects(inPlay,
                        new GameObjectVisitor() {
                            @Override
                            public boolean visitGameObject(Zone zone, GameObject gameObject) {
                                final String blueprintId = gameObject.getProperty("blueprintId");
                                final SolforgeCardBlueprint cardBlueprint = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                                        .getCardBlueprint(blueprintId);
                                final SolforgeCardLevelBlueprint levelBlueprint = cardBlueprint.getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty("level")));
                                final List<TriggeredEffect> triggeredEffects = levelBlueprint.getTriggeredEffects();
                                if (triggeredEffects != null) {
                                    for (TriggeredEffect triggeredEffect : triggeredEffects)
                                        triggeredEffect.processPossibleEvent(gameState, gameObject, gameEvent);
                                }
                                return false;
                            }
                        });
    }
}
