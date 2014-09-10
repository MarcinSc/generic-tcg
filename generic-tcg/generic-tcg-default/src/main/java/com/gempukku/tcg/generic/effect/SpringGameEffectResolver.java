package com.gempukku.tcg.generic.effect;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringGameEffectResolver implements GameEffectResolver, ApplicationContextAware {
    private ApplicationContext _applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
    }

    @Override
    public GameEffect resolveGameEffect(String id) {
        return _applicationContext.getBean("effect."+id, GameEffect.class);
    }
}
