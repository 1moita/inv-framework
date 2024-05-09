package net.moita.invframework.renderer.icon;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor @Getter
public class Icon {

    @NonNull private Material type;
    private int amount;

    private String name = ChatColor.WHITE.toString();
    private final List<String> description = new ArrayList<>();
    private final List<ItemFlag> flags = new ArrayList<>();

    private final Map<Action.Type, Consumer<Action>> action = new HashMap<>();

    private boolean cancellable = true;

    private ItemStack defaultItem;

    public Icon setType(Material type) {
        this.type = type;
        return this;
    }

    public Icon setAmount(int amount) {
        if(amount > 0 && amount < 65) this.amount = amount;
        return this;
    }

    public Icon setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    public Icon addDescriptionLine(String line) {
        this.description.add(ChatColor.translateAlternateColorCodes('&', line));
        return this;
    }

    public Icon addDescriptionLines(String... lines) {
        for(String line : lines) this.addDescriptionLine(line);
        return this;
    }

    public Icon addFlag(ItemFlag flag) {
        if(!(this.flags.contains(flag))) this.flags.add(flag);
        return this;
    }

    public Icon addFlags(ItemFlag... flags) {
        for(ItemFlag flag : flags) this.addFlag(flag);
        return this;
    }

    public Optional<Consumer<Action>> getAction(Action.Type type) {
        return Optional.ofNullable(this.action.get(type));
    }

    public Icon withAction(Action.Type type, Consumer<Action> consumable) {
        this.action.put(type, consumable);
        return this;
    }

    public Icon onLeftClick(Consumer<Action> consumable) {
        return withAction(Action.Type.LEFT, consumable);
    }

    public Icon onRightClick(Consumer<Action> consumable) {
        return withAction(Action.Type.RIGHT, consumable);
    }

    public Icon onShiftLeftClick(Consumer<Action> consumable) {
        return withAction(Action.Type.SHIFT_LEFT, consumable);
    }

    public Icon onShiftRightClick(Consumer<Action> consumable) {
        return withAction(Action.Type.SHIFT_RIGHT, consumable);
    }

    public Icon onSlotChange(Consumer<Action> consumable) {
        return withAction(Action.Type.SLOT_CHANGE, consumable);
    }

    public Icon setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    public Icon setItem(ItemStack item) {
        this.defaultItem = item;
        return this;
    }

    public static Icon withItem(ItemStack item) {
        return new Icon(Material.AIR).setItem(item);
    }

    public static Icon withType(Material type) {
        return withDefault(type, 1);
    }

    public static Icon withDefault(Material type, int amount) {
        return new Icon(type).setAmount(amount);
    }

    public ItemStack asItem() {
        if(this.defaultItem != null) return this.defaultItem;

        ItemStack item = new ItemStack(this.type, this.amount);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        if(!(name.isEmpty())) meta.setDisplayName(this.name);
        meta.setLore(this.description);

        item.setItemMeta(meta);
        return item;
    }

}
