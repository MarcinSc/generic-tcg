package com.gempukku.tcg.generic.decisions;

import com.gempukku.tcg.AwaitingDecision;
import com.gempukku.tcg.InvalidDecisionException;

public class YesNoDecision implements AwaitingDecision {
    private String _query;

    public YesNoDecision(String query) {
        _query = query;
    }

    @Override
    public void playerDecided(String answer) throws InvalidDecisionException {
        if (answer.equals("yes"))
            yes();
        else if (answer.equals("no"))
            no();
        else
            throw new InvalidDecisionException("Answer not one of accepted: yes, no");
    }

    protected void yes() {

    }

    protected void no() {

    }

    @Override
    public String getDecisionString() {
        return "YN:"+_query;
    }
}
