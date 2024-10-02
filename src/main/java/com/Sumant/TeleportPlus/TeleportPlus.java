package com.Sumant.TeleportPlus;

import org.bukkit.plugin.java.JavaPlugin;

public class TeleportPlus extends JavaPlugin {

    // called when the plugin is enabled
    @Override
    public void onEnable() {
        // registering the event listener
        getServer().getPluginManager().registerEvents(new SignsTeleport(), this);

        // creating an instance of HomeCommand
        HomeCommand homeCommand = new HomeCommand(this);

        // registering the "/sethome" and "/home" commands 
        this.getCommand("sethome").setExecutor(homeCommand);
        this.getCommand("home").setExecutor(homeCommand);

        getLogger().info("MyTeleportPlugin has been enabled!");
    }

    // called when the plugin is disabled 
    @Override
    public void onDisable() {
        // logging a message to the console
        getLogger().info("MyTeleportPlugin has been disabled!");
    }
}
