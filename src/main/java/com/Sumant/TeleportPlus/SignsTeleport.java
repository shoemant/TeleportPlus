package com.Sumant.TeleportPlus;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.event.block.Action;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SignsTeleport implements Listener {

    // event handler to handle when a player interacts with a block
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // check if the player right-clicked a block and is not sneaking
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && !(event.getPlayer().isSneaking())) {
            Block block = event.getClickedBlock();
            
            // check if the block clicked is a sign
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();

                // get the front side of the sign
                SignSide side = sign.getSide(Side.FRONT);

                // check if the first line of the sign contain prefix "TP:"
                if (side.getLine(0).contains("TP:")) {
                    Player player = event.getPlayer();

                    try {
                        // parse coordinates from the 2nd, 3rd, and 4th lines of sign
                        double x = Double.parseDouble(side.getLine(1));
                        double y = Double.parseDouble(side.getLine(2));
                        double z = Double.parseDouble(side.getLine(3));

                        // create a new location object with the parsed coordinates and the player's current world
                        Location tpLocation = new Location(player.getWorld(), x, y, z);

                        // teleport player to the specified location
                        player.teleport(tpLocation);

                        // get the destination name from the first line of the sign 
                        String[] parts = side.getLine(0).split(": ");
                        String destination = parts[1];

                        player.sendMessage("Teleported to " + destination);

                        // change the colour of sign text to yellow and make it glow
                        side.setColor(DyeColor.YELLOW);
                        side.setGlowingText(true);
                        sign.update();

                    } catch (NumberFormatException e) {
                        player.sendMessage("Invalid coordinates on the sign!");
                    }
                }
            }
        }
    }

}
