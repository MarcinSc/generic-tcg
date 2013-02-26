package com.gempukku.tcg.solforge.util;

public class SolforgeObjectUtil {
    public static int extractLaneNumber(String laneId) {
        if (laneId.startsWith("lane:"))
            return Integer.parseInt(laneId.substring(5));
        else
            throw new IllegalArgumentException("Invalid lane id: " + laneId);
    }
}
