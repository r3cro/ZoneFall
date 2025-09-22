package dev.recro.zoneFall.commands;

import dev.recro.zoneFall.ZoneFall;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DebugCommand implements CommandExecutor {

    private ZoneFall plugin;

    public DebugCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(!player.isOp()) return true;

        if(args.length == 0) {
            plugin.getPlayerDataManager().getPlayerData(player).setSellMultiplier(1);
        }

        return true;
    }
}
