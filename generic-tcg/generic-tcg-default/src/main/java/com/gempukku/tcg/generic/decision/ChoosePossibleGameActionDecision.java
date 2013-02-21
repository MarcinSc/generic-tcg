package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.generic.action.GameActionPossibility;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ChoosePossibleGameActionDecision implements AwaitingDecision {
    private String _message;
    private List<GameActionPossibility> _gameActionPossibilities;

    protected ChoosePossibleGameActionDecision(String message, List<GameActionPossibility> gameActionPossibilities) {
        _message = message;
        _gameActionPossibilities = gameActionPossibilities;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("message", _message);

        int index = 0;
        for (GameActionPossibility gameActionPossibility : _gameActionPossibilities) {
            String attachedId = gameActionPossibility.getAttachedObjectId();
            params.put(String.valueOf(index), (attachedId != null ? attachedId : "") + "," + gameActionPossibility.getText());
            index++;
        }

        return params;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        try {
            int index = Integer.parseInt(answer);
            if (index >= 0 && index < _gameActionPossibilities.size())
                gameActionChosen(_gameActionPossibilities.get(index));
            else
                throw new InvalidAnswerException("Action with this index does not exist");
        } catch (NumberFormatException exp) {
            throw new InvalidAnswerException("Invalid action index: " + answer);
        }
    }

    protected abstract void gameActionChosen(GameActionPossibility gameActionPossibility);

    @Override
    public String getType() {
        return "CHOOSE_POSSIBLE_ACTION";
    }
}
