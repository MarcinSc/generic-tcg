package com.gempukku.tcg.generic.effect;

public interface GameEffectContext {
    public void setAttribute(String name, String value);

    public void removeAttribute(String name);

    public String getAttribute(String name);
}
