package pl.dziubeek.dzcashblock.commands.command.admin;

import org.bukkit.configuration.file.FileConfiguration;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.configs.DataHandler;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Player;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class adminWalletCommand extends Command {

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration msgconfig;
    private DataHandler dh;

    public adminWalletCommand(dzCashBlock plugin) {
        super(plugin, "adminwallet","adminwallet", "Edit wallet of other player!", "dzcashblock.admin",
                "/adminwallet <player>", true, "admin-wallet", "awallet");
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.dh = plugin.getDataHandler();
        this.msgconfig = configManager.getMsgConfig();
    }

    @Override
    public void executeCommand(Sender sender, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(msgconfig.getString("commands.usage").replace("$usage", getUsage()));
            return;
        } else if(args.length == 1 && args[0].equalsIgnoreCase("check")) {
            Double balance = new Player(sender.getSender().getName()).getBalance();
            String title = msgconfig.getString("commands.wallet.title").replace("$balance", String.format("%.2f", balance)).replace("$currency", configManager.getConfig().getString("settings.currency-symbol"));
            String subtitle = msgconfig.getString("commands.wallet.subtitle").replace("$balance", String.format("%.2f", balance)).replace("$currency", configManager.getConfig().getString("settings.currency-symbol"));
            sender.sendTitle(title, subtitle);
            return;
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("check")){
                Double balance = new Player(args[1]).getBalance();
                String title = msgconfig.getString("commands.admin-wallet.check.title").replace("$balance", String.format("%.2f", balance)).replace("$player", args[1]).replace("$currency", configManager.getConfig().getString("settings.currency-symbol"));
                String subtitle = msgconfig.getString("commands.admin-wallet.check.subtitle").replace("$balance", String.format("%.2f", balance)).replace("$player", args[1]).replace("$currency", configManager.getConfig().getString("settings.currency-symbol"));
                sender.sendTitle(title, subtitle);
                return;
            } else {
                sender.sendMessage(msgconfig.getString("commands.usage").replace("$usage", getUsage()));
                return;
            }
        } else if(args.length == 3) {
            switch(args[0]) {
                case "set":
                    if(ChatUtilities.isDouble(args[2])) {
                        Player player = new Player(args[1]);
                        player.setPlayermoney(Double.valueOf(args[2]));
                        String message = msgconfig.getString("commands.admin-wallet.set").replace("$value", args[2]).replace("$currency", configManager.getConfig().getString("settings.currency-symbol").replace("$player", args[1]));
                        sender.sendMessage(message);
                        return;
                    } else {
                        sender.sendMessage(configManager.getMsgConfig().getString("commands.invalid-number-format"));
                    }
                    break;
                case "add":
                    if(ChatUtilities.isDouble(args[2])) {
                        Player player = new Player(args[1]);
                        player.addPlayermoney(Double.valueOf(args[2]));
                        String message = msgconfig.getString("commands.admin-wallet.add").replace("$value", args[2]).replace("$currency", configManager.getConfig().getString("settings.currency-symbol").replace("$player", args[1]));
                        sender.sendMessage(message);
                        return;
                    } else {
                        sender.sendMessage(configManager.getMsgConfig().getString("commands.invalid-number-format"));
                    }
                    break;
                case "take":
                    if(ChatUtilities.isDouble(args[2])) {
                        Player player = new Player(args[1]);
                        player.removePlayermoney(Double.valueOf(args[2]));
                        String message = msgconfig.getString("commands.admin-wallet.take").replace("$value", args[2]).replace("$currency", configManager.getConfig().getString("settings.currency-symbol").replace("$player", args[1]));
                        sender.sendMessage(message);
                        return;
                    } else {
                        sender.sendMessage(configManager.getMsgConfig().getString("commands.invalid-number-format"));
                    }
                    return;
            }
            sender.sendMessage(msgconfig.getString("commands.usage").replace("$usage", getUsage()));
        }
    }
}
