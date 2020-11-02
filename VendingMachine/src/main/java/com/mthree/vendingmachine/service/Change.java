/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author beshoy
 */
public class Change {

    private BigDecimal changeInPennies;

    public Change(BigDecimal changeInPennies) {
        this.changeInPennies = changeInPennies;
    }

    /**
     * calculates changes from pennies and return it in the correct coins
     * @param changeInPennies change in pennies
     * @return map with change coins
     */
    public static Map calculateChange(BigDecimal changeInPennies) {
        Map<String, Integer> change = new HashMap<>();

        int pounds = changeInPennies.divide(Coin._POUND.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._POUND.getValue());
        change.put(Coin._POUND.toString(), pounds);

        int _50p = changeInPennies.divide(Coin._50P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._50P.getValue());
        change.put(Coin._50P.toString(), _50p);

        int _20p = changeInPennies.divide(Coin._20P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._20P.getValue());
        change.put(Coin._20P.toString(), _20p);

        int _10p = changeInPennies.divide(Coin._10P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._10P.getValue());
        change.put(Coin._10P.toString(), _10p);

        int _5p = changeInPennies.divide(Coin._5P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._5P.getValue());
        change.put(Coin._5P.toString(), _5p);

        int _2p = changeInPennies.divide(Coin._2P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._2P.getValue());
        change.put(Coin._2P.toString(), _2p);

        int _1p = changeInPennies.divide(Coin._1P.getValue()).intValue();
        changeInPennies = changeInPennies.remainder(Coin._1P.getValue());
        change.put(Coin._1P.toString(), _1p);

        // return only coins that have value, not zero 
        return change.entrySet().stream()
                .filter((coin) -> coin.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
