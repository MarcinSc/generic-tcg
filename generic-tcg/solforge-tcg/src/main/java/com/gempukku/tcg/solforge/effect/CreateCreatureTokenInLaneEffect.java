package com.gempukku.tcg.solforge.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.solforge.Solforge;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.HashMap;
import java.util.Map;

public class CreateCreatureTokenInLaneEffect extends GameObjectEffect {
    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObject) {
        final Zone play = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);
        final KeywordManager keywordManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.KEYWORD_MANAGER);
        Map<String, String> newProperties = new HashMap<String, String>(gameObject.getAllProperties());
        newProperties.put(Solforge.Properties.TYPE, "token");
        newProperties.put(Solforge.Properties.DAMAGE, "0");
        newProperties.put(Solforge.Properties.OFFENSIVE, String.valueOf(keywordManager.hasKeyword(gameState, gameObject, Solforge.Keywords.SWIFTNESS)));
        final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
        gameObjectManager.createObjectInZone(play, newProperties);
    }
}
