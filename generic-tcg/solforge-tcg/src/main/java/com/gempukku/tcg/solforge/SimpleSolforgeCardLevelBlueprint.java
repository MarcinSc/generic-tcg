package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionPossibilitySource;
import com.gempukku.tcg.generic.action.GameObjectActionSource;
import com.gempukku.tcg.generic.keyword.Keyword;

import java.util.List;

public class SimpleSolforgeCardLevelBlueprint implements SolforgeCardLevelBlueprint {
    private List<GameObjectActionPossibilitySource> _playCardActionSources;
    private GameObjectActionSource _resolveActionSource;
    private String _cardType;
    private List<Keyword> _keywords;

    public void setPlayCardActionSources(List<GameObjectActionPossibilitySource> playCardActionSources) {
        _playCardActionSources = playCardActionSources;
    }

    public void setResolveActionSource(GameObjectActionSource resolveActionSource) {
        _resolveActionSource = resolveActionSource;
    }

    public void setCardType(String cardType) {
        _cardType = cardType;
    }

    public void setKeywords(List<Keyword> keywords) {
        _keywords = keywords;
    }

    @Override
    public List<GameObjectActionPossibilitySource> getPlayCardActionSources() {
        return _playCardActionSources;
    }

    @Override
    public GameObjectActionSource getResolveActionSource() {
        return _resolveActionSource;
    }

    @Override
    public String getCardType() {
        return _cardType;
    }

    @Override
    public List<Keyword> getKeywords() {
        return _keywords;
    }
}