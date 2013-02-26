package com.gempukku.tcg.generic.util;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.Zone;

public class GameObjectUtils {
    public static Zone resolveZone(GameState gameState, GameObject gameObject, String zone, String playerZone) {
        zone = resolveObjectProperty(gameObject, zone);
        playerZone = resolveObjectProperty(gameObject, playerZone);

        if (playerZone != null) {
            return (Zone) gameState.getPlayerObject(playerZone, zone);
        } else
            return (Zone) gameState.getGameObject(zone);
    }

    public static String resolveObjectProperty(GameObject gameObject, String property) {
        if (property == null)
            return null;
        int index = property.indexOf("*{");
        if (index > -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(property.substring(0, index));
            int lastIndex = property.lastIndexOf("}");
            if (lastIndex < 0)
                throw new IllegalArgumentException("Invalid property value: " + property);
            sb.append(extractProperty(gameObject, property.substring(index + 2, lastIndex)));
            sb.append(property.substring(lastIndex + 1));
            return sb.toString();
        } else
            return property;
    }

    private static String extractProperty(GameObject gameObject, String property) {
        return gameObject.getProperty(property);
    }
}
