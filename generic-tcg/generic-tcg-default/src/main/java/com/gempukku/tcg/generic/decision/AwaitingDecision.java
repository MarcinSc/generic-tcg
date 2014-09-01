package com.gempukku.tcg.generic.decision;

public interface AwaitingDecision {
    public void accept(AwaitingDecisionVisitor visitor);

    public void processAnswer(String answer) throws InvalidAnswerException;
}
