package me.ryandowling.allmightytwitchtoolbox;

import me.ryandowling.allmightytwitchtoolbox.gui.Console;

public class Logger {
    public static Console console = App.NOTIFIER.getConsole();

    public static void log(String message) {
        if (checkConsole()) {
            console.write(message);
        } else {
            System.out.println(message);
        }
    }

    private static boolean checkConsole() {
        if (console == null) {
            console = App.NOTIFIER.getConsole();
        }

        return console != null;
    }
}
