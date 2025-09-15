package dev.recro.zoneFall.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final DatabaseManager databaseManager;

    public PlayerDataManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), databaseManager::loadPlayerData);
    }

    public void removePlayerData(Player player) {
        PlayerData data = playerDataMap.remove(player.getUniqueId());
        if (data != null) databaseManager.savePlayerData(data);
    }

    public boolean hasPlayerData(Player player) {
        return playerDataMap.containsKey(player.getUniqueId());
    }

    public Map<UUID, PlayerData> getAllData() {
        return playerDataMap;
    }

}
