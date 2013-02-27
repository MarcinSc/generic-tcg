package com.gempukku.tcg.solforge.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.other.Counter;
import com.gempukku.tcg.solforge.SolforgeObjects;
import com.gempukku.tcg.solforge.damage.DamageCreatureEvent;
import com.gempukku.tcg.solforge.damage.DamagePlayerEvent;
import org.apache.commons.lang.mutable.MutableObject;

public class DealDamageToTargetEffect extends GameObjectEffect {
    private IntEvaluator _damage;
    private String _targetProperty;

    public void setTargetProperty(String targetProperty) {
        _targetProperty = targetProperty;
    }

    public void setDamage(IntEvaluator damage) {
        _damage = damage;
    }

    @Override
    public void executeGameEffect(final GameState gameState, final GameObject gameObject) {
        final int damage = _damage.getValue(gameState, gameObject);
        if (damage > 0) {
            final GameEventEngine gameEventEngine = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE);
            final KeywordManager keywordManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.KEYWORD_MANAGER);
            final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
            final Zone play = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);

            final String[] targets = gameObject.getProperty(_targetProperty).split(",");
            for (String target : targets) {
                if (target.startsWith("player:")) {
                    dealDamageToPlayer(gameState, gameEventEngine, gameObject, target.substring(7), damage);
                } else if (target.startsWith("object:")) {
                    final String targetId = target.substring(7);

                    final GameObject targetObject = getTargetObject(gameObjectManager, play, targetId);
                    if (targetObject != null)
                        dealDamageToCreature(gameState, gameEventEngine, keywordManager, gameObject, targetObject, damage);
                } else {
                    throw new IllegalArgumentException("Unable to deal damage to unrecognized object type: " + target);
                }
            }
        }
    }

    private GameObject getTargetObject(GameObjectManager gameObjectManager, Zone play, final String targetId) {
        final MutableObject targetObject = new MutableObject();
        gameObjectManager.visitGameObjects(play,
                new GameObjectVisitor() {
                    @Override
                    public boolean visitGameObject(Zone zone, GameObject gameObject) {
                        if (gameObject.equals(targetId))
                            targetObject.setValue(gameObject);
                        return true;
                    }
                });
        return (GameObject) targetObject.getValue();
    }

    private void dealDamageToPlayer(GameState gameState, GameEventEngine gameEventEngine, GameObject from, String player, int amount) {
        final Counter healthCounter = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.HEALTH_COUNTER, player);
        healthCounter.setValue(healthCounter.getValue() - amount);
        gameEventEngine.emitGameEvent(
                gameState, new DamagePlayerEvent(from, player, amount));
    }

    private void dealDamageToCreature(GameState gameState, GameEventEngine gameEventEngine, KeywordManager keywordManager, GameObject from, GameObject to, int amount) {
        int armor = Math.max(0, keywordManager.getKeywordCount(gameState, to, "armor"));
        amount -= armor;
        if (amount > 0) {
            to.setProperty("damage", String.valueOf(Integer.parseInt(to.getProperty("damage")) + amount));
            gameEventEngine.emitGameEvent(
                    gameState, new DamageCreatureEvent(from, to, amount));
        }
    }
}
