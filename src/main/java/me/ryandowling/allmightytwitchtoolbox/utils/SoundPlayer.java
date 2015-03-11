package me.ryandowling.allmightytwitchtoolbox.utils;

import me.ryandowling.allmightytwitchtoolbox.App;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.nio.file.Path;

public class SoundPlayer {
    public static void playSound(Path file, float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file.toFile());

            clip.open(inputStream);

            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(volume);

            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
