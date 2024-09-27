package com.Sumant.TeleportPlus;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeCommandTest {

    private HomeCommand homeCommand;
    private JavaPlugin plugin;  // Mocked JavaPlugin instance
    private Player mockPlayer;
    private Command mockCommand;
    private CommandSender mockSender;
    private Location mockLocation;
    private World mockWorld;
    private File mockDataFolder;  // A simple mock File object

    @BeforeEach
    public void setUp() throws Exception {
        // Mock the plugin and other Bukkit entities
        plugin = mock(JavaPlugin.class);  // Correctly mock JavaPlugin
        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);
        mockSender = mock(CommandSender.class);
        mockLocation = mock(Location.class);
        mockWorld = mock(World.class);

        // Mock a simple File object for the data folder
        mockDataFolder = mock(File.class);  // Correctly mock File
        when(mockDataFolder.exists()).thenReturn(true);  // Mocking that the folder exists

        // Make sure the plugin's getDataFolder() method returns the mocked File object
       // when(plugin.getDataFolder()).thenReturn(mockDataFolder);

        // Initialize the HomeCommand class with the mocked plugin
        homeCommand = new HomeCommand(plugin);

        // Mock player UUID and location details
        when(mockPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
        when(mockPlayer.getLocation()).thenReturn(mockLocation);
        when(mockLocation.getWorld()).thenReturn(mockWorld);
        when(mockWorld.getName()).thenReturn("world");
        when(mockLocation.getX()).thenReturn(100.0);
        when(mockLocation.getY()).thenReturn(64.0);
        when(mockLocation.getZ()).thenReturn(100.0);
    }

    @Test
    public void testSetHomeCommand() {
        // Mock the command name to be "sethome"
        when(mockCommand.getName()).thenReturn("sethome");

        // Simulate player issuing the /sethome command
        boolean result = homeCommand.onCommand(mockPlayer, mockCommand, "sethome", new String[0]);

        // Verify that the home was set successfully
        assertTrue(result);
        verify(mockPlayer).sendMessage("Home set!");
    }

    @Test
    public void testHomeCommandWithoutSettingHome() {
        // Mock the command name to be "home"
        when(mockCommand.getName()).thenReturn("home");

        // Simulate player issuing the /home command before setting a home
        boolean result = homeCommand.onCommand(mockPlayer, mockCommand, "home", new String[0]);

        // Verify that the player was informed they haven't set a home yet
        assertTrue(result);
        verify(mockPlayer).sendMessage("You haven't set a home yet!");
    }

    @Test
    public void testHomeCommandAfterSettingHome() {
        // Mock the command name to be "sethome"
        when(mockCommand.getName()).thenReturn("sethome");
        homeCommand.onCommand(mockPlayer, mockCommand, "sethome", new String[0]);

        // Mock the command name to be "home"
        when(mockCommand.getName()).thenReturn("home");

        // Simulate player issuing the /home command after setting a home
        boolean result = homeCommand.onCommand(mockPlayer, mockCommand, "home", new String[0]);

        // Verify that the player was teleported to their home
        assertTrue(result);
        verify(mockPlayer).teleport(mockLocation);
        verify(mockPlayer).sendMessage("Teleported to your home!");
    }

    @Test
    public void testNonPlayerSender() {
        // Simulate non-player (e.g., console) issuing the /home command
        when(mockCommand.getName()).thenReturn("home");

        boolean result = homeCommand.onCommand(mockSender, mockCommand, "home", new String[0]);

        // Verify that the sender was informed that only players can use the command
        assertTrue(result);
        verify(mockSender).sendMessage("Only players can use this command.");
    }
}
