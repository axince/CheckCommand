package xyz.axince.check;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.axince.check.commands.CheckCommand;

import java.util.ArrayList;

/*
 * Copyright © 2021 axince. All Rights Reserved.
 * Created: 2021 / 14:48
 */
public class Check extends JavaPlugin {

    private static Check instance;
    private static String prefix;

    private ArrayList<Player> activePlayers;

    private ArrayList<String> used = new ArrayList<>();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aCHECK WURDE AKTIVIERT!");

        instance = this;
        prefix = "§aCeck §8» §7";
        this.activePlayers = new ArrayList<>();
        loadCommands();
    }

    private void loadCommands() {
        getCommand("check").setExecutor(new CheckCommand());
    }

    public static Check getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public ArrayList<Player> getActivePlayers() {
        return this.activePlayers;
    }

    public ArrayList<String> getTimeUsed() {
        return this.used;
    }
}
