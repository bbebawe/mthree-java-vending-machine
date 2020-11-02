/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

import java.math.BigDecimal;

/**
 *
 * @author beshoy
 */
public enum Coin {
    _1P("1"),
    _2P("2"),
    _5P("5"),
    _10P("10"),
    _20P("20"),
    _50P("50"),
    _POUND("100");
    
    private BigDecimal value;

    private Coin(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }

}
