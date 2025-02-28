package pl.dziubeek.dzcashblock.commands.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.configs.DataHandler;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Brush;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class getBrushCommand extends Command {

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration config;
    private FileConfiguration msgconfig;
    private FileConfiguration dataconfig;

    public getBrushCommand(dzCashBlock plugin) {
        super(plugin, "getbrush","getbrush", "Gives you brush!", "dzcashblock.admin",
                "/getbrush <type> [player]", true, "givebrush", "gbrush", "getbr", "gbr");
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.dataconfig = configManager.getDataConfig();
        this.msgconfig = configManager.getMsgConfig();
    }

    @Override
    public void executeCommand(Sender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(msgconfig.getString("commands.specifybrushlevel"));
            return;
        }
        Player targetPlayer;
        if (args.length == 2) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage(msgconfig.getString("commands.noplayerfound"));
                return;
            }
        } else {
            if(!(sender.getSender() instanceof Player)) {
                sender.sendMessage(msgconfig.getString("commands.only-player-command"));
                return;
            } else {
                targetPlayer = (Player) sender.getSender();
            }
        }
        Brush brush = plugin.getDataHandler().loadBrush(args[0]);
        if (brush == null) {
            sender.sendMessage(msgconfig.getString("commands.invalidbrushlevel"));
            return;
        }
        ItemStack brushItem = brush.build();
        targetPlayer.getInventory().addItem(brushItem);
        targetPlayer.sendMessage(ChatUtilities.fixColor(msgconfig.getString("commands.brushreceived").replace("$brush", brush.getName())));
    }
}
