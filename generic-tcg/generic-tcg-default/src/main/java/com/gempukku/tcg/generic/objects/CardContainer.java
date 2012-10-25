package com.gempukku.tcg.generic.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CardContainer {
    private Map<String, CardZone> _sharedCardZones = new HashMap<String, CardZone>();
    private Set<String> _playerZoneNames;
    private Map<String, Map<String, CardZone>> _playerCardZones = new HashMap<String, Map<String, CardZone>>();

    public void setSharedZoneNames(Set<String> sharedZoneNames) {
        for (String sharedZoneName : sharedZoneNames)
            _sharedCardZones.put(sharedZoneName, new CardZone());
    }

    public void setPlayerZoneNames(Set<String> playerZoneNames) {
        _playerZoneNames = playerZoneNames;
    }

    public CardZone getSharedCardZone(String name) {
        CardZone cardZone = _sharedCardZones.get(name);
        if (cardZone == null)
            throw new IllegalArgumentException("Invalid share card zone name: "+name);
        return cardZone;
    }

    public CardZone getPlayerCardZone(String player, String name) {
        Map<String, CardZone> playerZones = _playerCardZones.get(player);
        if (playerZones == null) {
            playerZones = new HashMap<String, CardZone>();
            for (String playerZoneName : _playerZoneNames)
                playerZones.put(playerZoneName, new CardZone());
        }
        CardZone cardZone = playerZones.get(name);
        if (cardZone == null)
            throw new IllegalArgumentException("Invalid player card zone name: "+name);
        return cardZone;
    }
}
