package pl.dziubeek.dzcashblock.commands.command.admin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.configs.DataHandler;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class fstoneCommand extends Command {

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration msgconfig;
    private DataHandler dh;

    public fstoneCommand(dzCashBlock plugin) {
        super(plugin, "fstone","fstone", "Puts money in the target block!", "dzcashblock.admin",
                "/fstone <amount>", true, "putblock");
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.dh = plugin.getDataHandler();
        this.msgconfig = configManager.getMsgConfig();
    }

    private Block getTarget(Player player, Integer range) {
        BlockIterator bi= new BlockIterator(player, range);
        Block lastBlock = bi.next();
        while (bi.hasNext()) {
            lastBlock = bi.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }
        return lastBlock;
    }

    @Override
    public void executeCommand(Sender sender, String[] args) {
        if(ChatUtilities.isDouble(args[0])) {
            this.dh.addFStoneLoc(getTarget((Player)sender.getSender(), 100).getLocation(), Double.valueOf(args[0]));
            sender.sendMessage(configManager.getMsgConfig().getString("commands.fstone.money-put").replace("$value", args[0]));
        } else {
            sender.sendMessage(configManager.getMsgConfig().getString("commands.invalid-number-format"));
        }
    }
}
