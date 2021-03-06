package com.gempukku.tcg.generic.zone.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.card.CardManager;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.ChooseArbitraryCardDecision;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectArbitraryCardsFromZoneEffect implements GameEffect {
    private StringEvaluator _zone;
    private DigitalObjectFilter _digitalObjectFilter;
    private IntEvaluator _min;
    private IntEvaluator _max;
    private StringEvaluator _attributeName;
    private StringEvaluator _player;
    private StringEvaluator _message;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setDigitalObjectFilter(DigitalObjectFilter digitalObjectFilter) {
        _digitalObjectFilter = digitalObjectFilter;
    }

    public void setMax(IntEvaluator max) {
        _max = max;
    }

    public void setMin(IntEvaluator min) {
        _min = min;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setZone(StringEvaluator zone) {
        _zone = zone;
    }

    public void setMessage(StringEvaluator message) {
        _message = message;
    }

    @Override
    public Result execute(final GameObjects gameObjects, final GameEffectContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        if (context.getAttribute(attributeName) != null)
            return Result.pass();

        final CardManager cardManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.CARD_MANAGER);
        final DigitalObjectZoneManager zone = (DigitalObjectZoneManager) gameObjects.getGameObject(_zone.getValue(gameObjects, context));

        final String playerName = _player.getValue(gameObjects, context);

        final List<DigitalObject> matchingObjects = DigitalObjectUtils.filter(gameObjects, _digitalObjectFilter, context, zone.getDigitalObjectsInZone(gameObjects, playerName));
        List<String> characterBlueprints = new ArrayList<String>();
        for (DigitalObject object : matchingObjects) {
            characterBlueprints.add(cardManager.getBlueprintId(object));
        }

        int min = _min.getIntValue(gameObjects, context);
        int max = _max.getIntValue(gameObjects, context);

        String message = _message.getValue(gameObjects, context);

        Map<String, AwaitingDecision> decisions = new HashMap<String, AwaitingDecision>();
        decisions.put(playerName,
                new ChooseArbitraryCardDecision(message, characterBlueprints, min, max) {
                    @Override
                    protected void objectsChosen(List<Integer> indices, List<String> blueprintId) {
                        if (indices.size() > 0) {
                            context.setAttribute(attributeName, StringUtils.join(
                                    Iterables.transform(indices,
                                            new Function<Integer, String>() {
                                                @Override
                                                public String apply(Integer input) {
                                                    return matchingObjects.get(input).getId();
                                                }

                                            }).iterator(), ","));
                        }
                    }
                });

        return Result.decisions(decisions);
    }
}
