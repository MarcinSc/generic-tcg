package com.gempukku.tcg.generic.zone.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GamePlayerStateInitializing;
import com.gempukku.tcg.generic.GameStateInitializing;
import com.gempukku.tcg.generic.GenericContextObjects;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DigitalObjectZoneManager implements GamePlayerStateInitializing, GameStateInitializing {
    private String _zone;
    private boolean _playerOnly;

    public void setPlayerOnly(boolean playerOnly) {
        _playerOnly = playerOnly;
    }

    public void setZone(String zone) {
        _zone = zone;
    }

    @Override
    public void setupGameState(GameObjects gameObjects) {
        if (!_playerOnly && DigitalObjects.extractObject(gameObjects, _zone) == null) {
            final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", _zone);
            digitalEnvironment.createObject(attrs);
        }
    }

    @Override
    public void setupGameState(GameObjects gameObjects, String player) {
        if (_playerOnly && DigitalObjects.extractPlayerObject(gameObjects, _zone, player) == null) {
            final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", _zone);
            attrs.put("owner", player);
            digitalEnvironment.createObject(attrs);
        }
    }

    private void validatePlayer(String player) {
        if (_playerOnly == (player == null))
            throw new IllegalArgumentException("Attempt to address zone with incorrect player setting");
    }

    private DigitalObject getZone(GameObjects gameObjects, String player) {
        if (player != null)
            return DigitalObjects.extractPlayerObject(gameObjects, _zone, player);
        else
            return DigitalObjects.extractObject(gameObjects, _zone);
    }

    public void putOnTop(GameObjects gameObjects, String player, DigitalObject... object) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerZone)));
        for (DigitalObject obj : object) {
            ids.add(obj.getId());
        }

        digitalEnvironment.updateObject(playerZone.getId(), Collections.singletonMap("ids", convertToString(ids)), false);
    }

    public DigitalObject removeTopObject(GameObjects gameObjects, String player) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerZone)));
        if (ids.size() == 0) {
            return null;
        }
        final String removedId = ids.remove(ids.size() - 1);
        digitalEnvironment.updateObject(playerZone.getId(), Collections.singletonMap("ids", convertToString(ids)), false);

        return digitalEnvironment.getObjectById(removedId);
    }

    public DigitalObject getTopObject(GameObjects gameObjects, String player) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        String[] ids = getIds(playerZone);
        if (ids.length == 0)
            return null;
        return digitalEnvironment.getObjectById(ids[ids.length-1]);
    }

    public List<DigitalObject> getDigitalObjectsInZone(GameObjects gameObjects, String player) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        List<DigitalObject> result = new LinkedList<DigitalObject>();
        for (String id : getIds(playerZone)) {
            result.add(digitalEnvironment.getObjectById(id));
        }
        return result;
    }

    public void removeAllObjectsInZone(GameObjects gameObjects, String player) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        digitalEnvironment.updateObject(playerZone.getId(), Collections.singletonMap("ids", ""), false);
    }

    public DigitalObject removeObjectFromZone(GameObjects gameObjects, String player, String id) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerZone)));
        final boolean removed = ids.remove(id);
        if (!removed)
            return null;

        digitalEnvironment.updateObject(playerZone.getId(), Collections.singletonMap("ids", convertToString(ids)), false);

        return digitalEnvironment.getObjectById(id);
    }

    public void shuffleItemsInZone(GameObjects gameObjects, String player) {
        validatePlayer(player);
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerZone = getZone(gameObjects, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerZone)));
        Collections.shuffle(ids);

        digitalEnvironment.updateObject(playerZone.getId(), Collections.singletonMap("ids", convertToString(ids)), false);
    }

    private String[] getIds(DigitalObject playerZone) {
        final String ids = playerZone.getAttributes().get("ids");
        if (ids == null)
            return new String[0];
        return com.gempukku.tcg.generic.util.StringUtils.correctSplit(ids, ",");
    }

    private String convertToString(ArrayList<String> ids) {
        if (ids.size() == 0)
            return null;
        return StringUtils.join(ids, ",");
    }
}
