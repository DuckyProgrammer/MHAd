package me.duckyprogrammer.mhad;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;

public class AdCommand implements CommandExecutor, Runnable {

    private static HashMap<Player, Boolean> canDoColors = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.onlyPlayers);
            return true;
        }

        if (canDoColors(player)) {
            TextComponent suggestText = new TextComponent(ChatColor.translateAlternateColorCodes('&', Messages.clickForMessage));
            suggestText.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ad " + Messages.coloredAdMessage));
            player.spigot().sendMessage(suggestText);
        } else {
            TextComponent suggestText = new TextComponent(ChatColor.translateAlternateColorCodes('&', Messages.clickForMessage));
            suggestText.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ad " + Messages.nonColoredAdMessage));
            player.spigot().sendMessage(suggestText);
        }

        return false;
    }
    private boolean canDoColors(Player player) {
        if (canDoColors.containsKey(player)) {
            return canDoColors.get(player);
        } else {
            String uuid = player.getUniqueId().toString();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.checkingRank));
            try {
                URL url = new URL("https://api.minehut.com/cosmetics/profile/" + uuid);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                JSONObject obj = (JSONObject) JSONValue.parse(content.toString());
                String rank = (String) obj.get("rank");
                in.close();
                if (rank.equalsIgnoreCase("default")) {
                    canDoColors.put(player, false);
                    return false;
                } else {
                    canDoColors.put(player, true);
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run() {
        if (canDoColors.size() >= 1 && Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                canDoColors.remove(player);
            }
        }
    }
}
