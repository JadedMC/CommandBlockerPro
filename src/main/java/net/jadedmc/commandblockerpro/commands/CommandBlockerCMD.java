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
package net.jadedmc.commandblockerpro.commands;

import net.jadedmc.commandblockerpro.CommandBlockerProPlugin;
import net.jadedmc.commandblockerpro.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class runs the /commandblocker command, which is the main admin command for the plugin.
 * aliases:
 * - commandblockerpro
 * - cb
 * - cbp
 */
public class CommandBlockerCMD implements CommandExecutor, TabCompleter {
    private final CommandBlockerProPlugin plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public CommandBlockerCMD(final CommandBlockerProPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the command is executed.
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return If the command was successful.
     */
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {

        // Makes sure the command has an argument.
        if(args.length == 0) {
            args = new String[]{"help"};
        }

        // Get the sub command used.
        String subCommand = args[0].toLowerCase();

        switch (subCommand) {

            // Reloads all plugin configuration files.
            case "reload":
                plugin.settingsManager().reload();
                plugin.ruleManager().reloadRules();
                ChatUtils.chat(sender, "<green><bold>CommandBlockerPro</bold> <dark_gray>» <green>Configuration files reloaded successfully! You may need to relog for some changes to take effect.");
                return true;

            // Displays the plugin version.
            case "version":
                ChatUtils.chat(sender, "<green><bold>CommandBlockerPro</bold> <dark_gray>» <green>Current version: <white>" + plugin.getDescription().getVersion());
                return true;

            case "wiki":
                ChatUtils.chat(sender, "<green><bold>CommandBlockerPro</bold> <dark_gray>» <green>View the wiki at <white><click:open_url:'https://github.com/JadedMC/CommandBlockerPro/wiki'>https://github.com/JadedMC/CommandBlockerPro/wiki</click><green>.");
                return true;

            // Displays the help menu.
            default:
                ChatUtils.chat(sender, "<green><bold>CommandBlockerPro Commands");
                ChatUtils.chat(sender, "<green><click:suggest_command:\"/cb reload\">/cb reload</click> <dark_gray>» <white>Reloads all configuration files.");
                ChatUtils.chat(sender, "<green><click:suggest_command:\"/cb version\">/cb version</click> <dark_gray>» <white>Displays the plugin version.");
                ChatUtils.chat(sender, "<green><click:suggest_command:\"/cb wiki\">/cb wiki</click> <dark_gray>» <white>Displays a link to the plugin's wiki.");
                return true;
        }
    }

    /**
     * Processes command tab completion.
     * @param sender Command sender.
     * @param cmd Command.
     * @param label Command label.
     * @param args Arguments of the command.
     * @return Tab completion.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        // Return an empty list if the player does not have permission.
        if(!sender.hasPermission("commandblocker.admin")) {
            return Collections.emptyList();
        }

        // Lists all sub commands if the player hasn't picked one yet.
        if(args.length < 2) {
            return Arrays.asList("help", "reload", "version", "wiki");
        }

        // Otherwise, send an empty list.
        return Collections.emptyList();
    }
}