package pl.dziubeek.dzcashblock.commands.command;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.commands.CommandManager;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

import java.util.List;

public class MainCommand extends Command {

    private final dzCashBlock plugin;
    private final ConfigManager configManager;
    private final CommandManager cmdManager;
    private FileConfiguration msgconfig;

    public MainCommand(dzCashBlock plugin) {
        super(plugin, "none","dzcashblock", "Get help of plugin!", "dzcashblock.player",
                "/dzcashblock <help/reload/authors>", false, "cashblock", "dzcb", "cashb", "cblock", "dzcblock", "dzcashb");
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.cmdManager = plugin.getCommandManager();
    }


    @Override
    public void executeCommand(Sender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("$p &7Poprawne uzycie: &a" + getUsage());
            return;
        } else {
            if(args[0].equalsIgnoreCase("help")) {
                List<String> help = msgconfig.getStringList("commands.help");
                help = ChatUtilities.fixColor(help);
                for(String s : help) {
                    sender.sendMessage(s);
                }
            } else if(args[0].equalsIgnoreCase("authors")) {
                List<String> authors = plugin.getDescription().getAuthors();
                sender.sendMessage("$prefix &6Plugin authors: &b" + String.join("&6, &b", authors));
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(sender.hasPermission("dzcashblock.admin")) {
                    configManager.reloadConfig();
                    configManager.reloadMsgConfig();
                    configManager.reloadDataConfig();
                    cmdManager.loadCommands();
                    this.msgconfig = configManager.getMsgConfig();
                    sender.sendMessage("$prefix To properly load new aliases, description and usages, you must restart the server!");
                    sender.sendMessage(msgconfig.getString("plugin.reload"));
                } else {
                    sender.sendMessage(msgconfig.getString("commands.nopermission").replace("$permission", "dzcashblock.admin"));
                }
            } else {
                sender.sendMessage(msgconfig.getString("commands.usage").replace("$usage", getUsage()));
                return;
            }
        }
    }
}
