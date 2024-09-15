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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && !(event.getPlayer().isSneaking())) {
            Block block = event.getClickedBlock();
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();


                SignSide side = sign.getSide(Side.FRONT); // default chosen side to be front

                if (side.getLine(0).contains("TP:")) {
                    Player player = event.getPlayer();

                    try {
                        double x = Double.parseDouble(side.getLine(1));
                        double y = Double.parseDouble(side.getLine(2));
                        double z = Double.parseDouble(side.getLine(3));

                        Location tpLocation = new Location(player.getWorld(), x, y, z);

                        player.teleport(tpLocation);

                        String[] parts = side.getLine(0).split(": ");
                        String destination = parts[1];

                        player.sendMessage("Teleported to " + destination);


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









