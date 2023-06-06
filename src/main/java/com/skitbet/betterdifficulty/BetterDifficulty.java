package com.skitbet.betterdifficulty;

import com.mojang.logging.LogUtils;
import com.skitbet.betterdifficulty.event.EntityEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.skitbet.betterdifficulty.BetterDifficulty.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class BetterDifficulty
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "betterdifficulty";

    public BetterDifficulty()
    {
        MinecraftForge.EVENT_BUS.register(new EntityEvents());
    }
}
