package pl.dziubeek.dzcashblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.dziubeek.dzcashblock.commands.command.MainCommand;
import pl.dziubeek.dzcashblock.commands.command.admin.adminWalletCommand;
import pl.dziubeek.dzcashblock.commands.command.admin.fstoneCommand;
import pl.dziubeek.dzcashblock.commands.command.admin.getBrushCommand;
import pl.dziubeek.dzcashblock.commands.command.player.WalletCommand;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Brush;
import pl.dziubeek.dzcashblock.objects.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter {

    private static final List<Command> commands = new ArrayList<>();
    private final dzCashBlock plugin;

    public CommandManager(dzCashBlock plugin) {
        this.plugin = plugin;
    }

    public static void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public CommandManager loadCommands() {
        new MainCommand(plugin);
        new WalletCommand(plugin);
        new getBrushCommand(plugin);
        new fstoneCommand(plugin);
        new adminWalletCommand(plugin);
        return this;
    }

    public void registerCommands() {
        for(Command cmd : getCommands()) {
            plugin.getCommand(cmd.getCommand()).setAliases(Arrays.asList(cmd.getAliases()));
            plugin.getCommand(cmd.getCommand()).setDescription(cmd.getDescription());
            plugin.getCommand(cmd.getCommand()).setUsage(cmd.getUsage());
            plugin.getCommand(cmd.getCommand()).setTabCompleter(this);
            plugin.getCommand(cmd.getCommand()).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (Command cmd : commands) {
            if (cmd.getCommand().equalsIgnoreCase(command.getName()) ||
                    (cmd.getAliases() != null && Arrays.asList(cmd.getAliases()).contains(command.getName()))) {
                cmd.onCommand(new Sender(sender), cmd, args);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();
        String cmd = command.getName();
        for(Command cmd2 : commands) {
            if(cmd2.getCommand().equalsIgnoreCase(cmd) || Arrays.asList(cmd2.getAliases()).contains(cmd)) {
                cmd = cmd2.getCommand();
            }
        }
        switch (cmd.toLowerCase()) {
            case "dzcashblock":
                if (args.length == 1) {
                    suggestions = filter(Arrays.asList("help", "authors", "reload"), args[0]);
                }
                break;
            case "getbrush":
                if (args.length == 1) {
                    suggestions = filter(Brush.getBrushTypes(), args[0]);
                } else if (args.length == 2) {
                    suggestions = filter(getOnlinePlayerNames(), args[1]);
                }
                break;
            case "fstone":
                if (args.length == 1) {
                    suggestions = filter(Arrays.asList("10", "20", "50", "0.10", "0.20", "0.50", "0.01", "0.02", "0.05"), args[0]);
                }
                break;
            case "adminwallet":
                if (args.length == 1) {
                    suggestions = filter(Arrays.asList("check", "add", "set", "take"), args[0]);
                } else if (args.length == 2) {
                    suggestions = filter(getOnlinePlayerNames(), args[1]);
                } else if (args.length == 3) {
                    suggestions = filter(Arrays.asList("10", "20", "50", "100", "200", "500", "1000", "2000", "5000", "10000", "0.10", "0.20", "0.50", "1.00", "2.00", "5.00", "0.01", "0.02", "0.05"), args[2]);
                }
        }

        return suggestions;
    }

    private List<String> filter(List<String> suggestions, String input) {
        return suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<String> getOnlinePlayerNames() {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
        return playerNames;
    }

}
