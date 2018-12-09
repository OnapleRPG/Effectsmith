import com.onaple.itemizer.data.beans.IItemBeanConfiguration;
import com.onaple.itemizer.service.IItemBeanFactory;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.HashMap;
import java.util.Map;

public class ItemEffectFactory implements IItemBeanFactory {

    private final String keyId = "effect";
    private Map<String, Integer> hitEffects;

    @Override
    public String getKeyId() {
        return keyId;
    }

    @Override
    public IItemBeanConfiguration build(ConfigurationNode node) {
        Map<?, ?> effectsMap = node.getNode("effect").getChildrenMap();
        hitEffects = new HashMap<>();
        effectsMap.forEach((o, o2) -> {
            if (o instanceof String && o2 instanceof Integer) {
                hitEffects.put((String) o, (Integer) o2);
            }
        });

    }
}
