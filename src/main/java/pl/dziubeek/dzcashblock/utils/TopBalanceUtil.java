package pl.dziubeek.dzcashblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Sender;

import java.util.*;
import java.util.stream.Collectors;

public class TopBalanceUtil {

    private static dzCashBlock plugin = dzCashBlock.getPlugin(dzCashBlock.class);

    public static void openTopBalanceGUI(Player player) {
        FileConfiguration config = plugin.getConfigManager().getConfig();
        FileConfiguration data = plugin.getConfigManager().getDataConfig();

        // Pobranie wartości z configu
        String title = ChatUtilities.fixColor(config.getString("settings.balancetop.gui-title", "BALANCE TOP"));
        int rows = config.getInt("settings.balancetop.gui-rows", 3);
        String noAnswer = ChatUtilities.fixColor(config.getString("settings.balancetop.gui-noanswer", "&6N/A"));

        int size = rows * 9; // Każdy rząd ma 9 slotów
        Inventory gui = Bukkit.createInventory(null, size, title);

        ConfigurationSection playersSection = data.getConfigurationSection("players");
        if (playersSection == null) {
            player.sendMessage("Brak danych o graczach!");
            return;
        }

        Map<String, Double> balances = new HashMap<>();
        for (String uuid : playersSection.getKeys(false)) {
            double balance = data.getDouble("players." + uuid + ".balance", 0);
            balances.put(uuid, balance);
        }

        List<Map.Entry<String, Double>> sortedBalances = balances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(size)
                .collect(Collectors.toList());

        // Pobranie ustawień playeritem
        ConfigurationSection itemConfig = config.getConfigurationSection("settings.balancetop.gui-items.playeritem");
        if (itemConfig == null) {
            plugin.getLogger().warning("No data found!");
            return;
        }

        Material material = Material.PLAYER_HEAD;
        String itemName = itemConfig.getString("name", "$index. $player");
        List<String> itemLore = itemConfig.getStringList("lore");

        // Ustawianie przedmiotów w GUI
        int slot = 0;
        int index = 1;
        for (int i = 0; i < size; i++) {
            OfflinePlayer offlinePlayer = (i < sortedBalances.size()) ?
                    Bukkit.getOfflinePlayer(UUID.fromString(sortedBalances.get(i).getKey())) : null;

            double balance = (offlinePlayer != null) ? sortedBalances.get(i).getValue() : 0.0;
            String playerName = (offlinePlayer != null && offlinePlayer.getName() != null) ? offlinePlayer.getName() : noAnswer;

            // Podmiana placeholderów w nazwie i lore
            String finalName = itemName
                    .replace("$index", String.valueOf(index))
                    .replace("$player", playerName)
                    .replace("$balance", String.format("%.2f", balance));

            List<String> finalLore = new ArrayList<>();
            for (String line : itemLore) {
                finalLore.add(line
                        .replace("$index", String.valueOf(index))
                        .replace("$player", playerName)
                        .replace("$balance", String.format("%.2f", balance)));
            }

            // Tworzenie przedmiotu
            ItemStack item = new ItemStack(material);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatUtilities.fixColor(finalName));
                meta.setLore(ChatUtilities.fixColor(finalLore));
                meta.setOwningPlayer(offlinePlayer);
                item.setItemMeta(meta);
            }

            if (slot < gui.getSize()) {
                gui.setItem(slot, item);
            }

            slot++;
            index++;
        }

        player.openInventory(gui);
    }



}
