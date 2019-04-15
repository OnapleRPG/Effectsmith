package com.onaple.manipulator;

import com.onaple.HeKeys;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableMappedData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractMappedData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.effect.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HitEffectData extends AbstractMappedData<PotionEffectType,Integer,HitEffectData, HitEffectData.Immutable> {

    public HitEffectData(){
        this(new HashMap<>());
    }

    public HitEffectData(Map<PotionEffectType,Integer> value) {
        super(value, HeKeys.HIT_EFFECT);
    }

    @Override
    public Optional<HitEffectData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<HitEffectData> otherData_ = dataHolder.get(HitEffectData.class);
        if (otherData_.isPresent()) {
            HitEffectData otherData = otherData_.get();
            HitEffectData finalData = overlap.merge(this, otherData);
            finalData.setValue(otherData.getValue());
        }
        return Optional.of(this);
    }

    @Override
    public Optional<HitEffectData> from(DataContainer container) {
        return from((DataView) container);
    }

    Optional<HitEffectData> from(DataView container){
        if (container.contains(HeKeys.HIT_EFFECT)) {
            Map<PotionEffectType,Integer> effects = new HashMap<>();
            container.getList(HeKeys.HIT_EFFECT.getQuery()).ifPresent(objects -> effects.putAll( (Map<PotionEffectType,Integer>) objects));
            this.setValue(effects);
        }
        return Optional.of(this);
    }

    @Override
    public HitEffectData copy() {
        return new HitEffectData(getValue());
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(HeKeys.HIT_EFFECT,getValue());
    }

    @Override
    public Optional<Integer> get(PotionEffectType key) {
         Integer value =getValue().get(key);
         if (value != null){
             return Optional.of(value);
         }
        return Optional.empty();
    }

    @Override
    public Set<PotionEffectType> getMapKeys() {
        return getValue().keySet();
    }

    @Override
    public HitEffectData put(PotionEffectType key, Integer value) {
        getValue().put(key,value);
        return this;
    }

    @Override
    public HitEffectData putAll(Map<? extends PotionEffectType, ? extends Integer> map) {
        getValue().putAll(map);
        return this;
    }


    @Override
    public HitEffectData remove(PotionEffectType key) {
        getValue().remove(key);
        return this;
    }

    public class Immutable extends AbstractImmutableMappedData<PotionEffectType,Integer, Immutable, HitEffectData> {

        public Immutable() {
            this(new HashMap<PotionEffectType, Integer>());
        }

        public Immutable(Map<PotionEffectType,Integer> value) {
            super(value, HeKeys.HIT_EFFECT);
        }

        @Override
        public HitEffectData asMutable() {
            return new HitEffectData(getValue());
        }

        @Override
        public int getContentVersion() {
            return 0;
        }
    }

    public static class Builder extends AbstractDataBuilder<HitEffectData>
            implements DataManipulatorBuilder<HitEffectData, Immutable> {

        protected int CONTENT_VERSION = 1;

        public Builder() {
            super(HitEffectData.class, 1);
        }

        @Override
        public HitEffectData create() {
            return new HitEffectData();
        }

        @Override
        public Optional<HitEffectData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<HitEffectData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }
    }
}
