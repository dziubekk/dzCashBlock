package pl.dziubeek.dzcashblock.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Sender;

public abstract class Command implements CommandExecute {

    private String[] aliases;
    private String description;
    private String command;
    private String usage;
    private String permission;
    private Boolean playerOnly;
    private String commandpath;

    private final dzCashBlock plugin;
    private ConfigManager configManager;
    private FileConfiguration config;
    private FileConfiguration msgconfig;
    private FileConfiguration dataconfig;

    public Command(dzCashBlock plugin, String commandpath, String command, String description, String permission, String usage, Boolean playerOnly, String... aliases) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.config = configManager.getConfig();
        this.msgconfig = configManager.getMsgConfig();
        this.dataconfig = configManager.getDataConfig();
        this.command = command;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
        this.permission = permission;
        this.playerOnly = playerOnly;
        this.commandpath = commandpath;
        if(!this.commandpath.equalsIgnoreCase("none")) {
            this.aliases = config.getStringList("commands." + commandpath + ".aliases").toArray(new String[0]);
            this.description = config.getString("commands." + commandpath + ".description");
            this.usage = config.getString("commands." + commandpath + ".usage");
        }
        buildCommand();
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public Command setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public void buildCommand() {
        CommandManager.addCommand(this);
    }

    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public Command setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean isPlayerOnly() {
        return playerOnly;
    }

    @Override
    public void onCommand(Sender sender, Command command, String[] args) {
        if (isPlayerOnly() && !(sender.getSender() instanceof Player)) {
            sender.sendMessage("$prefix &cThis command is player-only.");
            return;
        }
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(msgconfig.getString("commands.nopermission").replace("$permission", String.valueOf(permission)));
            return;
        }

        executeCommand(sender, args);
    }

    public abstract void executeCommand(Sender sender, String[] args);
}
