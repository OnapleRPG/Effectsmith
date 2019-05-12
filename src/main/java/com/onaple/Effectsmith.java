package com.onaple;

import com.onaple.itemizer.Itemizer;
import com.onaple.itemizer.service.IItemService;
import com.onaple.manipulator.HitEffectData;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Plugin(id = "effectsmith",
        name = "com.onaple.Effectsmith",
        description = "Create weapon and armour with potion effect and more",
        authors = {"Raiponz"},
        dependencies = {@Dependency(id = "itemizer")},
        version = "0.1")
public class Effectsmith {

    @Inject
    Logger logger;
    @Inject
    private PluginContainer container;

    @Listener
    public void onServerStart(GameConstructionEvent event) {
        new HeKeys();
        DataRegistration.builder()
                .dataClass(HitEffectData.class)
                .immutableClass(HitEffectData.Immutable.class)
                .dataImplementation(HitEffectData.class)
                .immutableImplementation(HitEffectData.Immutable.class)
                .dataName("Hit Effect")
                .manipulatorId("hiteffect")
                .builder(new HitEffectData.Builder())
                .buildAndRegister(this.container);
    }

    @Listener
    public void onHit(DamageEntityEvent event, @First Player player) {
       Optional<ItemStack> itemOpt = player.getItemInHand(HandTypes.MAIN_HAND);
       if (itemOpt.isPresent()) {
           ItemStack item = itemOpt.get();
           Optional<Map<String,Integer>>  dataOpt = item.get(HeKeys.HIT_EFFECT);
           if(dataOpt.isPresent()) {
               List<PotionEffect> potionEffects = new ArrayList<>();
               logger.info("item effect data [{}]", dataOpt.get());
               for (Map.Entry<String,Integer> effect : dataOpt.get().entrySet()) {
                    Sponge.getRegistry().getType(PotionEffectType.class, effect.getKey()).ifPresent(potionEffectType -> {
                        PotionEffect potionEffect = PotionEffect.builder().potionType(potionEffectType)
                                .amplifier(effect.getValue())
                                .duration(1000)
                                .particles(true).build();
                        potionEffects.add(potionEffect);
                    });
               }
               event.getTargetEntity().offer(Keys.POTION_EFFECTS, potionEffects);
               player.sendMessage(Text.of(event.getTargetEntity().get(Keys.POTION_EFFECTS)));
           }
       }
    }
}
