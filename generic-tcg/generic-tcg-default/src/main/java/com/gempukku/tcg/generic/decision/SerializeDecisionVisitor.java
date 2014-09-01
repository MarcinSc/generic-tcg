package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SerializeDecisionVisitor implements AwaitingDecisionVisitor {
    private Map<String, String> _result = new HashMap<String, String>();
    private GameObjects _gameObjects;

    public SerializeDecisionVisitor(GameObjects gameObjects) {
        _gameObjects = gameObjects;
    }

    @Override
    public void visit(ChooseDigitalObjectDecision decision) {
        _result.put("decisionType", "chooseDigitialObject");
        _result.put("message", decision.getMessage());
        _result.put("ids", StringUtils.join(Iterators.transform(decision.getObjects().iterator(),
                new Function<DigitalObject, String>() {
                    @Override
                    public String apply(DigitalObject input) {
                        return input.getId();
                    }
                }), ","));
    }

    @Override
    public void visit(ChooseArbitraryCardDecision decision) {
        _result.put("decisionType", "chooseArbitraryCard");
        _result.put("message", decision.getMessage());
        _result.put("cardBlueprints", StringUtils.join(decision.getCardBlueprints(), ","));
    }

    @Override
    public void visit(ChoosePossibleGameActionDecision decision) {
        _result.put("decisionType", "chooseGameAction");
        _result.put("message", decision.getMessage());
        final AtomicInteger index = new AtomicInteger(0);
        _result.put("actions", StringUtils.join(Iterators.transform(decision.getGameActionPossibilities().iterator(),
                new Function<GameActionPossibility, String>() {
                    @Override
                    public String apply(GameActionPossibility input) {
                        final String attachedObjectId = input.getAttachedObjectId(_gameObjects);
                        return index.getAndIncrement() + "|" + ((attachedObjectId != null) ? attachedObjectId + "|" : "") + input.getText(_gameObjects);
                    }
                }), ","));
    }

    @Override
    public void visit(YesNoDecision decision) {
        _result.put("decisionType", "yesOrNo");
        _result.put("message", decision.getMessage());
    }

    public Map<String, String> getResult() {
        return _result;
    }
}
