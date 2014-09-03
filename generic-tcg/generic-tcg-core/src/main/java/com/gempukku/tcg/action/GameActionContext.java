package com.gempukku.tcg.action;

public interface GameActionContext {
    public void setProperty(String name, String value);

    public String getValue(String value);
}
