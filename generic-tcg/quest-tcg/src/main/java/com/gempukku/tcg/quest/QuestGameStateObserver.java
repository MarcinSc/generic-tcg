package com.gempukku.tcg.quest;

import com.gempukku.tcg.AwaitingDecision;
import org.w3c.dom.Element;

import java.util.LinkedList;
import java.util.List;

public class QuestGameStateObserver {
    private String _player;
    private List<Element> _elements = new LinkedList<Element>();
    private AwaitingDecision _playerDecision;

    public QuestGameStateObserver(String player) {
        _player = player;
    }

    public String getPlayer() {
        return _player;
    }

    public void setPlayerDecision(AwaitingDecision playerDecision) {
        _playerDecision = playerDecision;
    }

    public AwaitingDecision getPlayerDecision() {
        return _playerDecision;
    }
}
