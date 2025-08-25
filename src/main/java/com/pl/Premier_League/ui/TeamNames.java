package com.pl.Premier_League.ui;

import java.util.*;

public class TeamNames {

    private static final Map<String, List<String>> SLUG_TO_ALIASES = new HashMap<>();

    static {
        put("Arsenal", List.of("Arsenal"));

        put("Manchester-City", List.of("Manchester City", "Man City", "ManchesterCity"));
        put("Newcastle-United", List.of("Newcastle United", "Newcastle", "Newcastle Utd"));
        put("Tottenham-Hotspur", List.of("Tottenham", "Tottenham Hotspur", "Spurs"));
        put("Manchester-United", List.of("Manchester United", "Man United", "Man Utd", "Manchester Utd"));
        put("Liverpool", List.of("Liverpool"));
        put("Brighton-and-Hove-Albion", List.of("Brighton", "Brighton & Hove Albion", "Brighton and Hove Albion"));

        put("Chelsea", List.of("Chelsea"));
        put("Fulham", List.of("Fulham"));
        put("Brentford", List.of("Brentford"));
        put("Crystal-Palace", List.of("Crystal Palace", "CrystalPalace"));

        put("Aston-Villa", List.of("Aston Villa", "AstonVilla"));
        put("Leicester-City", List.of("Leicester City", "Leicester"));
        put("Bournemouth", List.of("Bournemouth"));
        put("Leeds-United", List.of("Leeds United", "Leeds"));
        put("West-Ham_United", List.of("West Ham United", "West Ham", "WestHam"));
        put("Everton", List.of("Everton"));
        put("Nottingham-Forest", List.of("Nottingham Forest", "Nottm Forest", "Nottingham"));
        put("Southhampton", List.of("Southampton"));
        put("Wolverhampton-Wanderers", List.of("Wolverhampton", "Wolves"));
    }

    private static void put(String slug, List<String> aliases) {
        SLUG_TO_ALIASES.put(slug, aliases);
    }

    public static List<String> aliasesFor(String slug) {
        if (slug == null) return List.of();
        var list = SLUG_TO_ALIASES.get(slug);
        if (list != null) return list;
        var normalized = slug.replace('_',' ').replace('-',' ').trim();
        return List.of(normalized);
    }

    public static String primary(String slug) {
        var a = aliasesFor(slug);
        return a.isEmpty() ? "" : a.get(0);
    }
}
