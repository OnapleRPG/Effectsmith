package com.onaple;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.effect.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class HeKeys {
    public static Key<MapValue<String,Integer>> HIT_EFFECT;
    public HeKeys(){
        HIT_EFFECT = Key.builder()
                .id("hiteffect")
                .name("Hit effect")
                .type(new TypeToken<MapValue<String,Integer>>() {})
                .query(DataQuery.of("HitEffect"))
                .build();
    }
}
