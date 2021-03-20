package xyz.axince.check.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import xyz.axince.check.Check;

/*
 * Copyright © 2021 axince. All Rights Reserved.
 * Created: 2021 / 14:50
 */
public class CheckCommand implements CommandExecutor {
    private Check check;
    private static Location location;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            final Player player = (Player) commandSender;
            if (player.hasPermission("check.use")) {
                switch (args.length) {
                    case 0:
                        if (!Check.getInstance().getActivePlayers().contains(player)) {
                            Check.getInstance().getActivePlayers().add(player);
                            player.sendMessage(Check.getPrefix() + "Du hat den Checkmodus aktiviert!");
                            location = player.getLocation();
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.hidePlayer(player);
                                player.setGameMode(GameMode.SPECTATOR);
                            }
                            return true;
                        }
                        Check.getInstance().getActivePlayers().remove(player);
                        player.teleport(location);
                        for (Player all : Bukkit.getOnlinePlayers())
                            all.showPlayer(player);
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Check.getPrefix() + "Du hat den Checkmodus verlassen!");
                        return false;
                    case 1:
                        if (!Check.getInstance().getTimeUsed().contains(player.getName())) {
                            if (Check.getInstance().getActivePlayers().contains(player)) {
                                Player c = Bukkit.getPlayer(args[0]);
                                String cp = args[0];
                                Vector dir = c.getLocation().getDirection();
                                Vector vec = new Vector(dir.getX() + 0.9D, dir.getY() * 0.3D, dir.getZ() - -0.9D);
                                c.setVelocity(vec);
                                player.sendMessage("");
                                Check.getInstance().getTimeUsed().add(player.getName());
                                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this.check, new Runnable() {
                                    public void run() {
                                        Check.getInstance().getTimeUsed().remove(player.getName());
                                    }
                                }, 60L);
                                return true;
                            }
                            player.sendMessage(Check.getPrefix() + "Du bist noch nicht im Check! Benutze /check");
                        } else {
                            player.sendMessage(Check.getPrefix() + "§cBitte warte noch 3 Sekunden um den Befehl erneut auszuführen!");
                            return true;
                        }
                        return false;
                }
                player.sendMessage(Check.getPrefix() + "Verwende: /check <Player>");
            } else {
                player.sendMessage(Check.getPrefix() + "§cDazu hast du keine Rechte");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("NUR ALS SPIELER");
        }
        return false;
    }
}
