package com.appdesigndm.loc2loc.MenuOptionsFloatBar;

import java.io.Serializable;

public class FragmentSelector implements Serializable {

    private int selectorFragment;

    public FragmentSelector(int selectorFragment) {
        this.selectorFragment = selectorFragment;
    }

    public int getSelectorFragment() {
        return selectorFragment;
    }

    public void setSelectorFragment(int selectorFragment) {
        this.selectorFragment = selectorFragment;
    }

    @Override
    public String toString() {
        return "FragmentSelector{" +
                "selectorFragment=" + selectorFragment +
                '}';
    }
}
