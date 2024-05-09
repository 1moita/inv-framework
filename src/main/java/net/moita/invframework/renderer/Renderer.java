package net.moita.invframework.renderer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import net.moita.invframework.renderer.icon.Action;
import net.moita.invframework.CustomView;
import net.moita.invframework.renderer.icon.Icon;
import net.moita.invframework.Viewer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class Renderer {

    private final CustomView customView;
    private final Viewer viewer;
    private final Map<Integer, Icon> icon = new HashMap<>();

    public Optional<Icon> getIcon(int slot) {
        return Optional.ofNullable(this.icon.get(slot));
    }

    public Renderer withIcon(int slot, Icon item) {
        this.icon.put(slot, item);
        return this;
    }

    public Renderer removeIcon(int slot) {
        this.icon.remove(slot);
        return this;
    }

    public void simulateClick(Action.Type type, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        getIcon(slot).ifPresent((icon) -> {
            event.setCancelled(icon.isCancellable());

            Action action = new Action(type, icon, slot, this.customView, this, player, this.customView.getViewer());
            ItemStack cursor = player.getItemOnCursor();

            if(cursor.getType() != Material.AIR) {
                event.setCancelled(true);

                action.setReceivedItem(cursor);
                icon.getAction(Action.Type.SLOT_CHANGE).ifPresent((consum) -> consum.accept(action));
            }

            icon.getAction(type).ifPresent((consumable) -> consumable.accept(action));
            this.viewer.getInstance().handleAction(action, this);

            if(this.customView.getConfigurator().isRenderInAction()) {
                this.icon.clear();
                this.viewer.getInstance().renderView(this);
            }
            renderInventory();
        });
    }

    public int availableSlot(int slot) {
        if(slot >= this.customView.getSize() || slot < 0) return -1;
        int index = slot;
        while(index <= this.customView.getSize()) {
            if(!(this.getIcon(index).isPresent())) break;
            index++;
        }
        return index;
    }

    public int availableSlot() {
        return this.availableSlot(0);
    }

    public <T> Optional<T> getProperty(String key) {
        return this.viewer.getProperty(key);
    }

    public void computeIfPresent(String key, Function<Object, Object> consumable) {
        this.viewer.getProperty(key).ifPresent((it) -> this.viewer.withProperty(key, consumable.apply(it)));
    }

    public <T> void setProperty(String key, T value) {
        this.viewer.withProperty(key, value);
    }

    public void renderInventory() {
        this.customView.getRaw().clear();

        this.icon.forEach((slot, icon) ->
                this.customView.getRaw().setItem(slot, icon.asItem()));
    }

    public static Renderer withCustomView(CustomView customView) {
        return new Renderer(customView, customView.getViewer());
    }

}
