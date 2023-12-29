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
package net.jadedmc.commandblockerpro;

import net.jadedmc.commandblockerpro.commands.CommandBlockerCMD;
import net.jadedmc.commandblockerpro.listeners.PlayerCommandPreprocessListener;
import net.jadedmc.commandblockerpro.listeners.PlayerCommandSendListener;
import net.jadedmc.commandblockerpro.rules.RuleManager;
import net.jadedmc.commandblockerpro.utils.ChatUtils;
import net.jadedmc.commandblockerpro.utils.VersionUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBlockerProPlugin extends JavaPlugin {
    private HookManager hookManager;
    private SettingsManager settingsManager;
    private RuleManager ruleManager;

    /**
     * Runs when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Load plugin settings.
        hookManager = new HookManager();
        settingsManager = new SettingsManager(this);
        ruleManager = new RuleManager(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);

        // This event only exists on 1.13+.
        if(VersionUtils.getServerVersion() >= 13) {
            getServer().getPluginManager().registerEvents(new PlayerCommandSendListener(this), this);
        }

        // Register command
        getCommand("commandblocker").setExecutor(new CommandBlockerCMD(this));

        // Enables bStats statistics tracking.
        new Metrics(this, 20588);

        // Enables ChatUtils.
        ChatUtils.enable(this);

        // Sets up static API methods.
        CommandBlockerPro.setPlugin(this);
    }

    /**
     * Runs when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        // Disables ChatUtils. Required to prevent memory leaks with the Adventure Library.
        ChatUtils.disable();
    }

    /**
     * Get the Hook Manager, which returns an object that keeps track of hooks into other plugins.
     * @return HookManager.
     */
    public HookManager hookManager() {
        return hookManager;
    }

    /**
     * Get the rule manager of the plugin.
     * @return Rule Manager.
     */
    public RuleManager ruleManager() {
        return ruleManager;
    }

    /**
     * Get the plugin's settings manager, which manages config files.
     * @return Settings Manager.
     */
    public SettingsManager settingsManager() {
        return settingsManager;
    }
}