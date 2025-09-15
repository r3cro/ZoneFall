package dev.recro.zoneFall.commands;

import dev.recro.zoneFall.ZoneFall;
import dev.recro.zoneFall.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class DebugCommand implements CommandExecutor {

    private ZoneFall plugin;

    public DebugCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
           for (Map.Entry<UUID, PlayerData> entry : plugin.getPlayerDataManager().getAllData().entrySet()) {
               player.sendMessage("" + entry);
           }
        }

        return true;
    }
}
