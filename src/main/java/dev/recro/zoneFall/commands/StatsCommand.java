package dev.recro.zoneFall.commands;

import dev.recro.zoneFall.ZoneFall;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class StatsCommand implements CommandExecutor {

    ZoneFall plugin;

    public StatsCommand(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
        }

        Player player = (Player) sender;

        if(args.length == 0 ) {
            player.sendMessage("Loading your stats");
            int kills = plugin.getPlayerDataManager().getPlayerData(player).getKills();
            int mobkills = plugin.getPlayerDataManager().getPlayerData(player).getMobKills();
            int deaths = plugin.getPlayerDataManager().getPlayerData(player).getDeaths();
            double balance = plugin.getPlayerDataManager().getPlayerData(player).getBalance();
            long firstJoin = plugin.getPlayerDataManager().getPlayerData(player).getFirstJoin();
            long lastJoin = plugin.getPlayerDataManager().getPlayerData(player).getLastJoin();

            player.sendMessage(player.getName() + "'s Stats");
            player.sendMessage("Kills:" + kills);
            player.sendMessage("Mob Kills:" + mobkills);
            player.sendMessage("Deaths:" + deaths);
            player.sendMessage("Balance:" + balance);
            player.sendMessage("First played:" + firstJoin);
            player.sendMessage("Last Played:" + lastJoin);
            return true;
        } else

        if(args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!target.isOnline()) {
                player.sendMessage(ChatColor.RED + target.getName() + " is not online.");
                return true;
            }
            if (args[0].equalsIgnoreCase(target.getName())) {
                int kills = plugin.getPlayerDataManager().getPlayerData(target).getKills();
                int mobkills = plugin.getPlayerDataManager().getPlayerData(target).getKills();
                int deaths = plugin.getPlayerDataManager().getPlayerData(target).getDeaths();
                double balance = plugin.getPlayerDataManager().getPlayerData(target).getBalance();
                long firstJoin = plugin.getPlayerDataManager().getPlayerData(target).getFirstJoin();
                long lastJoin = plugin.getPlayerDataManager().getPlayerData(target).getLastJoin();

                player.sendMessage(target.getName() + "'s Stats");
                player.sendMessage("Kills: " + kills);
                player.sendMessage("Mob Kills: " + mobkills);
                player.sendMessage("Deaths: " + deaths);
                player.sendMessage("Balance: " + balance);
                player.sendMessage("First played: " + firstJoin);
                player.sendMessage("Last Played: " + lastJoin);
            }
        }

        return true;
    }
}
