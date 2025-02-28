package pl.dziubeek.dzcashblock.objects;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class Brush {
    private final String level;
    private final String name;
    private final List<String> lore;
    private final int xRadius;
    private final int yRadius;
    private final int zRadius;
    private final Material material;
    private final dzCashBlock plugin;

    public Brush(dzCashBlock plugin, String level, String name, List<String> lore, int xRadius, int yRadius, int zRadius, Material material) {
        this.level = level;
        this.name = name;
        this.lore = lore;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.zRadius = zRadius;
        this.material = material;
        this.plugin = plugin;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getXRadius() {
        return xRadius;
    }

    public int getYRadius() {
        return yRadius;
    }

    public int getZRadius() {
        return zRadius;
    }

    public Material getMaterial() {
        return material;
    }

    public static List<String> getBrushTypes() {
        return new ArrayList<>(dzCashBlock.getPlugin(dzCashBlock.class).getConfig().getConfigurationSection("brushes").getKeys(false));
    }

    public ItemStack build() {
        ItemStack is = new ItemStack(this.material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatUtilities.fixColor(name));
        im.setLore(ChatUtilities.fixColor(lore));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        is.setItemMeta(im);
        return is;
    }
}
