package pl.dziubeek.dzcashblock.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.dziubeek.dzcashblock.dzCashBlock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {

    private final dzCashBlock plugin;
    private FileConfiguration config;
    private FileConfiguration messagesconfig;
    private File msgfile;
    private FileConfiguration dataconfig;
    private File datafile;

    public ConfigManager(dzCashBlock plugin) {
        this.plugin = plugin;
        msgfile = new File(plugin.getDataFolder() + "/messages.yml");
        datafile = new File(plugin.getDataFolder() + "/data.yml");

    }

    public void reloadConfig() {
        plugin.reloadConfig();
        saveConfig();
    }

    public void reloadMsgConfig() {
        messagesconfig = YamlConfiguration.loadConfiguration(msgfile);
        saveMsgConfig();
    }

    public void reloadDataConfig() {
        dataconfig = YamlConfiguration.loadConfiguration(datafile);
        saveDataConfig();
    }

    private void createMessagesConfig() {
        File f = msgfile;
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    private void createDataConfig() {
        File f = datafile;
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadConfig() {
        createMessagesConfig();
        createDataConfig();
        messagesconfig = YamlConfiguration.loadConfiguration(msgfile);
        InputStream defaultStream = plugin.getResource("messages.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            messagesconfig.setDefaults(defaultConfig);
            messagesconfig.options().copyDefaults(true);
        }
        saveMsgConfig();
        dataconfig = YamlConfiguration.loadConfiguration(datafile);
        saveDataConfig();
        this.config = plugin.getConfig();
        plugin.getConfig().options().copyDefaults(true);
        saveConfig();
        messagesconfig = YamlConfiguration.loadConfiguration(msgfile);
        dataconfig = YamlConfiguration.loadConfiguration(datafile);
        this.config = plugin.getConfig();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public void saveMsgConfig() {
        try {
            this.messagesconfig.save(msgfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDataConfig() {
        try {
            this.dataconfig.save(datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDefault() {
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public FileConfiguration getMsgConfig() {
        return this.messagesconfig;
    }

    public FileConfiguration getDataConfig() {
        return this.dataconfig;
    }
}
