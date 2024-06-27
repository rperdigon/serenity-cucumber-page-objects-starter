package starter.pages.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SelectorFactoryProvider {

    private static final Map<String, SelectorFactory> FACTORIES = new HashMap<>();

    static {
        FACTORIES.put("yaml", new YamlFactory());
        //FACTORIES.put("page", new PageFactory());
    }

    public static SelectorFactory getFactory(String type) {
        return Optional.ofNullable(FACTORIES.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported selector factory type: " + type));
    }
}