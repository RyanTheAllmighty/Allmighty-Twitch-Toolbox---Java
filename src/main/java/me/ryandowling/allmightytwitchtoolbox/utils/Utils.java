package me.ryandowling.allmightytwitchtoolbox.utils;

import me.ryandowling.allmightytwitchtoolbox.data.twitch.TwitchAPIRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    public static Path getCoreDir() {
        return Paths.get(System.getProperty("user.dir"));
    }

    public static Path getDataDir() {
        return getCoreDir().resolve("data");
    }

    public static Path getHTMLDir() {
        return getDataDir().resolve("html");
    }

    public static Path getSettingsFile() {
        return getCoreDir().resolve("settings.json");
    }

    public static Path getNotificationsHTMLFile() {
        return getHTMLDir().resolve("notifications.html");
    }

    public static Path getNotificationsImageFile() {
        return getHTMLDir().resolve("notifications.png");
    }

    public static Path getFollowersFile() {
        return getDataDir().resolve("followers.json");
    }

    public static Path getDonationsFile() {
        return getDataDir().resolve("donations.json");
    }

    public static Path getLatestFollowerFile() {
        return getDataDir().resolve("latestFollower.txt");
    }

    public static Path getNumberOfFollowersFile() {
        return getDataDir().resolve("numberOfFollowers.txt");
    }

    public static Path getFollowersTallyFile() {
        return getDataDir().resolve("followersTally.txt");
    }

    public static Path getFollowerGoalFile() {
        return getDataDir().resolve("followerGoal.txt");
    }

    public static Path getLatestDonationFile() {
        return getDataDir().resolve("latestDonation.txt");
    }

    public static Path getDonationsTallyFile() {
        return getDataDir().resolve("donationsTally.txt");
    }

    public static Path getDonationGoalFile() {
        return getDataDir().resolve("donationGoal.txt");
    }

    public static Path getCounterFile() {
        return getDataDir().resolve("counter.txt");
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

    public static FileFilter getWavFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".wav");

            }

            @Override
            public String getDescription() {
                return "WAV sound files";
            }
        };
    }

    public static String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

    public static boolean isTwitchAPITokenValid(String accessToken) {
        try {
            TwitchAPIRequest request = new TwitchAPIRequest("/", accessToken);
            String response = request.get();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);

            JSONObject token = (JSONObject) jsonObject.get("token");
            boolean valid = (boolean) token.get("valid");

            if (!valid) {
                System.err.println("API token not valid!");
            }

            return valid;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to Twitch API!");
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error occured parsing JSON from Twitch API!");
        }

        return false;
    }
}