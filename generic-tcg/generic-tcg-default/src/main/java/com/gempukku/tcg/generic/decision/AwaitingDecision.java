package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;

import java.util.Map;

public interface AwaitingDecision {
    public void processAnswer(String answer) throws InvalidAnswerException;

    public String getType();

    public Map<String, String> getParameters(GameObjects gameState);
}
