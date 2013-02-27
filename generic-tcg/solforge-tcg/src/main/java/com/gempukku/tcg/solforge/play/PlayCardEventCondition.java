package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventCondition;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.util.GameObjectUtils;

public class PlayCardEventCondition implements GameObjectEventCondition {
    private String _playedPlayer;
    private GameObjectFilter _playedFilter;

    public void setPlayedPlayer(String playedPlayer) {
        _playedPlayer = playedPlayer;
    }

    public void setPlayedFilter(GameObjectFilter playedFilter) {
        _playedFilter = playedFilter;
    }

    @Override
    public boolean matches(GameState gameState, GameObject conditionOn, GameEvent event) {
        if (event.getType().equals(PlayedCardEvent.TYPE)) {
            PlayedCardEvent playedCardEvent = (PlayedCardEvent) event;
            if (_playedPlayer != null) {
                if (!playedCardEvent.getPlayer().equals(GameObjectUtils.resolveObjectProperty(conditionOn, _playedPlayer)))
                    return false;
            }
            if (_playedFilter != null) {
                if (!_playedFilter.matches(gameState, playedCardEvent.getGameObject()))
                    return false;
            }
            return true;
        }
        return false;
    }
}
