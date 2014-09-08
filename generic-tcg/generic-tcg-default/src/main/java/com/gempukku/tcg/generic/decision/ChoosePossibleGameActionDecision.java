package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.generic.action.GameActionPossibility;

import java.util.List;

public abstract class ChoosePossibleGameActionDecision implements AwaitingDecision {
    private String _message;
    private List<GameActionPossibility> _gameActionPossibilities;

    protected ChoosePossibleGameActionDecision(String message, List<GameActionPossibility> gameActionPossibilities) {
        _message = message;
        _gameActionPossibilities = gameActionPossibilities;
    }

    public String getMessage() {
        return _message;
    }

    public List<GameActionPossibility> getGameActionPossibilities() {
        return _gameActionPossibilities;
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
}
