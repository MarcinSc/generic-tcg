package com.gempukku.tcg.generic.object;

public interface GameObjectVisitor {
    /**
     * Returns true, if the iteration over all objects can be stopped.
     *
     * @param gameObject
     * @return
     */
    public boolean visitGameObject(GameObject gameObject);
}
