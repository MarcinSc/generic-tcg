package com.gempukku.tcg.generic.decision;

public abstract class YesNoDecision implements AwaitingDecision {
    private String _question;

    protected YesNoDecision(String question) {
        _question = question;
    }

    public String getMessage() {
        return _question;
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
