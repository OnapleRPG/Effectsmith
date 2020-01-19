package com.onaple;

import com.onaple.manipulator.HitEffectData;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Plugin(id = "effectsmith",
        name = "com.onaple.Effectsmith",
        description = "Create weapon and armour with potion effect and more",
        authors = {"Raiponz"},
        dependencies = {@Dependency(id = "itemizer")},
        version = "0.1")
public class EffectSmith {

    @Inject
    private PluginContainer container;

    @Inject
    private void setLogger(Logger logger){
        this.logger = logger;
    }


    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @Listener
    public void onServerStart(GameConstructionEvent event) {
        new HeKeys();
        Sponge.getDataManager().registerTranslator(SingleHitEffect.class,new HitEffectTranslator());
        DataRegistration.builder()
                .dataClass(HitEffectData.class)
                .immutableClass(HitEffectData.Immutable.class)
                .dataName("Hit Effect")
                .manipulatorId("hit.effect")
                .builder(new HitEffectData.Builder())
                .buildAndRegister(this.container);

    }


    @Listener
    public void onHit(DamageEntityEvent event, @First Player player) {
        Optional<ItemStack> itemOpt = player.getItemInHand(HandTypes.MAIN_HAND);
        if (itemOpt.isPresent()) {
            ItemStack item = itemOpt.get();
            Optional<List<SingleHitEffect>> dataOpt = item.get(HeKeys.HIT_EFFECT);
            if (dataOpt.isPresent()) {
                List<PotionEffect> potionEffects = new ArrayList<>();
                logger.info("item effect data [{}]", dataOpt.get());
                for (SingleHitEffect effect : dataOpt.get()) {
                    PotionEffect potionEffect = PotionEffect.builder().potionType(effect.getPotionEffectType())
                            .amplifier(effect.getAmplification())
                            .duration(effect.getDuration())
                            .particles(true).build();
                    potionEffects.add(potionEffect);
                }
                event.getTargetEntity().offer(Keys.POTION_EFFECTS, potionEffects);
                player.sendMessage(Text.of(event.getTargetEntity().get(Keys.POTION_EFFECTS)));
            }
        }
    }

    @Listener(order = Order.POST)
    public void onEat(InteractItemEvent event) {
        logger.info("interact with {}", event.getItemStack().getType());
    }
}
