package pl.dziubeek.dzcashblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Player;

public class BlockBreakListener implements Listener {

    private final dzCashBlock plugin;

    public BlockBreakListener(dzCashBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        plugin.getMoneyDrop().DropMoney(new Player(event.getPlayer()), event.getBlock());
    }
}
