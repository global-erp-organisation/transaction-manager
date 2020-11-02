package com.ia.transaction.view;

import lombok.Getter;

public enum Account {
    CAPITALE_ONE_CC("Capitale one"),
    DESJARDINS_CC("Desjardins Odyssee"),
    DESJARDINS_EOP("Desjardins eop");

    @Getter
    private final String label;

    Account(String label) {
        this.label = label;
    }
}
