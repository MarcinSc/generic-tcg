package com.gempukku.tcg.overpower.card;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.card.CardManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class OverpowerCardManager implements ApplicationContextAware {
    private ApplicationContext _applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
    }

    public OverpowerCardBlueprint getCardBlueprint(GameObjects gameObjects, DigitalObject card) {
        final CardManager cardManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.CARD_MANAGER);
        return _applicationContext.getBean("card." + cardManager.getBlueprintId(card), OverpowerCardBlueprint.class);
    }
}
