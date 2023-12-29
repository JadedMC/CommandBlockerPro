# CommandBlockerPro
<center><a href="https://github.com/JadedMC/CommandBlockerPro">Source Code</a> | <a href="https://github.com/JadedMC/CommandBlockerPro/wiki">Wiki</a> | <a href="https://github.com/JadedMC/CommandBlockerPro/issues">Support</a></center><br/>

CommandBlockerPro is a feature-rich command blocking plugin for Spigot and Paper servers running Minecraft 1.8 or later. It's designed for servers trying to take their configuration to the next level, with the ability to set multiple different filters based on different conditions, with configurable block messages that support PlaceholderAPI.

>⚠️ If you're just looking to block a few commands, I'd suggest using regular [CommandBlocker](https://hangar.papermc.io/JadedMC/CommandBlocker) instead. It's easier to use and will get the job done.

## Features
* Ability to define multiple rules with different permissions.
* Block Full Commands.
* Block commands with a given string in them/
* Block commands based off regex.
* Whitelist specific commands based off the above conditions.
* Hide (without blocking) specific commands with the same conditions.
* An API to interact with the plugin through.
* MiniMessage support
* PlaceholderAPI support

## Requirements
* Java 8 or newer
* Server Software with Spigot API.
* Minecraft 1.8 or later
* PlaceholderAPI (Optional)

## Commands
| Command | Aliases | Permission Node | Description |
| ------- | ------- | --------------- | ----------- |
| **/commandblocker** | **/cb** | **commandblocker.admin** | **Main plugin command** |
| /cb reload | N/A | N/A | Reloads the configuration file |
| /cb version | N/A | N/A | View the current version of the plugin |
| /cb wiki | N/A | N/A | View a link to the plugin wiki |

## bStats
<a href="https://bstats.org/plugin/bukkit/CommandBlockerPro/20588"><img src="https://bstats.org/signatures/bukkit/CommandBlockerPro.svg"/></a>