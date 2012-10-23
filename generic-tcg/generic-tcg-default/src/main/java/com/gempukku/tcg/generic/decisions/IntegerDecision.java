package com.gempukku.tcg.generic.decisions;

import com.gempukku.tcg.AwaitingDecision;
import com.gempukku.tcg.InvalidDecisionException;

public abstract class IntegerDecision implements AwaitingDecision {
    private String _query;
    private int _min;
    private int _max;

    protected IntegerDecision(String query, int min, int max) {
        if (min>max)
            throw new IllegalArgumentException("Minimum can't be larger than maximum");
        _query = query;
        _min = min;
        _max = max;
    }

    @Override
    public void playerDecided(String answer) throws InvalidDecisionException {
        try {
            int value = Integer.parseInt(answer);
            if (value < _min || value > _max)
                throw new InvalidDecisionException("Answer not in acceptable range: " + _min + ":" + _max);
            playerAnswered(value);
        } catch (NumberFormatException exp) {
            throw new InvalidDecisionException("Answer not in acceptable range: " + _min + ":" + _max);
        }
    }

    protected abstract void playerAnswered(int value);

    @Override
    public String getDecisionString() {
        return "INT:" + _min + ":" + _max + ":" + _query;
    }
}
