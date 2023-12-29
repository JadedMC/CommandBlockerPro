/*
 * This file is part of CommandBlockerPro, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.commandblockerpro.rules;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a pattern to follow when determining what commands should be blocked or hidden from a player.
 * Stores:
 *   - Rule Type (Blacklist, Hide, or Whitelist)
 *   - A bypass permission node (which allows players to ignore the rule if they have)
 *   - A list of commands the rule should act on.
 */
@SuppressWarnings("unused")
public class Rule {
    private final RuleType type;
    private final String bypassPermission;
    private final List<String> commands = new ArrayList<>();
    private final List<String> contains = new ArrayList<>();

    /**
     * Creates the rule using a configuration section.
     * @param config Configuration section storing the rule settings.
     */
    public Rule(final ConfigurationSection config) {
        type = RuleType.valueOf(config.getString("type"));

        if(config.isSet("bypassPermission")) {
            bypassPermission = config.getString("bypassPermission");
        }
        else {
            bypassPermission = "commandblocker.admin";
        }

        // Loop for applicable commands.
        if(config.isSet("commands")) {
            for(String command : config.getStringList("commands")) {
                commands.add(command.toLowerCase());
            }
        }

        // Loop for applicable contains strings.
        if(config.isSet("contains")) {
            for(String containedString : config.getStringList("contains")) {
                contains.add(containedString.toLowerCase());
            }
        }
    }

    /**
     * Get the permission node required to bypass the rule.
     * @return Rule's bypass permission node.
     */
    public String bypassPermission() {
        return bypassPermission;
    }

    /**
     * Get all commands stored by the rule.
     * @return The rule's stored commands.
     */
    public Collection<String> commands() {
        return commands;
    }

    /**
     * Determine if a rule blocks a given player from using a given command.
     * @param player Player trying to use the command.
     * @param command Command the player is trying to use.
     * @return Whether they can use the command or not.
     */
    public boolean shouldBlock(Player player, String command) {
        // Don't block if no permission was set.
        if(bypassPermission.isEmpty()) {
            return false;
        }

        // Only block if the player does not have the bypass permission.
        if(player.hasPermission(bypassPermission)) {
           return false;
        }

        // The hide rule does not block commands.
        if(type == RuleType.HIDE) {
            return false;
        }

        // Check the type of the rule to determine if the command should be blocked.
        switch (type) {
            case BLACKLIST:

                // Check for contained strings.
                for(String containsString : contains) {
                    if(command.toLowerCase().contains(containsString)) {
                        return true;
                    }
                }

                return commands.contains(command.toLowerCase());

            case WHITELIST:
                // Check for contained strings.
                for(String containsString : contains) {
                    if(!command.toLowerCase().contains(containsString)) {
                        return true;
                    }
                }

                return !commands.contains(command.toLowerCase());
        }

        // If something goes wrong, don't block the command.
        return false;
    }

    /**
     * Determine if a rule blocks a given player from hiding a given command from tab complete.
     * @param player Player trying to tab complete the command.
     * @param command Command the player is trying to tab complete.
     * @return Whether they can tab complete the command or not.
     */
    public boolean shouldHide(Player player, String command) {
        // Don't hide if no permission was set.
        if(bypassPermission.isEmpty()) {
            return false;
        }

        // Only hide if the player does not have the bypass permission.
        if(player.hasPermission(bypassPermission)) {
            return false;
        }

        // Check the type of the rule to determine if the command should be hidden.
        switch(type) {
            case BLACKLIST:
            case HIDE:
                // Check for contained strings.
                for(String containsString : contains) {
                    if(command.toLowerCase().contains(containsString)) {
                        return true;
                    }
                }

                return commands.contains(command.toLowerCase());

            case WHITELIST:
                // Check for contained strings.
                for(String containsString : contains) {
                    if(!command.toLowerCase().contains(containsString)) {
                        return true;
                    }
                }

                return !commands.contains(command.toLowerCase());
        }

        // If something goes wrong, don't hide the command.
        return false;
    }

    /**
     * Retrieves the type of the rule.
     * @return Rule Type.
     */
    public RuleType type() {
        return type;
    }
}