package com.gempukku.tcg.generic.flow;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

import java.util.Collections;

public class ObjectGameEffectContext implements GameEffectContext {
    private GameObjects _gameObjects;
    private DigitalObject _digitalObject;

    public ObjectGameEffectContext(GameObjects gameObjects, DigitalObject digitalObject) {
        _gameObjects = gameObjects;
        _digitalObject = digitalObject;
    }

    @Override
    public void setAttribute(String name, String value) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        digitalEnvironment.updateObject(_digitalObject.getId(), Collections.singletonMap(name, value), false);
    }

    @Override
    public void removeAttribute(String name) {
        setAttribute(name, null);
    }

    @Override
    public String getAttribute(String name) {
        return _digitalObject.getAttributes().get(name);
    }
}
