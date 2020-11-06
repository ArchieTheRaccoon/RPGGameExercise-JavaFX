package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

public class MusicPlayer {
    private static boolean isMenuMusicPlaying;
    private static final Media mMENU = new Media(Paths.get("src/ui/mMENU.mp3").toUri().toString());
    private static final MediaPlayer mediaPlayerMENU = new MediaPlayer(mMENU);

    private static final Media mFIGHT = new Media(Paths.get("src/ui/mFIGHT.mp3").toUri().toString());
    private static final MediaPlayer mediaPlayerFIGHT = new MediaPlayer(mFIGHT);

    private static final Media mSHOP = new Media(Paths.get("src/ui/mSHOP.mp3").toUri().toString());
    private static final MediaPlayer mediaPlayerSHOP = new MediaPlayer(mSHOP);

    public static void turnOnMenuMusic() {
        isMenuMusicPlaying = true;
        mediaPlayerMENU.seek(Duration.ZERO);
        mediaPlayerMENU.setVolume(0.1);
        mediaPlayerMENU.play();
    }

    public static void turnOffMenuMusic() {
        isMenuMusicPlaying = false;
        mediaPlayerMENU.stop();
    }

    public static void turnOnFightMusic() {
        mediaPlayerFIGHT.setVolume(0.1);
        mediaPlayerFIGHT.seek(Duration.ZERO);
        mediaPlayerFIGHT.play();
    }

    public static void turnOffFightMusic() {
        mediaPlayerFIGHT.stop();
    }

    public static void turnOnShopMusic() {
        mediaPlayerSHOP.seek(Duration.ZERO);
        mediaPlayerSHOP.setVolume(0.1);
        mediaPlayerSHOP.play();
    }

    public static void turnOffShopMusic() {
        mediaPlayerSHOP.stop();
    }

    public static boolean isMenuMusicPlaying() {
        return isMenuMusicPlaying;
    }
}
