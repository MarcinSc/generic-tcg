package com.gempukku.tcg.generic.action;

public interface GameActionContext {
    public void setAttribute(String name, String value);

    public void removeAttribute(String name);

    public String getAttribute(String name);
}
