package com.onaple.manipulator;

import com.onaple.EffectSmith;
import com.onaple.HeKeys;
import com.onaple.SingleHitEffect;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableListData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractListData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HitEffectData extends AbstractListData<SingleHitEffect, HitEffectData, HitEffectData.Immutable> {

    public HitEffectData() {
        this(new ArrayList<>());
    }

    public HitEffectData(List<SingleHitEffect> value) {
        super(value,HeKeys.HIT_EFFECT);
    }

    @Override
    public Optional<HitEffectData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<HitEffectData> otherData_ = dataHolder.get(HitEffectData.class);
        EffectSmith.getLogger().info("fill with data {}",dataHolder);
        if (otherData_.isPresent()) {
            HitEffectData otherData = otherData_.get();
            HitEffectData finalData = overlap.merge(this, otherData);
            finalData.setValue(otherData.getValue());
            return Optional.of(this);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<HitEffectData> from(DataContainer container) {
        return from((DataView) container);
    }

    Optional<HitEffectData> from(DataView container) {
        if (container.contains(HeKeys.HIT_EFFECT.getQuery())) {
            EffectSmith.getLogger().info("from effect data {}",container);
            List<SingleHitEffect> effects = (List<SingleHitEffect>) container.getList(HeKeys.HIT_EFFECT.getQuery()).get();
            this.setValue(effects);
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
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
        return Builder.CONTENT_VERSION;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(HeKeys.HIT_EFFECT.getQuery(), getValue());
    }



    public static class Immutable extends AbstractImmutableListData<SingleHitEffect, Immutable, HitEffectData> {

        public Immutable() {
            this(new ArrayList<>());
        }

        public Immutable(List<SingleHitEffect> value) {
            super(value,HeKeys.HIT_EFFECT);
        }

        @Override
        public HitEffectData asMutable() {
            return new HitEffectData(getValue());
        }

        @Override
        public int getContentVersion() {
            return Builder.CONTENT_VERSION;
        }

        @Override
        @NonNull
        public DataContainer toContainer() {
            return super.toContainer().set(HeKeys.HIT_EFFECT.getQuery(), getValue());
        }

    }

    public static class Builder extends AbstractDataBuilder<HitEffectData>
            implements DataManipulatorBuilder<HitEffectData, Immutable> {


        public static final int CONTENT_VERSION = 1;

        public Builder() {
            super(HitEffectData.class, CONTENT_VERSION);
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
