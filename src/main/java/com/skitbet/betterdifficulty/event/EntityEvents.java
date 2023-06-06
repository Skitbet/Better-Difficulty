package com.skitbet.betterdifficulty.event;

import com.skitbet.betterdifficulty.utils.MathUtils;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEvents {

    @SubscribeEvent
    public void onEntitySpawned(LivingSpawnEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (!(mob instanceof Monster)) return;
            if (mob.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                Difficulty difficulty = event.getWorld().getDifficulty();
                float defaultHealth = mob.getMaxHealth();

                // Setting the max health and health values of the mob
                AttributeInstance attribute = mob.getAttribute(Attributes.MAX_HEALTH);
                attribute.setBaseValue(MathUtils.calcNewHealth(defaultHealth, difficulty, (float) event.getWorld().dayTime() / 24000L));
                attribute.save();
                mob.setHealth(mob.getMaxHealth());
            }
        }
    }
}