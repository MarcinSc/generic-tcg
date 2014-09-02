package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GamePlayerStateInitializing;
import com.gempukku.tcg.generic.GenericContextObjects;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlayerDigitalObjectStackManager implements GamePlayerStateInitializing {
    private String _stackName;

    public void setStackName(String stackName) {
        _stackName = stackName;
    }

    @Override
    public void setupGameState(GameObjects gameObjects, String player) {
        if (DigitalObjects.extractPlayerObject(gameObjects, _stackName, player) == null) {
            final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", _stackName);
            attrs.put("owner", player);
            digitalEnvironment.createObject(attrs);
        }
    }

    public void putOnTop(GameObjects gameObjects, String player, DigitalObject ... object) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerStack)));
        for (DigitalObject obj : object) {
            ids.add(obj.getId());
        }

        digitalEnvironment.updateObject(playerStack.getId(), Collections.singletonMap("ids", convertToString(ids)), false);
    }

    public DigitalObject removeTopObject(GameObjects gameObjects, String player) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerStack)));
        if (ids.size() == 0) {
            return null;
        }
        final String removedId = ids.remove(ids.size() - 1);
        digitalEnvironment.updateObject(playerStack.getId(), Collections.singletonMap("ids", convertToString(ids)), false);

        return digitalEnvironment.getObjectById(removedId);
    }

    public List<DigitalObject> getDigitalObjectsInStack(GameObjects gameObjects, String player) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        List<DigitalObject> result = new LinkedList<DigitalObject>();
        for (String id : getIds(playerStack)) {
            result.add(digitalEnvironment.getObjectById(id));
        }
        return result;
    }

    public void removeAllObjectsInStack(GameObjects gameObjects, String player) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        digitalEnvironment.updateObject(playerStack.getId(), Collections.singletonMap("ids", ""), false);
    }

    public DigitalObject removeObjectFromStack(GameObjects gameObjects, String player, String id) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerStack)));
        final boolean removed = ids.remove(id);
        if (!removed)
            return null;

        digitalEnvironment.updateObject(playerStack.getId(), Collections.singletonMap("ids", convertToString(ids)), false);

        return digitalEnvironment.getObjectById(id);
    }

    public void shuffleItemsInStack(GameObjects gameObjects, String player) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject playerStack = DigitalObjects.extractPlayerObject(gameObjects, _stackName, player);
        final ArrayList<String> ids = new ArrayList<String>(Arrays.asList(getIds(playerStack)));
        Collections.shuffle(ids);

        digitalEnvironment.updateObject(playerStack.getId(), Collections.singletonMap("ids", convertToString(ids)), false);
    }

    private String[] getIds(DigitalObject playerStack) {
        final String ids = playerStack.getAttributes().get("ids");
        if (ids == null)
            return new String[0];
        return ids.split(",");
    }

    private String convertToString(ArrayList<String> ids) {
        if (ids.size() == 0)
            return null;
        return StringUtils.join(ids, ",");
    }
}