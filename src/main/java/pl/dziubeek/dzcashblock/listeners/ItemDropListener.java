package pl.dziubeek.dzcashblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Brush;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class ItemDropListener implements Listener {
    private final dzCashBlock plugin;

    public ItemDropListener(dzCashBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if(getBrushLevel(e.getPlayer()) != null) {
            if(plugin.getConfigManager().getConfig().getBoolean("settings.cancel-drop-brush")) {
                e.setCancelled(true);
                Sender s = new Sender(e.getPlayer());
                s.sendMessage(plugin.getConfigManager().getMsgConfig().getString(""));
            }
        }
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
}
