package pl.dziubeek.dzcashblock.commands.command.player;

import org.bukkit.configuration.file.FileConfiguration;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Player;
import pl.dziubeek.dzcashblock.objects.Sender;

public class WalletCommand extends Command {

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration config;
    private FileConfiguration msgconfig;
    private FileConfiguration dataconfig;

    public WalletCommand(dzCashBlock plugin) {
        super(plugin, "wallet","wallet", "Check your wallet-balance!", "dzcashblock.player",
                "/wallet", true, "mywallet");
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.dataconfig = configManager.getDataConfig();
        this.msgconfig = configManager.getMsgConfig();
    }

    @Override
    public void executeCommand(Sender sender, String[] args) {
        Double balance = new Player(sender.getSender().getName()).getBalance();
        sender.sendTitle(msgconfig.getString("commands.wallet.title").replace("$balance", String.valueOf(balance)), msgconfig.getString("commands.wallet.subtitle").replace("$balance", String.valueOf(balance)));
    }
}
