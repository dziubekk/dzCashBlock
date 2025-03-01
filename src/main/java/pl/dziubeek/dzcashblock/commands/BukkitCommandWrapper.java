package pl.dziubeek.dzcashblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.dziubeek.dzcashblock.objects.Sender;

import java.util.Arrays;

public class BukkitCommandWrapper extends Command {

    private final pl.dziubeek.dzcashblock.commands.Command customCommand;

    public BukkitCommandWrapper(pl.dziubeek.dzcashblock.commands.Command customCommand) {
        super(customCommand.getCommand());
        this.customCommand = customCommand;
        this.setAliases(Arrays.asList(customCommand.getAliases()));
        this.setDescription(customCommand.getDescription());
        this.setUsage(customCommand.getUsage());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        customCommand.onCommand(new Sender(sender), customCommand, args);
        return true;
    }

}