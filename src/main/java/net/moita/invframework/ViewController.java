package net.moita.invframework;

import lombok.Getter;

import net.moita.invframework.listener.InventoryListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ViewController {

    @Getter
    private static JavaPlugin plugin;

    @Getter
    private static final Map<UUID, Viewer> viewers = new HashMap<>();

    public static void openView(Viewer viewer, Class<? extends View> view) {
        final CustomView customView = new CustomView(viewer);
        try {
            View instance = view.newInstance();
            viewer.setInstance(instance);

            instance.configureView(customView.getConfigurator());
            instance.renderView(customView.getRenderer());
            customView.createInventory(viewer.getPlayer());

            viewer.setCustomView(customView);
            if(!(getViewer(viewer.getUniqueId()).isPresent())) viewers.put(viewer.getUniqueId(), viewer);

            viewer.getPlayer().openInventory(customView.getRaw());
        } catch(InstantiationException | IllegalAccessException ignored) {}
    }

    public static Optional<Viewer> getViewer(UUID uniqueId) {
        return Optional.ofNullable(viewers.get(uniqueId));
    }

    public static Optional<Viewer> unregisterViewer(UUID uniqueId) {
        return Optional.ofNullable(viewers.remove(uniqueId));
    }

    public static void withPlugin(JavaPlugin it) {
        plugin = it;
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), it);
    }

}
