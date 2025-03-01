package pl.dziubeek.dzcashblock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dziubeek.dzcashblock.commands.CommandManager;
import pl.dziubeek.dzcashblock.configs.ConfigManager;
import pl.dziubeek.dzcashblock.configs.DataHandler;
import pl.dziubeek.dzcashblock.listeners.BlockBreakListener;
import pl.dziubeek.dzcashblock.listeners.BrushListener;
import pl.dziubeek.dzcashblock.listeners.ItemDropListener;
import pl.dziubeek.dzcashblock.listeners.MoneyDrop;
import pl.dziubeek.dzcashblock.objects.Sender;

public final class dzCashBlock extends JavaPlugin {

    private CommandManager commandManager;
    private ConfigManager configManager;
    private DataHandler dataHandler;
    private MoneyDrop moneyDrop;

    @Override
    public void onEnable() {
        // Plugin startup logic
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        Sender sender = new Sender(Bukkit.getConsoleSender());
        sender.sendMessage("$prefix Loading plugin...");
        sender.sendMessage("$prefix Loading commands...");
        commandManager = new CommandManager(this);
        sender.sendMessage("$prefix Implementing...");
        dataHandler = new DataHandler(this);
        moneyDrop = new MoneyDrop(this);
        sender.sendMessage("$prefix Registering commands...");
        commandManager.loadCommands();
        commandManager.registerCommands();
        sender.sendMessage("$prefix Registering events...");
        this.getServer().getPluginManager().registerEvents(new BrushListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        sender.sendMessage("$prefix Plugin has been loaded!");
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public DataHandler getDataHandler() {
        return this.dataHandler;
    }

    public MoneyDrop getMoneyDrop() {
        return moneyDrop;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
