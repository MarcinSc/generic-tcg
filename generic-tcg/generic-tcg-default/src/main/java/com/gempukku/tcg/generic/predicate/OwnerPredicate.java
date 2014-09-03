package com.gempukku.tcg.generic.predicate;


public class OwnerPredicate extends AbstractAttributePredicate {
    public OwnerPredicate(String owner) {
        super("owner", owner);
    }
}