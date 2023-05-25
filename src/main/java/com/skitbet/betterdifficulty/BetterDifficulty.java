package com.skitbet.betterdifficulty;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

import static com.skitbet.betterdifficulty.BetterDifficulty.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class BetterDifficulty
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "betterdifficulty";

    public BetterDifficulty()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntitySpawned(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (!(mob instanceof Monster)) return;
            if (mob.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                Difficulty difficulty = event.getWorld().getDifficulty();
                float defaultHealth = mob.getMaxHealth();

                AttributeInstance attribute = mob.getAttribute(Attributes.MAX_HEALTH);
                attribute.setBaseValue(calcNewHealth(defaultHealth, difficulty, (float) event.getWorld().getDayTime() / 24000L));
                attribute.save();

                mob.setHealth(mob.getMaxHealth());
                LOGGER.debug(mob.getHealth() + " " + mob.getDisplayName().getString());
            }
        }

    }

    /*
     * A very dumb way of handling the calculations of health
     */
    public float calcNewHealth(float defaultHealth, Difficulty difficulty, float day) {
        if (day == 0) day = 0.1f; // Haha funny dumb way of making mobs easy on first day

        float newHeath = defaultHealth;
        switch (difficulty) {
            case EASY -> newHeath *= ((day / 0.9));
            case NORMAL -> newHeath *= (1.25 * (day / 0.8));
            case HARD -> newHeath *= (1.5 * (day / 0.6));
        }
        return newHeath;
    }
}
