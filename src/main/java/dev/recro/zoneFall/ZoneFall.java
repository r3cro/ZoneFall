package dev.recro.zoneFall;

import dev.recro.zoneFall.commands.*;
import dev.recro.zoneFall.commands.economy.BalanceCommand;
import dev.recro.zoneFall.commands.economy.PayCommand;
import dev.recro.zoneFall.commands.economy.SellCommand;
import dev.recro.zoneFall.commands.economy.WorthCommand;
import dev.recro.zoneFall.data.DatabaseManager;
import dev.recro.zoneFall.data.PlayerDataManager;
import dev.recro.zoneFall.listeners.LoadZonePlayer;
import dev.recro.zoneFall.utils.DataFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ZoneFall extends JavaPlugin {

    private PlayerDataManager playerDataManager;
    private DatabaseManager databaseManager;

    private DataFile configFile;
    private DataFile itemWorthFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.configFile = new DataFile(this, "config");
        this.itemWorthFile = new DataFile(this, "worth");

        String host = getConfigFile().getString("sql.host");
        int port = getConfigFile().getInt("sql.port");
        String database = getConfigFile().getString("sql.database");
        String username = getConfigFile().getString("sql.username");
        String password = getConfigFile().getString("sql.password");

        databaseManager = new DatabaseManager(host, port, database, username,password);
        databaseManager.setupTable();
        playerDataManager = new PlayerDataManager(databaseManager);

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        playerDataManager.getAllData().values().forEach(databaseManager::savePlayerData);
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new LoadZonePlayer(this), this);
    }

    private void registerCommands() {
        getCommand("debug").setExecutor(new DebugCommand(this));
        getCommand("worth").setExecutor(new WorthCommand(this));
        getCommand("sell").setExecutor(new SellCommand(this));
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
    }

}
