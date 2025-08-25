package com.pl.Premier_League.ui;

import com.pl.Premier_League.player.Player;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PriceUtil {
    private static final Map<String, BigDecimal> cache = new HashMap<>();

    public static BigDecimal price(Player p){
        return cache.computeIfAbsent(p.getName(), n -> {
            int h = Math.abs(Objects.hashCode(n));
            long eur = 2_000_000 + (h % 8_000_000);
            return BigDecimal.valueOf(eur);
        });
    }

    public static String fmtM(BigDecimal euros){
        double m = euros.doubleValue() / 1_000_000.0;
        return String.format("%.1fM", m);
    }
}
