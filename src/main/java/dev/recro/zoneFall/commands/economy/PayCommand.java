package dev.recro.zoneFall.commands.economy;

import dev.recro.zoneFall.ZoneFall;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {

    private ZoneFall plugin;

    public PayCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("zonefall.pay")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(ChatColor.RED + "/pay <player> <amount>");
            return true;
        }

        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "/pay <player> <amount>");
            return true;
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!isNumeric(args[1])) {
                player.sendMessage(args[1] + " is not a valid number");
                return true;
            }

            double amount = Double.parseDouble(args[1]);
            double senderBalance = plugin.getPlayerDataManager().getPlayerData(player).getBalance();

            if (senderBalance < amount) {
                player.sendMessage(ChatColor.RED + "You do not have enough funds");
                return true;
            }

            plugin.getPlayerDataManager().getPlayerData(player).removeBalance(amount);
            plugin.getPlayerDataManager().getPlayerData(target).addBalance(amount, 1);
            player.sendMessage(ChatColor.RED + "-$" + amount);
            target.sendMessage(ChatColor.GREEN + "+$" + amount);
        }

        return true;
    }

    private boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
