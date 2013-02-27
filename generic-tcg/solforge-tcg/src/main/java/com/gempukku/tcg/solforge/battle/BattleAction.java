package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.other.Counter;
import com.gempukku.tcg.generic.stat.StatManager;
import com.gempukku.tcg.solforge.Solforge;
import com.gempukku.tcg.solforge.SolforgeObjects;
import com.gempukku.tcg.solforge.damage.DamageCreatureEvent;
import com.gempukku.tcg.solforge.damage.DamagePlayerEvent;
import com.gempukku.tcg.solforge.util.SolforgeObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleAction implements GameAction {
    private boolean _resolved = false;

    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return !_resolved;
    }

    @Override
    public void processNextGameEffect(final GameState gameState) {
        final Zone playZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);

        final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
        final GameEventEngine gameEventEngine = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE);

        final KeywordManager keywordManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.KEYWORD_MANAGER);
        final StatManager attackManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.ATTACK_MANAGER);
        final StatManager healthManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.HEALTH_MANAGER);

        String[] players = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_ORDER).getValue().split(",");

        final Set<Integer> fightingLanes = new HashSet<Integer>();
        gameObjectManager.visitGameObjects(playZone,
                new GameObjectVisitor() {
                    @Override
                    public boolean visitGameObject(Zone zone, GameObject gameObject) {
                        // Is a creature, offensive and not a defender
                        if (gameObject.getProperty(Solforge.Properties.CARD_TYPE).equals("creature")
                                && gameObject.getProperty(Solforge.Properties.OFFENSIVE).equals("true")
                                && !keywordManager.hasKeyword(gameState, gameObject, Solforge.Keywords.DEFENDER)) {
                            fightingLanes.add(SolforgeObjectUtil.extractLaneNumber(gameObject.getProperty(Solforge.Properties.LANE)));
                        }
                        return false;
                    }
                });

        for (int fightingLane : fightingLanes) {
            final List<GameObject> creaturesInLane = getCreaturesInLane(gameState, playZone, gameObjectManager, fightingLane);
            if (creaturesInLane.size() > 1) {
                final GameObject firstCreature = creaturesInLane.get(0);
                final GameObject secondCreature = creaturesInLane.get(1);

                executeAttackFromCreature(gameState, gameEventEngine, keywordManager, attackManager, healthManager, firstCreature, secondCreature);
                executeAttackFromCreature(gameState, gameEventEngine, keywordManager, attackManager, healthManager, secondCreature, firstCreature);
            } else if (creaturesInLane.size() == 1) {
                final GameObject firstCreature = creaturesInLane.get(0);
                int attackFirst = attackManager.getStatValue(gameState, firstCreature);
                dealDmgToPlayer(gameState, gameEventEngine, firstCreature, getOpponent(players, firstCreature.getProperty(Solforge.Properties.OWNER)), attackFirst);
            }
        }

        gameEventEngine.emitGameEvent(gameState, new BattledEvent());
        _resolved = true;
    }

    private String getOpponent(String[] players, String owner) {
        for (String player : players) {
            if (!player.equals(owner))
                return player;
        }
        throw new IllegalArgumentException("Could not find opponent for: " + owner);
    }

    private void executeAttackFromCreature(GameState gameState, GameEventEngine gameEventEngine, KeywordManager keywordManager, StatManager attackManager, StatManager healthManager, GameObject fromCreature, GameObject toCreature) {
        int attackFirst = attackManager.getStatValue(gameState, fromCreature);
        int armorSecond = Math.max(0, keywordManager.getKeywordCount(gameState, toCreature, Solforge.Keywords.ARMOR));
        attackFirst -= armorSecond;
        if (keywordManager.hasKeyword(gameState, fromCreature, Solforge.Keywords.BREAKTHROUGH)
                && fromCreature.getProperty(Solforge.Properties.OFFENSIVE).equals("true")) {
            int healthSecond = healthManager.getStatValue(gameState, toCreature);
            if (attackFirst > healthSecond) {
                dealDmgToCreature(gameState, gameEventEngine, fromCreature, toCreature, healthSecond);
                dealDmgToPlayer(gameState, gameEventEngine, fromCreature, toCreature.getProperty(Solforge.Properties.OWNER), attackFirst - healthSecond);
            } else {
                dealDmgToCreature(gameState, gameEventEngine, fromCreature, toCreature, attackFirst);
            }
        } else {
            dealDmgToCreature(gameState, gameEventEngine, fromCreature, toCreature, attackFirst);
        }
    }

    private void dealDmgToCreature(GameState gameState, GameEventEngine gameEventEngine, GameObject from, GameObject to, int amount) {
        if (amount > 0) {
            to.setProperty(Solforge.Properties.DAMAGE, String.valueOf(Integer.parseInt(to.getProperty(Solforge.Properties.DAMAGE)) + amount));
            gameEventEngine.emitGameEvent(
                    gameState, new DamageCreatureEvent(from, to, amount));
        }
    }

    private void dealDmgToPlayer(GameState gameState, GameEventEngine gameEventEngine, GameObject from, String player, int amount) {
        if (amount > 0) {
            final Counter healthCounter = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.HEALTH_COUNTER, player);
            healthCounter.setValue(healthCounter.getValue() - amount);
            gameEventEngine.emitGameEvent(
                    gameState, new DamagePlayerEvent(from, player, amount));
        }
    }

    private List<GameObject> getCreaturesInLane(final GameState gameState, Zone playZone, final GameObjectManager gameObjectManager, final int lane) {
        final List<GameObject> result = new ArrayList<GameObject>();
        gameObjectManager.visitGameObjects(playZone,
                new GameObjectVisitor() {
                    @Override
                    public boolean visitGameObject(Zone zone, GameObject gameObject) {
                        if (SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                                .getCardBlueprint(gameObject.getProperty(Solforge.Properties.BLUEPRINT_ID))
                                .getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty(Solforge.Properties.LEVEL))).getCardType().equals("creature")
                                && SolforgeObjectUtil.extractLaneNumber(gameObject.getProperty(Solforge.Properties.LANE)) == lane)
                            result.add(gameObject);
                        return false;
                    }
                });
        return result;
    }
}
