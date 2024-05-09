package net.moita.invframework;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.moita.invframework.renderer.Renderer;
import net.moita.invframework.configurator.Configurator;

@Getter @Setter
public final class CustomView {

    private final Viewer viewer;

    private String title;
    private int size = 3 * 9;

    private final Renderer renderer;
    private final Configurator configurator;

    private org.bukkit.inventory.Inventory raw;

    public CustomView(Viewer viewer) {
        this.viewer = viewer;
        this.renderer = Renderer.withCustomView(this);
        this.configurator = Configurator.withCustomView(this);
    }

    public void createInventory(Player player) {
        this.raw = Bukkit.createInventory(
                player,
                this.size,
                this.title.isEmpty() ? "Inventário" : this.title);
    }

}
