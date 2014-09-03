package com.gempukku.tcg.generic.action;

public interface GameActionContext {
    public void setProperty(String name, String value);

    public String getValue(String value);
}
