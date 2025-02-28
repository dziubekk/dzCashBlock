package pl.dziubeek.dzcashblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.dzCashBlock;

import java.util.List;

public class ChatUtilities {

    private static ConfigManager configManager = dzCashBlock.getPlugin(dzCashBlock.class).getConfigManager();

    public static String fixColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace("$prefix", ChatColor.translateAlternateColorCodes('&', configManager.getMsgConfig().getString("plugin.prefix"))));
    }

    public static boolean isDouble(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void broadcast(String text) {
        for (Player s : Bukkit.getOnlinePlayers())
            s.sendMessage(fixColor(text));
    }

    public static List<String> fixColor(List<String> input) {
        if (input == null || input.isEmpty())
            return input;
        for (int i = 0; i < input.size(); i++)
            input.set(i, fixColor(input.get(i)));
        return input;
    }
}
