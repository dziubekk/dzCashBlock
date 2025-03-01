package pl.dziubeek.dzcashblock.objects;

import org.bukkit.Bukkit;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.dzCashBlock;

import java.util.UUID;

public class Player {

    private dzCashBlock plugin = dzCashBlock.getPlugin(dzCashBlock.class);
    private ConfigManager configManager = plugin.getConfigManager();


    private final org.bukkit.entity.Player player;
    private final String nick;
    private final UUID uuid;
    private Double playermoney;

    public Player(String nick) {
        this.player = Bukkit.getPlayer(nick);
        this.nick = nick;
        this.uuid = player.getUniqueId();
        this.playermoney = getPlayerMoney();
    }

    public Player(org.bukkit.entity.Player player) {
        this.player = player;
        this.nick = player.getName();
        this.uuid = player.getUniqueId();
        this.playermoney = getPlayerMoney();
    }

    public Player(org.bukkit.entity.Player player, Double playermoney) {
        this.player = player;
        this.nick = player.getName();
        this.uuid = player.getUniqueId();
        this.playermoney = playermoney;
    }

    public Player(String nick, Double playermoney) {
        this.player = Bukkit.getPlayer(nick);
        this.nick = nick;
        this.uuid = player.getUniqueId();
        this.playermoney = playermoney;
    }

    private Double getPlayerMoney() {
        return configManager.getDataConfig().getDouble("players." + uuid.toString() + ".balance");
    }

    public String getNick() {
        return nick;
    }

    public org.bukkit.entity.Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Double getBalance() {
        return playermoney;
    }

    public Player setPlayermoney(Double playermoney) {
        this.playermoney = playermoney;
        return this;
    }

    public Player addPlayermoney(Double addmoney) {
        this.playermoney = this.playermoney + addmoney;
        return this;
    }

    public Player removePlayermoney(Double removemoney) {
        this.playermoney = this.playermoney - removemoney;
        return this;
    }

    public void savePlayer() {
        plugin.getDataHandler().savePlayer(this);
    }
}
