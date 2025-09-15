package dev.recro.zoneFall.listeners;

import dev.recro.zoneFall.ZoneFall;
import dev.recro.zoneFall.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoadZonePlayer implements Listener {

    private ZoneFall plugin;

    public LoadZonePlayer(ZoneFall plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().getPlayerData(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        plugin.getPlayerDataManager().getPlayerData(event.getPlayer()).setBalance(69);
        event.getPlayer().sendMessage("Balance: " + plugin.getPlayerDataManager().getPlayerData(event.getPlayer()).getBalance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().getPlayerData(player);

        plugin.getPlayerDataManager().removePlayerData(player);
    }

}
