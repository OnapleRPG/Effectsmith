package com.onaple;

import com.onaple.itemizer.data.beans.ItemNbtFactory;
import com.onaple.manipulator.HitEffectData;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EffectFactory implements ItemNbtFactory {

    @Setting("effects")
    private Map<String, Integer> effects;


    @Override
    public Key getKey() {
        return HeKeys.HIT_EFFECT;
    }

    @Override
    public String getName() {
        return "EffectSmith";
    }

    @Override
    public DataManipulator<?, ?> constructDataManipulator() {
        return new HitEffectData(effects);
    }

    public List<Text> getLore(){

        return effects.entrySet().stream().map(stringIntegerEntry -> Text.builder()
                .append(
                        Text.builder(stringIntegerEntry.getKey()).color(TextColors.GRAY).build(),
                        Text.builder(" -> ").color(TextColors.GRAY).build(),
                        Text.builder(stringIntegerEntry.getValue().toString()).color(TextColors.GOLD).build()
                )
                .build()
        ).collect(Collectors.toList());
    }


    @Override
    public int compareTo(ItemNbtFactory itemNbtFactory) {
        return 0;
    }
}
