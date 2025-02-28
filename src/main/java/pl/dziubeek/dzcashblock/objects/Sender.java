package pl.dziubeek.dzcashblock.objects;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.utils.ChatUtilities;

public class Sender {

    private CommandSender sender;

    public Sender(CommandSender sender) {
        this.sender = sender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(ChatUtilities.fixColor(message));
    }

    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    public void sendTitle(String title, String subtitle) {
        if(sender instanceof Player) {
            ((Player) sender).sendTitle(ChatUtilities.fixColor(title), ChatUtilities.fixColor(subtitle));
            return;
        } else {
            sender.sendMessage("You can't handle TitlePacket!");
        }
    }

    public CommandSender getSender() {
        return sender;
    }
}
