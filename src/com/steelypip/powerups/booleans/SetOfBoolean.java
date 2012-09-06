package com.steelypip.powerups.booleans;

import java.util.Set;

public interface SetOfBoolean extends Set< Boolean > {

        boolean contains( boolean flag );
        boolean isFull();

}
