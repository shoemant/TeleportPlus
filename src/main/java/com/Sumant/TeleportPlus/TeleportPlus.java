package com.Sumant.TeleportPlus;
import org.bukkit.plugin.java.JavaPlugin;


public class TeleportPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SignsTeleport(), this);


        HomeCommand homeCommand = new HomeCommand(this);


        this.getCommand("sethome").setExecutor(homeCommand);
        this.getCommand("home").setExecutor(homeCommand);

        getLogger().info("MyTeleportPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MyTeleportPlugin has been disabled!");
    }

}
