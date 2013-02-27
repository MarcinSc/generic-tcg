package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionPossibilitySource;
import com.gempukku.tcg.generic.action.GameObjectActionSource;
import com.gempukku.tcg.generic.keyword.Keyword;
import com.gempukku.tcg.solforge.trigger.TriggeredEffect;

import java.util.List;

public class SimpleSolforgeCardLevelBlueprint implements SolforgeCardLevelBlueprint {
    private List<GameObjectActionPossibilitySource> _playCardActionSources;
    private GameObjectActionSource _resolveActionSource;
    private String _cardType;
    private List<Keyword> _keywords;
    private int _attack;
    private int _health;
    private List<TriggeredEffect> _triggeredEffects;

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

    public void setAttack(int attack) {
        _attack = attack;
    }

    public void setHealth(int health) {
        _health = health;
    }

    public void setTriggeredEffects(List<TriggeredEffect> triggeredEffects) {
        _triggeredEffects = triggeredEffects;
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

    @Override
    public int getAttack() {
        return _attack;
    }

    @Override
    public int getHealth() {
        return _health;
    }

    @Override
    public List<TriggeredEffect> getTriggeredEffects() {
        return _triggeredEffects;
    }
}
