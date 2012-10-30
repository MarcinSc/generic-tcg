package com.gempukku.tcg.quest;


import com.gempukku.tcg.AwaitingDecision;
import com.gempukku.tcg.InvalidDecisionException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class StartGameAtTest extends AbstractAtTest {
    @Test
    public void testStartGame() throws InvalidDecisionException {
        setupSampleGame();
        AwaitingDecision decision = getProcessedDecision(P1);
        assertNotNull(decision);
    }
}
