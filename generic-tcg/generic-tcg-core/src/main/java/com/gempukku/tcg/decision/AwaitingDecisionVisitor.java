package com.gempukku.tcg.decision;

public interface AwaitingDecisionVisitor {
    public void visit(ChooseDigitalObjectDecision decision);

    public void visit(ChoosePossibleGameActionDecision decision);

    public void visit(ChooseArbitraryCardDecision decision);

    public void visit(YesNoDecision decision);
}
