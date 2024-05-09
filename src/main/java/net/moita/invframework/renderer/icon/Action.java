package net.moita.invframework.renderer.icon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import net.moita.invframework.CustomView;
import net.moita.invframework.renderer.Renderer;
import net.moita.invframework.Viewer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter @Setter
public final class Action {

    public enum Type {
        LEFT, RIGHT, SHIFT_LEFT, SHIFT_RIGHT, SLOT_CHANGE;
    }

    private final Type clickType;
    private final Icon clickedIcon;
    private final int clickedSlot;

    private final CustomView customView;
    private final Renderer renderer;

    private final Player whoClicked;
    private final Viewer viewer;

    private ItemStack receivedItem;

}
