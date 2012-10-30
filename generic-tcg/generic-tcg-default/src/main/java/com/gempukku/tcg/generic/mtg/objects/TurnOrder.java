package com.gempukku.tcg.generic.mtg.objects;

import java.util.*;

public class TurnOrder {
    private List<String> _playerTurnOrder;
    private int _currentTurnIndex = -1;
    private Queue<String> _extraTurns = new LinkedList<String>();
    private List<String> _skipTurns = new LinkedList<String>();
    private String _currentPlayer;
    private Set<String> _eliminatedPlayers = new HashSet<String>();

    public void setPlayerTurnOrder(List<String> playerTurnOrder) {
        _playerTurnOrder = playerTurnOrder;
    }

    public String getCurrentPlayer() {
        return _currentPlayer;
    }

    public String nextTurn() {
        String potentialPlayer;
        while (_skipTurns.contains(potentialPlayer = getNextPotentialPlayer()))
            _skipTurns.remove(potentialPlayer);

        _currentPlayer = potentialPlayer;
        return _currentPlayer;
    }

    private String getNextPotentialPlayer() {
        String player;
        do {
            player = _extraTurns.poll();
            if (player == null) {
                _currentTurnIndex++;
                if (_currentTurnIndex == _playerTurnOrder.size())
                    _currentTurnIndex = 0;
                player = _playerTurnOrder.get(_currentTurnIndex);
            }
        } while (_eliminatedPlayers.contains(player));
        return player;
    }

    public void addExtraTurn(String player) {
        _extraTurns.add(player);
    }

    public void skipTurn(String player) {
        _skipTurns.add(player);
    }

    public void eliminatePlayer(String player) {
        _eliminatedPlayers.add(player);
    }
}
