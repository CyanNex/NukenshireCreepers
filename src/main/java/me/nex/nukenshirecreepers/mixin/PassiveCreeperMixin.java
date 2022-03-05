package me.nex.nukenshirecreepers.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class PassiveCreeperMixin extends MobEntity {

    protected PassiveCreeperMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "initGoals",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onInitGoals(@NotNull CallbackInfo ci) {
        ci.cancel();

        CreeperEntity self = (CreeperEntity) (Object) this;

        this.goalSelector.add(1, new SwimGoal(self));
        this.goalSelector.add(3, new FleeEntityGoal<>(self, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(3, new FleeEntityGoal<>(self, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new MeleeAttackGoal(self, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(self, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(self, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(self));
    }
}
