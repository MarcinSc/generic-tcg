package com.gempukku.tcg.solforge.trigger;

import com.gempukku.tcg.generic.action.EffectsGameObjectActionSource;
import com.gempukku.tcg.generic.effect.DestroyGameObjectEffect;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.filter.SameObjectFilter;
import com.gempukku.tcg.solforge.SimpleSolforgeTriggerBlueprint;
import com.gempukku.tcg.solforge.SolforgeObjects;
import com.gempukku.tcg.solforge.SolforgeTriggerBlueprint;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

public class SolforgeTrigger implements FactoryBean<SolforgeTriggerBlueprint> {
    private List<GameObjectEffect> _announceGameObjectEffects;
    private List<GameObjectEffect> _resolveGameObjectEffects;

    public void setAnnounceGameObjectEffects(List<GameObjectEffect> announceGameObjectEffects) {
        _announceGameObjectEffects = announceGameObjectEffects;
    }

    public void setResolveGameObjectEffects(List<GameObjectEffect> resolveGameObjectEffects) {
        _resolveGameObjectEffects = resolveGameObjectEffects;
    }

    @Override
    public SolforgeTriggerBlueprint getObject() throws Exception {
        SimpleSolforgeTriggerBlueprint simpleSolforgeTriggerBlueprint = new SimpleSolforgeTriggerBlueprint();
        simpleSolforgeTriggerBlueprint.setStackActionSource(createStackActionSource());
        simpleSolforgeTriggerBlueprint.setResolveActionSource(createResolveActionSource());
        return simpleSolforgeTriggerBlueprint;
    }

    private EffectsGameObjectActionSource createStackActionSource() {
        EffectsGameObjectActionSource announceActionSource = new EffectsGameObjectActionSource();
        announceActionSource.setEffects(_announceGameObjectEffects);
        return announceActionSource;
    }

    private EffectsGameObjectActionSource createResolveActionSource() {
        EffectsGameObjectActionSource announceActionSource = new EffectsGameObjectActionSource();
        announceActionSource.setEffects(_resolveGameObjectEffects);
        DestroyGameObjectEffect removeGameObjectEffect = new DestroyGameObjectEffect();
        removeGameObjectEffect.setGameObjectManager(SolforgeObjects.GAME_OBJECT_MANAGER.getName());
        removeGameObjectEffect.setZone("stack");
        removeGameObjectEffect.setGameObjectFilter(new SameObjectFilter());
        announceActionSource.addEffect(removeGameObjectEffect);
        return announceActionSource;
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleSolforgeTriggerBlueprint.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
