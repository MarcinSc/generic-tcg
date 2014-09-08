package com.gempukku.tcg.generic.decision;

public abstract class ChooseNumberDecision implements AwaitingDecision {
    private String _message;
    private int _min;
    private int _max;

    public ChooseNumberDecision(String message, int min, int max) {
        _message = message;
        _min = min;
        _max = max;
    }

    public String getMessage() {
        return _message;
    }

    public int getMin() {
        return _min;
    }

    public int getMax() {
        return _max;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        try {
            int value = Integer.parseInt(answer);
            if (value < _min || value > _max)
                throw new InvalidAnswerException("Number not in allowed range");
            numberChosen(value);
        } catch (NumberFormatException exp) {
            throw new InvalidAnswerException("Unable to parse answer as an integer");
        }
    }

    protected abstract void numberChosen(int value);
}
