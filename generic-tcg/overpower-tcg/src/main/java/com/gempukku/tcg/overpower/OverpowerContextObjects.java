package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

public class OverpowerContextObjects {
    public static final GenericObject<PlayerDigitalObjectStackManager> SETUP_CHARACTERS_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("setupCharactersZone");
    public static final GenericObject<PlayerDigitalObjectStackManager> SETUP_DECK_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("setupDeckZone");
    public static final GenericObject<OverpowerCardManager> OVERPOWER_CARD_MANAGER = new GenericObject<OverpowerCardManager>("overpowerCardManager");

    public static final GenericObject<PlayerDigitalObjectStackManager> FRONT_LINE_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("frontLineZone");
    public static final GenericObject<PlayerDigitalObjectStackManager> RESERVE_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("reserveZone");
    public static final GenericObject<PlayerDigitalObjectStackManager> DECK_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("deckZone");
    public static final GenericObject<PlayerDigitalObjectStackManager> HAND_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("handZone");
    public static final GenericObject<PlayerDigitalObjectStackManager> POWER_PACK_ZONE = new GenericObject<PlayerDigitalObjectStackManager>("powerPackZone");

    public static <T> T extractGameObject(GameObjects gameObjects, GenericObject<T> object) {
        return (T) gameObjects.getGameObject(object._name);
    }

    private static class GenericObject<T> {
        private String _name;

        private GenericObject(String name) {
            _name = name;
        }
    }
}
