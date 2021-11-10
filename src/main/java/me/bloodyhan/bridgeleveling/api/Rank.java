package me.bloodyhan.bridgeleveling.api;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Bloody_Han
 */
@Getter
public class Rank {

    private String id;
    private String name;
    private String patternA;
    private String patternB;
    private int levelRequirement;
    private int blockPlaceRequirement;
    private int killRequirement;
    private int killStreakRequirement;

    public Rank(String id, String name, String patternA, String patternB, int levelRequirement, int blockPlaceRequirement, int killRequirement, int killStreakRequirement){
        this.id = id;
        this.name = name;
        this.patternA = patternA;
        this.patternB = patternB;
        this.levelRequirement = levelRequirement;
        this.blockPlaceRequirement = blockPlaceRequirement;
        this.killRequirement = killRequirement;
        this.killStreakRequirement = killStreakRequirement;
    }



}
