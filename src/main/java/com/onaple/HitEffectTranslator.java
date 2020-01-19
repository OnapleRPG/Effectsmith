package com.onaple;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.effect.potion.PotionEffectType;

public class HitEffectTranslator implements DataTranslator<SingleHitEffect> {
    @Override
    public TypeToken<SingleHitEffect> getToken() {
        return TypeToken.of(SingleHitEffect.class);
    }

    @Override
    public SingleHitEffect translate(DataView view) throws InvalidDataException {
        SingleHitEffect hitEffect = new SingleHitEffect();
         PotionEffectType effect = view.getCatalogType(HeKeys.HIT_EFFECT.getQuery().then("effect"), CatalogTypes.POTION_EFFECT_TYPE).orElseThrow(() -> new InvalidDataException());
        hitEffect.setPotionEffectType(effect);
         view.getInt(HeKeys.HIT_EFFECT.getQuery().then("amplification")).ifPresent(
                hitEffect::setAmplification
        );
        view.getInt(HeKeys.HIT_EFFECT.getQuery().then("duration")).ifPresent(
                hitEffect::setDuration
        );
        return hitEffect;
    }

    @Override
    public DataContainer translate(SingleHitEffect obj) throws InvalidDataException {
            DataContainer container = DataContainer.createNew();
            container.set(DataQuery.of("effectType"),obj.getPotionEffectType());
            container.set(DataQuery.of("amplification"),obj.getAmplification());
            container.set(DataQuery.of("duration"),obj.getDuration());
            return container;
    }

    @Override
    public String getId() {
        return "hit.effect";
    }

    @Override
    public String getName() {
        return "Hit Effect";
    }
}
