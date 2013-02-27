package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionSource;

public class SimpleSolforgeTriggerBlueprint implements SolforgeTriggerBlueprint {
    private GameObjectActionSource _stackActionSource;
    private GameObjectActionSource _resolveActionSource;

    public void setStackActionSource(GameObjectActionSource stackActionSource) {
        _stackActionSource = stackActionSource;
    }

    public void setResolveActionSource(GameObjectActionSource resolveActionSource) {
        _resolveActionSource = resolveActionSource;
    }

    @Override
    public GameObjectActionSource getAfterStackActionSource() {
        return _stackActionSource;
    }

    @Override
    public GameObjectActionSource getResolveActionSource() {
        return _resolveActionSource;
    }
}
