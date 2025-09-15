package dev.recro.zoneFall.data;

import dev.recro.zoneFall.ZoneFall;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PlayerData {

    private ZoneFall plugin;

    private final UUID uuid;
    private int kills;
    private int deaths;
    private double balance;
    private long firstJoin;
    private long lastJoin;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.balance = 0.0;
        this.lastJoin = System.currentTimeMillis();
    }

    public void addKill() { this.kills++; }
    public void addDeath() { this.deaths++; }

    public long setFirstJoin(long l) {
        return this.firstJoin = l;
    }
    public long setLastJoin(long l) {
        return this.lastJoin = l;
    }
}
