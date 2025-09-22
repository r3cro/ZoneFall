package dev.recro.zoneFall.commands.economy;

import dev.recro.zoneFall.ZoneFall;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private ZoneFall plugin;
    public BalanceCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("zonefall.balance")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.WHITE + plugin.getPlayerDataManager().getPlayerData(player).getBalance());
            return true;
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s Balance: " + ChatColor.WHITE + plugin.getPlayerDataManager().getPlayerData(target).getBalance());
        }
        return true;
    }
}
