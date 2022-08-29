package me.bloodyhan.bridgeleveling.config;

/**
 * @author Bloody_Han
 */
public class SoundConfig extends ConfigManager {

    public static SoundConfig config;

    @Config(path = "sound.level-up.name")
    public static String LEVEL_UP = "LEVEL_UP";
    @Config(path = "sound.level-up.pitch")
    public static float LEVEL_UP_PITCH = 1f;
    @Config(path = "sound.rank-up.name")
    public static String RANK_UP = "LEVEL_UP";
    @Config(path = "sound.rank-up.pitch")
    public static float RANK_UP_PITCH = 1f;
    @Config(path = "sound.on-kill.name")
    public static String KILL = "SUCCESSFUL_HIT";
    @Config(path = "sound.on-kill.pitch")
    public static float KILL_PITCH = 1.8f;

    public SoundConfig(){
        super("sound");
        config = this;
    }

}
