package pl.dziubeek.dzcashblock.commands.command.player;

import org.bukkit.entity.Player;
import pl.dziubeek.dzcashblock.commands.Command;
import pl.dziubeek.dzcashblock.dzCashBlock;
import pl.dziubeek.dzcashblock.objects.Sender;
import pl.dziubeek.dzcashblock.utils.TopBalanceUtil;

public class balanceTopCommand extends Command {

    private dzCashBlock plugin;

    public balanceTopCommand(dzCashBlock plugin) {
        super(plugin, "balancetop","balancetop", "Shows the richest players!", "dzcashblock.player",
                "/balancetop", true, "baltop", "topwallet");
        this.plugin = plugin;
    }

    @Override
    public void executeCommand(Sender sender, String[] args) {
        TopBalanceUtil.openTopBalanceGUI((Player) sender.getSender());
    }
}
