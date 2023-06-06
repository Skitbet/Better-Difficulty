package com.skitbet.betterdifficulty.utils;

import com.skitbet.betterdifficulty.BetterDifficulty;
import net.minecraft.world.Difficulty;

public class MathUtils {
    public static float calcNewHealth(float defaultHealth, Difficulty difficulty, float day) {
        if (day == 0) day = 0.1f; // Haha funny dumb way of making mobs easy on first day

        int adder = 0;

        for (int i = 0; i < day; i++) {
            switch (difficulty) {
                case EASY -> {
                    if (day <= 35) {
                        adder+=1;
                    }else{
                        adder+=0.1;
                    }
                }
                case NORMAL -> {
                    if (day <= 45) {
                        adder+=2;
                    }else{
                        adder+=0.5;
                    }
                }
                case HARD -> {
                    if (day <= 50) {
                        adder+=3;
                    }else{
                        adder+=1;
                    }
                }
            }
        }

        float newHeath = defaultHealth + adder;
        return newHeath;
    }
}
