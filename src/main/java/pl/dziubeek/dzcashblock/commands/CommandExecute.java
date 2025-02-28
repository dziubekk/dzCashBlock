package pl.dziubeek.dzcashblock.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.objects.Sender;

public interface CommandExecute {

    public abstract void onCommand(Sender sender, Command command, String[] args);

}
