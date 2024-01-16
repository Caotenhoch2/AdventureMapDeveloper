package eu.caoten.adventure_map_developer.register;

import net.minecraft.util.Identifier;

import java.util.*;

/**
 * Use this if you want so save things in an array to a name and later get these again
 * @param <T> The type of the thing you want to save
 */
public class MapRegistration<T> {
    private final Map<Identifier, T> map = new HashMap<>();

    /**
     * Add here things to save
     * @param name The name with which it should get identified with
     * @param value The value of the thing you're saving
     */
    public void register(Identifier name, T value) {
        map.put(name, value);
    }

    /**
     * @param name The name of the value that you want to get
     * @return The value if it exists
     */
    public Optional<T> get(Identifier name) {
        if (map.containsKey(name)) {
            return Optional.of(map.get(name));
        }
        return Optional.empty();
    }

    /**
     * @return All the saved values, without their identifier
     */
    public Collection<T> getAll() {
        return map.values();
    }

    /**
     * @param modId The namespace of the identifier, from which you want to get everything
     * @return All values saved to an identifier of the specified namespace
     */
    public Collection<T> getAll(String modId) {
        Collection<T> returnValues = new ArrayList<>();
        for (Identifier identifier : map.keySet()) {
            if (identifier.getNamespace().equals(modId)) {
                returnValues.add(map.get(identifier));
            }
        }
        return returnValues;
    }

    public boolean contains(Identifier id) {
        return map.containsKey(id);
    }
}
