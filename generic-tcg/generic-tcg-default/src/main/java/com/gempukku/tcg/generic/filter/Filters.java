package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public class Filters {
    public static DigitalObjectFilter and(final DigitalObjectFilter ... filters) {
        return new DigitalObjectFilter() {
            @Override
            public boolean accept(GameObjects gameObjects, GameEffectContext context, DigitalObject object) {
                for (DigitalObjectFilter filter : filters) {
                    if (!filter.accept(gameObjects, context, object))
                        return false;
                }

                return true;
            }
        };
    }

    public static DigitalObjectFilter or(final DigitalObjectFilter ... filters) {
        return new DigitalObjectFilter() {
            @Override
            public boolean accept(GameObjects gameObjects, GameEffectContext context, DigitalObject object) {
                for (DigitalObjectFilter filter : filters) {
                    if (filter.accept(gameObjects, context, object))
                        return true;
                }

                return false;
            }
        };
    }
}
