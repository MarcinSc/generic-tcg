package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;

import java.util.Collections;
import java.util.Map;

public abstract class YesNoDecision implements AwaitingDecision {
    private String _question;

    protected YesNoDecision(String question) {
        _question = question;
    }

    @Override
    public String getType() {
        return "YES_NO";
    }

    @Override
    public Map<String, String> getParameters(GameObjects gameState) {
        return Collections.singletonMap("message", _question);
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        if (answer.equals("yes"))
            yes();
        else if (answer.equals("no"))
            no();
        else
            throw new InvalidAnswerException("Decision requires yes/no answer");
    }

    protected void yes() {

    }

    protected void no() {

    }
}
