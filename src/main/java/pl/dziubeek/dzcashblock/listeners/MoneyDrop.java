package pl.dziubeek.dzcashblock.listeners;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.configs.DataHandler;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Player;
import pl.dziubeek.dzcashblock.objects.Sender;

import java.util.Map;
import java.util.Random;

public class MoneyDrop {

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration config;

    public MoneyDrop(dzCashBlock plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.config = configManager.getConfig();
    }

    private boolean shouldDrop(double chance) {
        Random random = new Random();
        return random.nextDouble() < chance;
    }

    public void DropMoney(Player p, Block b) {
        if(!checkFStone(b, p)) {
            if (config.contains("drops")) {
                ConfigurationSection dropsConfig = config.getConfigurationSection("drops");
                for(String key : dropsConfig.getKeys(false)) {
                    double chance = dropsConfig.getDouble(key + ".chance");
                    Double value = dropsConfig.getDouble(key + ".value");
                    String name = dropsConfig.getString(key + ".name");
                    if (shouldDrop(chance)) {
                        giveMoneyToPlayer(p, name, value);
                    }
                }
            }
        }
    }

    private boolean checkFStone(Block b, Player p) {
        DataHandler dh = plugin.getDataHandler();
        Double value = dh.checkFStone(b.getLocation());
        if(value != null) {
            giveMoneyToPlayer(p, String.valueOf(value), value);
            return true;
        }
        return false;
    }

    public void giveMoneyToPlayer(Player player, String name, Double value) {
        Sender sender = new Sender(player.getPlayer());
        sender.sendTitle(configManager.getMsgConfig().getString("drops.title").replace("$value", name).replace("$currency", config.getString("settings.currency-symbol")), configManager.getMsgConfig().getString("drops.subtitle").replace("$value", name).replace("$currency", config.getString("settings.currency-symbol")));
        player.addPlayermoney(value).savePlayer();
    }
}
