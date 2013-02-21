package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SolforgeCardBlueprintResolver implements BeanFactoryAware {
    private BeanFactory _beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        _beanFactory = beanFactory;
    }

    public SolforgeCardBlueprint getCardBlueprint(String blueprintId) {
        return _beanFactory.getBean(blueprintId, SolforgeCardBlueprint.class);
    }

    public GameObjectActionSource getCardActionBlueprint(String actionId) {
        return _beanFactory.getBean(actionId, GameObjectActionSource.class);
    }
}
