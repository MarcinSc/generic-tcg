package com.gempukku.tcg.digital;

import java.util.Map;

public interface DigitalObjectListener {
    public void startChangeFrame();

    public void objectCreated(String id, Map<String, String> objectAttributes);

    public void objectUpdated(String oldId, String newId, Map<String, String> attributeDiff);

    public void objectDestroyed(String id);

    public void finishChangeFrame();
}
