package dev.recro.zoneFall.commands.economy;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import dev.recro.zoneFall.ZoneFall;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SellCommand implements CommandExecutor {

    private ZoneFall plugin;

    public SellCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player.");
        }

        Player player = (Player) sender;

        if (!player.hasPermission("zonefall.sell")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length == 0) {
            ChestGui gui = new ChestGui(4, "Sell Items");
            gui.setOnClose(event -> {
                if (event.getInventory().getContents() == null) return;
                double sellAmount = 0;
                double sellMultiplier = plugin.getPlayerDataManager().getPlayerData(player).getSellMultiplier();
                 for (ItemStack item : event.getInventory().getContents()) {
                     if (item == null) continue;
                     sellAmount = sellAmount + plugin.getItemWorthFile().getDouble("worth." + item.getType() + ".value")* item.getAmount();
                     player.sendMessage(""+item.getType());
                 }
                 plugin.getPlayerDataManager().getPlayerData(player).addBalance(sellAmount, sellMultiplier);
                 player.sendMessage(ChatColor.GREEN + "+ $" + sellAmount*sellMultiplier);
                 player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
             });
            gui.show(player);
        }
        return true;
    }
}
