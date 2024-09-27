# TeleportPlus

TeleportPlus is a Minecraft plugin that enhances the teleportation experience. It allows players to set and teleport to their personal homes with simple commands and use signs to teleport to predefined coordinates.

## Features
**Set and Teleport Home:**

- Players can use `/sethome` to save their current location.
- Players can use `/home` to teleport back to their saved home location.

**Teleport via Signs:**

- Players can right-click a sign to teleport to the specified coordinates written on it.
- The first line of the sign should start with TP: followed by the destination name.
- The second, third, and fourth lines should contain the X, Y, and Z coordinates respectively.

**Sign Interaction:**

- The plugin adds visual feedback when a player successfully teleports using a sign by changing the text color and making it glow.

## Commands
- `/sethome:` Sets the player's current location as their home.
- `/home:` Teleports the player to their saved home location.

## Permissions
There are no specific permissions needed for this plugin. All players have access to the commands and features by default.

## Installation
- Download the `TeleportPlus.jar` file.
- Place the `TeleportPlus.jar` file in your server's `plugins` folder.
- Start or restart your Minecraft server.
- The plugin will automatically generate a `homes.yml` file to store players' home locations.

## How to Use
### Setting and Teleporting Home:
- **Set Home:** Stand at your desired location and use the `/sethome` command. You will receive a message confirming your home has been set.
- **Teleport Home**: Use `/home` to teleport back to the location you saved with `/sethome`. If you have not set a home, you will receive a message indicating this.

### Teleporting with Signs:
Create a sign and write the following:
- Line 1: `TP: <Destination Name>` (Example: `TP: Village`)
- Line 2:` <X Coordinate>` (Example: `100`)
- Line 3: `<Y Coordinate> `(Example: `65`)
- Line 4: `<Z Coordinate>` (Example: `200`)
When a player right-clicks the sign, they will be teleported to the specified location.

**Note:** If the coordinates are invalid (non-numeric values), the player will receive an error message.

## Configuration
The plugin uses a `homes.yml` file to store each player's home coordinates. This file is automatically created and managed by the plugin. You do not need to manually edit this file unless necessary.
