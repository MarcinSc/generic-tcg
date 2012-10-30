package com.gempukku.tcg.quest;

import com.gempukku.tcg.generic.mtg.objects.BlueprintTriggerResolver;
import com.gempukku.tcg.generic.mtg.objects.TriggerCondition;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonBlueprintDefinition implements BlueprintTriggerResolver {
    private Map<String, List<TriggerCondition>> _triggerConditions = new HashMap<String, List<TriggerCondition>>();

    public void setJsonDefinitionLocation(String location) throws IOException {
        JsonFactory fac = new JsonFactory();

        Map<String, JsonNode> cards = new HashMap<String, JsonNode>();
        Map<String, JsonNode> triggers = new HashMap<String, JsonNode>();

        parseJsonFile(location, fac, cards, triggers);
        processJsonObjects(cards, triggers);
    }

    private void processJsonObjects(Map<String, JsonNode> cards, Map<String, JsonNode> triggers) {

    }

    private void parseJsonFile(String location, JsonFactory fac, Map<String, JsonNode> cards, Map<String, JsonNode> triggers) throws IOException {
        JsonParser jsonParser = fac.createJsonParser(JsonBlueprintDefinition.class.getResourceAsStream(location));
        JsonNode json = jsonParser.readValueAsTree();
        JsonNode imports = json.get("import");
        if (imports != null) {
            if (!imports.isArray())
                throw new IllegalArgumentException("Invalid JSON definition - import is not an array");
            for (JsonNode anImport : imports)
                parseJsonFile(anImport.asText(), fac, cards, triggers);
        }

        parseCardsFromJson(cards, json);
        parseTriggersFromJson(triggers, json);
    }

    private void parseTriggersFromJson(Map<String, JsonNode> triggers, JsonNode json) {
        JsonNode jsonTriggers = json.get("trigger");
        if (jsonTriggers != null) {
            if (!jsonTriggers.isArray())
                throw new IllegalArgumentException("Invalid JSON definition - trigger is not an array");
            for (JsonNode trigger : jsonTriggers)
                triggers.put(trigger.get("id").asText(), trigger);
        }
    }

    private void parseCardsFromJson(Map<String, JsonNode> cards, JsonNode json) {
        JsonNode jsonCards = json.get("card");
        if (jsonCards != null) {
            if (!jsonCards.isArray())
                throw new IllegalArgumentException("Invalid JSON definition - card is not an array");
            for (JsonNode card : jsonCards)
                cards.put(card.get("id").asText(), card);
        }
    }

    @Override
    public List<TriggerCondition> getTriggerConditions(String blueprint) {
        return _triggerConditions.get(blueprint);
    }
}
