package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.zone.player.DigitalObjectZoneManager;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

public class OverpowerContextObjects {
    public static final GenericObject<DigitalObjectZoneManager> SETUP_CHARACTERS_ZONE = new GenericObject<DigitalObjectZoneManager>("setupCharactersZone");
    public static final GenericObject<DigitalObjectZoneManager> SETUP_DECK_ZONE = new GenericObject<DigitalObjectZoneManager>("setupDeckZone");
    public static final GenericObject<OverpowerCardManager> OVERPOWER_CARD_MANAGER = new GenericObject<OverpowerCardManager>("overpowerCardManager");

    public static final GenericObject<DigitalObjectZoneManager> IN_PLAY_ZONE = new GenericObject<DigitalObjectZoneManager>("inPlayZone");
    public static final GenericObject<DigitalObjectZoneManager> DECK_ZONE = new GenericObject<DigitalObjectZoneManager>("deckZone");
    public static final GenericObject<DigitalObjectZoneManager> HAND_ZONE = new GenericObject<DigitalObjectZoneManager>("handZone");
    public static final GenericObject<DigitalObjectZoneManager> POWER_PACK_ZONE = new GenericObject<DigitalObjectZoneManager>("powerPackZone");
    public static final GenericObject<DigitalObjectZoneManager> DEAD_PILE_ZONE = new GenericObject<DigitalObjectZoneManager>("deadPileZone");

    public static final GenericObject<DigitalObjectZoneManager> COMPLETED_MISSIONS_ZONE = new GenericObject<DigitalObjectZoneManager>("completedMissionsZone");
    public static final GenericObject<DigitalObjectZoneManager> RESERVE_MISSIONS_ZONE = new GenericObject<DigitalObjectZoneManager>("reserveMissionsZone");
    public static final GenericObject<DigitalObjectZoneManager> DEFEATED_MISSIONS_ZONE = new GenericObject<DigitalObjectZoneManager>("defeatedMissionsZone");

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
