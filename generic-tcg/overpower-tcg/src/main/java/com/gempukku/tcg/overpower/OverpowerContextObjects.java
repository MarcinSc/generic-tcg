package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.zone.PlayerDigitalObjectZoneManager;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

public class OverpowerContextObjects {
    public static final GenericObject<PlayerDigitalObjectZoneManager> SETUP_CHARACTERS_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("setupCharactersZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> SETUP_DECK_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("setupDeckZone");
    public static final GenericObject<OverpowerCardManager> OVERPOWER_CARD_MANAGER = new GenericObject<OverpowerCardManager>("overpowerCardManager");

    public static final GenericObject<PlayerDigitalObjectZoneManager> IN_PLAY_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("inPlayZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> DECK_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("deckZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> HAND_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("handZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> POWER_PACK_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("powerPackZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> DEAD_PILE_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("deadPileZone");

    public static final GenericObject<PlayerDigitalObjectZoneManager> COMPLETED_MISSIONS_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("completedMissionsZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> RESERVE_MISSIONS_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("reserveMissionsZone");
    public static final GenericObject<PlayerDigitalObjectZoneManager> DEFEATED_MISSIONS_ZONE = new GenericObject<PlayerDigitalObjectZoneManager>("defeatedMissionsZone");

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
