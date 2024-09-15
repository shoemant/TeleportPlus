package com.Sumant.TeleportPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {
    private HashMap<UUID, Location> playerHomes = new HashMap<>();
    private final File homesFile;
    private FileConfiguration homesConfig;

    public HomeCommand(JavaPlugin plugin) {
        homesFile = new File(plugin.getDataFolder(), "homes.yml");

        if (!plugin.getDataFolder().exists()) {
            boolean created = plugin.getDataFolder().mkdirs();
            if (!created) {
                plugin.getLogger().severe("Failed to create plugin data directory: " + plugin.getDataFolder().getAbsolutePath());
                return;
            }
        }

        if (!homesFile.exists()) {
            try {
                boolean created = homesFile.createNewFile();
                if (!created) {
                    plugin.getLogger().severe("Failed to create homes.yml file: " + homesFile.getAbsolutePath());
                    return;
                }
            } catch (IOException e) {
                plugin.getLogger().severe("An error occurred while creating homes.yml: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        homesConfig = YamlConfiguration.loadConfiguration(homesFile);

        if (!homesConfig.contains("homes")) {
            homesConfig.createSection("homes");
            saveConfig(); // Save the initial configuration
        }

        loadHomes();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (command.getName().equalsIgnoreCase("sethome")) {
            Location location = player.getLocation();
            playerHomes.put(playerId, location);
            saveHome(playerId, location);
            player.sendMessage("Home set!");

        } else if (command.getName().equalsIgnoreCase("home")) {
            if (playerHomes.containsKey(playerId)) {
                Location homeLocation = playerHomes.get(playerId);
                player.teleport(homeLocation);
                player.sendMessage("Teleported to your home!");
            } else {
                player.sendMessage("You haven't set a home yet!");
            }
        }

        return true;
    }

    private void saveHome(UUID playerId, Location location) {
        String path = "homes." + playerId.toString();
        homesConfig.set(path + ".world", location.getWorld().getName());
        homesConfig.set(path + ".x", location.getX());
        homesConfig.set(path + ".y", location.getY());
        homesConfig.set(path + ".z", location.getZ());
        saveConfig();
    }

    private void loadHomes() {
        if (homesConfig != null && homesConfig.contains("homes")) {
            for (String key : homesConfig.getConfigurationSection("homes").getKeys(false)) {
                UUID playerId = UUID.fromString(key);
                String worldName = homesConfig.getString("homes." + key + ".world");
                double x = homesConfig.getDouble("homes." + key + ".x");
                double y = homesConfig.getDouble("homes." + key + ".y");
                double z = homesConfig.getDouble("homes." + key + ".z");
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                playerHomes.put(playerId, location);
            }
        }
    }

    private void saveConfig() {
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
