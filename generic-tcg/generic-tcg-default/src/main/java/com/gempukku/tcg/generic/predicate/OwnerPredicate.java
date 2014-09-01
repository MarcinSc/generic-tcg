package com.gempukku.tcg.generic.predicate;


public class OwnerPredicate extends AbstractStringPredicate {
    public OwnerPredicate(String owner) {
        super("owner", owner);
    }
}