package com.onaple;

import com.google.common.reflect.TypeToken;
import com.onaple.manipulator.HitEffectData;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.effect.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class HeKeys {
    public static Key<ListValue<SingleHitEffect>> HIT_EFFECT;
    public HeKeys(){
        HIT_EFFECT = Key.builder()
                .id("hit.effect")
                .name("Hit effect")
                .type(new TypeToken<ListValue<SingleHitEffect>>() {})
                .query(DataQuery.of(".","hit.effect"))
                .build();
    }
}
