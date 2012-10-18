package com.gempukku.tcg.generic;

public interface GameStateSerializerFactory<T> {
    public GameStateSerializer<T> createSerializer(String user);
}
