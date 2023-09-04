package me.duckyprogrammer.mhad;

import org.bukkit.ChatColor;

import java.util.Objects;

public class Messages {

    private static final MHAd plugin = MHAd.getInstance();

    public static final String coloredAdMessage = plugin.getConfig().getString("coloredAdMessage");
    public static final String nonColoredAdMessage = plugin.getConfig().getString("nonColoredAdMessage");
    public static final String clickForMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("clickForMessage")));
    public static final String onlyPlayers = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("onlyPlayers")));
    public static final String checkingRank = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("checkingRankMessage")));
}
