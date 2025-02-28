package pl.dziubeek.dzcashblock.configs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Brush;
import pl.dziubeek.dzcashblock.objects.Player;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private ConfigManager configManager;
    private dzCashBlock plugin;


    public DataHandler(dzCashBlock plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public Player loadPlayer(String path) {
        String nick = configManager.getDataConfig().getString(path + ".nickname");
        Double playermoney = configManager.getDataConfig().getDouble(path + ".balance");
        return new Player(nick, playermoney);
    }

    public void savePlayer(Player p) {
        String nick = p.getNick();
        Double playermoney = p.getBalance();
        configManager.getDataConfig().set("players." + p.getUuid().toString() + ".nick", nick);
        configManager.getDataConfig().set("players." + p.getUuid().toString() + ".balance", playermoney);
        configManager.saveDataConfig();
    }

    public Brush loadBrush(String level) {
        String name = configManager.getConfig().getString("brushes." + level + ".name");
        List<String> lore = configManager.getConfig().getStringList("brushes." + level + ".lore");
        Integer x = configManager.getConfig().getInt("brushes." + level + ".x");
        Integer y = configManager.getConfig().getInt("brushes." + level + ".y");
        Integer z = configManager.getConfig().getInt("brushes." + level + ".z");
        Material brushmaterial = Material.valueOf(configManager.getConfig().getString("brushes." + level + ".material").toUpperCase());
        return new Brush(plugin, level, name, lore, x, y, z, brushmaterial);
    }

    public Double checkFStone(Location loc) {
        String locationKey = loc.getBlockX() + "_" +
                loc.getBlockY() + "_" +
                loc.getBlockZ();

        if (configManager.getDataConfig().contains("fstone." + locationKey)) {
            Double amount = configManager.getDataConfig().getDouble("fstone." + locationKey);
            removeFStoneLoc(loc);
            return amount;
        }
        return null;
    }

    public void addFStoneLoc(Location loc, Double d) {
        String locationKey = loc.getBlockX() + "_" +
                loc.getBlockY() + "_" +
                loc.getBlockZ();
        configManager.getDataConfig().set("fstone." + locationKey, d);
        configManager.saveDataConfig();
    }

    public void removeFStoneLoc(Location loc) {
        String locationKey = loc.getBlockX() + "_" +
                loc.getBlockY() + "_" +
                loc.getBlockZ();
        configManager.getDataConfig().set("money_positions." + locationKey, null);
        configManager.saveDataConfig();
    }


}
