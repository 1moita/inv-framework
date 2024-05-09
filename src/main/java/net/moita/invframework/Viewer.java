package net.moita.invframework;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor @Getter @Setter
public final class Viewer {

    private final UUID uniqueId;
    private final Map<String, Object> properties = new HashMap<>();

    private View instance;
    private CustomView customView;

    public <T> Viewer withProperty(String key, T value) {
        properties.put(key, value);
        return this;
    }

    public <T> Optional<T> getProperty(String key) {
        return (Optional<T>) Optional.ofNullable(properties.get(key));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUniqueId());
    }

}
