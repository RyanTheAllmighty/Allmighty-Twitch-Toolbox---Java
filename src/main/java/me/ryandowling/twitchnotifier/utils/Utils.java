package me.ryandowling.twitchnotifier.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {
    public static Path getCoreDir() {
        return Paths.get(System.getProperty("user.dir"));
    }

    public static Path getSettingsFile() {
        return getCoreDir().resolve("settings.json");
    }

    public static Path getFollowersFile() {
        return getCoreDir().resolve("followers.json");
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    public static String timeConversion(int totalSeconds) {
        final int HOURS_IN_A_DAY = 24;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;

        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;

        int totalHours = totalMinutes / MINUTES_IN_AN_HOUR;
        int hours = totalHours % HOURS_IN_A_DAY;

        int days = totalHours / HOURS_IN_A_DAY;

        if (days != 0) {
            return days + " days, " + hours + " hours, " + minutes + " minutes and " + seconds + " seconds";
        } else {
            if (hours != 0) {
                return hours + " hours, " + minutes + " minutes and " + seconds + " seconds";
            } else {
                if (minutes != 0) {
                    return minutes + " minutes and " + seconds + " seconds";
                } else {
                    return seconds + " seconds";
                }
            }
        }
    }
}
