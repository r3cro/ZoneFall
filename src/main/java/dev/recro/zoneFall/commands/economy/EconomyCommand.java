package dev.recro.zoneFall.commands.economy;

import dev.recro.zoneFall.ZoneFall;
import dev.recro.zoneFall.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyCommand implements CommandExecutor {

    private ZoneFall plugin;

    public EconomyCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (sender instanceof Player && !sender.hasPermission("zonefall.economy")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        switch (args.length) {
            case 0, 1, 2:
                sender.sendMessage("/eco <player> <give,set,remove> <amount>");
                break;
            case 3:
                Player target = Bukkit.getPlayer(args[0]);
                if (!isNumeric(args[2])) {
                    target.sendMessage(args[2] + " is not a valid number");
                    return true;
                }
                if(target == null) {
                    sender.sendMessage(args[0] + " is not a valid player");
                    return true;
                }
                double amount = Double.parseDouble(args[2]);
                switch (args[1].toUpperCase()) {
                    case "ADD", "GIVE":
                        plugin.getPlayerDataManager().getPlayerData(target).addBalance(amount, 1);
                        sender.sendMessage(ChatColor.GREEN + "Added " + target.getName() + "'s balance to: $" + ChatColor.RED + amount);
                        break;
                    case "SET":
                        plugin.getPlayerDataManager().getPlayerData(target).setBalance(amount);
                        sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s balance to: $" + ChatColor.RED + amount);
                        break;
                    case "REMOVE":
                        plugin.getPlayerDataManager().getPlayerData(target).removeBalance(amount);
                        sender.sendMessage(ChatColor.GREEN + "Removed " + target.getName() + "'s balance to: $" + ChatColor.RED + amount);
                        break;
                    default:
                        sender.sendMessage("/eco <player> <give,set,remove> <amount>");
                }
                break;
            default:
                sender.sendMessage("/eco <give,set,remove> <player> <amount>");
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
