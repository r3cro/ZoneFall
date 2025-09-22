package dev.recro.zoneFall.commands.economy;

import dev.recro.zoneFall.ZoneFall;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorthCommand implements CommandExecutor {

    private ZoneFall plugin;

    public WorthCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to execute this command");
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (args.length == 0) {
            if (itemInHand == null) {
                player.sendMessage(ChatColor.RED + "/worth <item>");
                return true;
            }
            double worth = plugin.getItemWorthFile().getDouble("worth." + player.getInventory().getItemInMainHand().getType() + ".value");
            player.sendMessage("" + ChatColor.GREEN + player.getInventory().getItemInMainHand().getType() + "'s Worth is: " + ChatColor.WHITE + worth);
        } else if (args.length == 1) {
            double worth = plugin.getItemWorthFile().getDouble("worth." + args[0].toUpperCase() + ".value");
            player.sendMessage("" + ChatColor.GREEN + args[0] + "'s Worth is: " + ChatColor.WHITE + worth);
        }

        return true;
    }
}
