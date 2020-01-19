package com.onaple;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;

@ConfigSerializable
public class SingleHitEffect{

    @Setting("effectType")
    private PotionEffectType potionEffectType;
    @Setting("duration")
    private int duration;
    @Setting("amplification")
    private int amplification;

    public PotionEffectType getPotionEffectType() {
        return potionEffectType;
    }

    public void setPotionEffectType(PotionEffectType potionEffectType) {
        this.potionEffectType = potionEffectType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmplification() {
        return amplification;
    }

    public void setAmplification(int amplification) {
        this.amplification = amplification;
    }

    public SingleHitEffect() {
    }

    public SingleHitEffect(PotionEffectType potionEffectType, int duration, int amplification) {
        this.potionEffectType = potionEffectType;
        this.duration = duration;
        this.amplification = amplification;
    }

}
