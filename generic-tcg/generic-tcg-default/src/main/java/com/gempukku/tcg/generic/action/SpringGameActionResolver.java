package com.gempukku.tcg.generic.action;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringGameActionResolver implements GameActionResolver, ApplicationContextAware {
    private ApplicationContext _applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
    }

    @Override
    public GameAction resolveGameAction(String id) {
        return _applicationContext.getBean("action."+id, GameAction.class);
    }
}
