package net.moita.invframework.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import static net.moita.invframework.ViewController.getViewer;

import net.moita.invframework.renderer.icon.Action;
import net.moita.invframework.Viewer;

public final class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        Viewer viewer = getViewer(player.getUniqueId()).orElse(null);
        if(viewer == null) return;

        if(event.getClick() != ClickType.LEFT
                && event.getClick() != ClickType.SHIFT_LEFT
                && event.getClick() != ClickType.RIGHT
                && event.getClick() != ClickType.SHIFT_RIGHT) {
            event.setCancelled(true);
            return;
        }

        if(event.getCursor() != null
                && event.getCursor().getType() != Material.AIR
                && event.getRawSlot() < viewer.getCustomView().getSize()) {
            event.setCancelled(true);
            return;
        }

        Action.Type type = Action.Type.valueOf(event.getClick().toString());
        viewer.getCustomView().getRenderer().simulateClick(type, event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        getViewer(player.getUniqueId()).ifPresent(viewer -> event.setCancelled(true));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        getViewer(event.getPlayer().getUniqueId()).ifPresent((viewer) -> {
            if(viewer.getInstance() != null) viewer.getInstance().handleClose(viewer);
        });
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        getViewer(event.getPlayer().getUniqueId()).ifPresent((viewer) -> {
           if(viewer.getInstance() != null) viewer.getInstance().handleOpen(viewer);
           viewer.getCustomView().getRenderer().renderInventory();
        });
    }

}
