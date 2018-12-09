import com.onaple.itemizer.service.IItemBeanFactory;
import com.onaple.itemizer.service.IItemService;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import javax.inject.Inject;
import java.util.Optional;

@Plugin(id = "effectsmith",
        name = "Effectsmith",
        description = "Create weapon and armour with potion effect and more",
        authors = {"Raiponz"},
        dependencies = {@Dependency(id = "itemizer")},
        version = "0.1")
public class Effectsmith {

    @Inject
    Logger logger;

    @Listener
    public void onServerStart(GameInitializationEvent event){
        Optional<IItemService> optionalIItemService = Sponge.getServiceManager().provide(IItemService.class);
        if (optionalIItemService.isPresent()) {
            logger.info("itemizer present");
        } else {
            logger.warn("itemizer not present");
        }
    }
}
