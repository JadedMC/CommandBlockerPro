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
package net.jadedmc.commandblockerpro.utils;

import net.jadedmc.commandblockerpro.CommandBlockerProPlugin;
import net.jadedmc.commandblockerpro.commands.DummyCommand;
import net.jadedmc.commandblockerpro.rules.Rule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A collection of methods useful for Dynamically registering commands.
 * This is specifically used for the "MESSAGE" Rule Type.
 */
public class CommandUtils {
    private static CommandBlockerProPlugin plugin = null;
    private static Field bukkitCommandMap = null;
    private static CommandMap commandMap = null;

    /**
     * Registers the utility.
     * @param pl Instance of the plugin.
     */
    public CommandUtils(final CommandBlockerProPlugin pl) {
        plugin = pl;

        // Allow editing bukkit's command map dynamically using Reflection.
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        }
        catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Registers dummy commands for a given Rule.
     * Dummy commands are commands that are registered dynamically, and only display the rule's blocked message.
     * Used for the "MESSAGE" Rule Type.
     * @param rule Rule to register dummy commands for.
     */
    public static void registerDummyCommands(@NotNull final Rule rule) {
        // Define the block message to be used.
        final String blockMessage;
        if(rule.hasBlockMessage()) {
            blockMessage = rule.getBlockMessage();
        }
        else {
            if(plugin.settingsManager().getConfig().isSet("blockMessage")) {
                blockMessage = plugin.settingsManager().getConfig().getString("blockMessage");
            }
            else {
                blockMessage = "";
            }
        }

        // Register the dummy commands set in the rule.
        for(final String command : rule.getCommands()) {
            final String finalCommand = command.replaceFirst("/", "");
            commandMap.register(finalCommand, new DummyCommand(finalCommand, blockMessage));
        }

        syncCommands();
    }

    /**
     * Update the command map for all online players, allowing players to see commands added in the "SHOW" Rule Type.
     */
    public static void syncCommands() {
        try {
            final Method syncCommandsMethod = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
            syncCommandsMethod.invoke(plugin.getServer());
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }
}