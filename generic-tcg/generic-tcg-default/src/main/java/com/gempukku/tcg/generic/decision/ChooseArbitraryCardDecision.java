package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.generic.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ChooseArbitraryCardDecision implements AwaitingDecision {
    private String _message;
    private List<String> _cardBlueprints;
    private int _min;
    private int _max;

    public ChooseArbitraryCardDecision(String message, List<String> cardBlueprints, int min, int max) {
        _message = message;
        _cardBlueprints = cardBlueprints;

        _min = Math.min(min, cardBlueprints.size());
        _max = Math.min(max, cardBlueprints.size());
    }

    public String getMessage() {
        return _message;
    }

    public List<String> getCardBlueprints() {
        return _cardBlueprints;
    }

    public int getMin() {
        return _min;
    }

    public int getMax() {
        return _max;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        final String[] split = StringUtils.correctSplit(answer, ",");
        if (split.length < _min || split.length > _max)
            throw new InvalidAnswerException("Invalid number of objects selected");

        try {
            List<String> blueprintIds = new ArrayList<String>();
            List<Integer> indices = new ArrayList<Integer>();
            Set<Integer> indiceSelected = new HashSet<Integer>();

            for (String indexStr : split) {
                final int index = Integer.parseInt(indexStr);
                if (!indiceSelected.add(index))
                    throw new InvalidAnswerException("Answer contains the same index more than once");
                if (index<0 || index>=_cardBlueprints.size())
                    throw new InvalidAnswerException("Item index outside of allowed bounds");
                blueprintIds.add(_cardBlueprints.get(index));
                indices.add(index);
            }
            objectsChosen(indices, blueprintIds);
        } catch (NumberFormatException exp) {
            throw new InvalidAnswerException("Invalid item index presented");
        }
    }

    protected abstract void objectsChosen(List<Integer> indices, List<String> blueprintId);
}
