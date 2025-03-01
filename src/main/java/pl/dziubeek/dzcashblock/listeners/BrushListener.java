package pl.dziubeek.dzcashblock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Brush;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class BrushListener implements Listener {


    private final dzCashBlock plugin;

    public BrushListener(dzCashBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseBrush(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        String brushLevel = getBrushLevel(player);
        if (brushLevel == null) return;

        int xRadius = getBrushRadius(brushLevel, "x");
        int yRadius = getBrushRadius(brushLevel, "y");
        int zRadius = getBrushRadius(brushLevel, "z");

        if (xRadius <= 0 && yRadius <= 0 && zRadius <= 0) return;

        Bukkit.getScheduler().runTask(plugin, () -> clearArea(clickedBlock, xRadius, yRadius, zRadius, player));
    }


    private String getBrushLevel(Player player) {
        if (player.getInventory().getItemInMainHand() == null) return null;

        String itemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        for (String type : Brush.getBrushTypes()) {
            String expectedName = plugin.getConfig().getString("brushes." + type + ".name");
            if (expectedName != null && itemName.equalsIgnoreCase(ChatUtilities.fixColor(expectedName))) {
                return type;
            }
        }
        return null;
    }

    private int getBrushRadius(String brushLevel, String axis) {
        FileConfiguration config = plugin.getConfig();
        return config.getInt("brushes." + brushLevel + "." + axis, 1);
    }


    private void clearArea(Block centerBlock, int radiusX, int radiusY, int radiusZ, Player player) {
        Location targetBlock = centerBlock.getLocation();

        int centerX = targetBlock.getBlockX();
        int centerY = targetBlock.getBlockY();
        int centerZ = targetBlock.getBlockZ();

        int minX = centerX - (radiusX / 2);
        int maxX = centerX + (radiusX / 2) - (radiusX % 2 == 0 ? 1 : 0);
        int minY = centerY - (radiusY / 2);
        int maxY = centerY + (radiusY / 2) - (radiusY % 2 == 0 ? 1 : 0);
        int minZ = centerZ - (radiusZ / 2);
        int maxZ = centerZ + (radiusZ / 2) - (radiusZ % 2 == 0 ? 1 : 0);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = targetBlock.getWorld().getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR && !plugin.getConfigManager().getConfig().getList("settings.nobreak-blocks").contains(block.getType())) {
                        block.breakNaturally();
                        plugin.getMoneyDrop().DropMoney(new pl.dziubeek.dzcashblock.objects.Player(player), block);
                    }
                }
            }
        }
    }
}
