package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Term;

public class SelectedListItem {
    private static Term selectedTerm = null;

    public static void setSelectedTerm(Term term) {
        selectedTerm = term;
    }

    public static Term getSelectedTerm() {
        return selectedTerm;
    }
}
