package com.gempukku.tcg.solforge.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.filter.AndFilter;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.filter.GameObjectPropertyFilter;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.util.GameObjectUtils;
import com.gempukku.tcg.solforge.Solforge;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.Arrays;

public class DestroyCreatureAlreadyInLaneEffect extends GameObjectEffect {
    private String _laneProperty= Solforge.Properties.LANE;
    private String _owner;

    public void setLaneProperty(String laneProperty) {
        _laneProperty = laneProperty;
    }

    public void setOwner(String owner) {
        _owner = owner;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObject) {
        final String lane = gameObject.getProperty(_laneProperty);
        final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
        final Zone play = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);

        AndFilter creatureAlreadyInLane = new AndFilter();
        creatureAlreadyInLane.setFilters(Arrays.<GameObjectFilter>asList(
                new GameObjectPropertyFilter(Solforge.Properties.OWNER, GameObjectUtils.resolveObjectProperty(gameObject, _owner)),
                new GameObjectPropertyFilter(Solforge.Properties.CARD_TYPE, "creature"),
                new GameObjectPropertyFilter(Solforge.Properties.LANE, lane)));
        final GameObject creatureInLane = gameObjectManager.findFirstObjectMatching(play, gameState, gameObject, creatureAlreadyInLane);
        if (creatureInLane != null)
            gameObjectManager.destroyObjectInZone(play, creatureInLane);
    }
}
