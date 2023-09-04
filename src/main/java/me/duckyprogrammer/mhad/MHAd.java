package me.duckyprogrammer.mhad;

import org.bukkit.plugin.java.JavaPlugin;

public final class MHAd extends JavaPlugin {
    private static MHAd instance;

    public static MHAd getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        getCommand("admessage").setExecutor(new AdCommand());
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        int pluginId = 19724; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
