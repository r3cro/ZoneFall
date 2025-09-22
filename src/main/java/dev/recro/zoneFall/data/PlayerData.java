package dev.recro.zoneFall.data;

import dev.recro.zoneFall.ZoneFall;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class PlayerData {

    private ZoneFall plugin;

    private final UUID uuid;
    private int kills;
    private int mobKills;
    private int deaths;
    private double balance;
    private long firstJoin;
    private long lastJoin;
    private double sellMultiplier;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.balance = 0.0;
        this.sellMultiplier = 1.0;
        this.lastJoin = System.currentTimeMillis();
    }

    public void addKill() { this.kills++; }
    public void addMobKill() { this.mobKills++; }
    public void addDeath() { this.deaths++; }
    public void addBalance(double amount, double multiplier) { this.balance = balance + (amount*multiplier); }
    public void removeBalance(double amount) { this.balance = balance-amount; }

    public long setFirstJoin(long l) {
        return this.firstJoin = l;
    }
    public long setLastJoin(long l) {
        return this.lastJoin = l;
    }
}
