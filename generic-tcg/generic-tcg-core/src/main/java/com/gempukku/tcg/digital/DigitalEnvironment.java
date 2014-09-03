package com.gempukku.tcg.digital;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface DigitalEnvironment {
    public void addDigitalObjectListener(DigitalObjectListener listener);

    public void removeDigitalObjectListener(DigitalObjectListener listener);

    public void startFrameChange();

    public DigitalObject createObject(Map<String, String> attributes);

    public DigitalObject updateObject(String id, Map<String, String> attributeDiff, boolean requiresNewId);

    public void destroyObject(String id);

    public void finishFrameChange();

    public DigitalObject getObjectById(String id);

    public Iterable<DigitalObject> findObjects(Predicate<DigitalObject> predicate);
}
