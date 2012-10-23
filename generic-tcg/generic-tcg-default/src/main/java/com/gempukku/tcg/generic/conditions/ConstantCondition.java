package com.gempukku.tcg.generic.conditions;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.Condition;
import org.springframework.beans.factory.annotation.Required;

public class ConstantCondition implements Condition {
    private boolean _value;

    @Required
    public void setValue(boolean value) {
        _value = value;
    }

    @Override
    public boolean isTrue(GameState gameState) {
        return _value;
    }
}
