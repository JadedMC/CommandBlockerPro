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

import net.jadedmc.commandblockerpro.CommandBlockerProPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * Manages all rules set by the plugin.
 * These are stored in config.yml.
 */
public class RuleManager {
    private final CommandBlockerProPlugin plugin;
    private final Collection<Rule> rules = new HashSet<>();

    /**
     * Creates the RuleManager.
     * @param plugin Instance of the plugin.
     */
    public RuleManager(@NotNull final CommandBlockerProPlugin plugin) {
        this.plugin = plugin;

        reloadRules();
    }

    /**
     * Clears the existing rules and loads them again from config.yml.
     */
    public void reloadRules() {
        rules.clear();

        final ConfigurationSection rulesSection = plugin.getSettingsManager().getConfig().getConfigurationSection("rules");
        if(rulesSection == null) {
            return;
        }

        for(final String rule : rulesSection.getKeys(false)) {
            final ConfigurationSection ruleSection = rulesSection.getConfigurationSection(rule);
            if(ruleSection == null) {
                continue;
            }

            rules.add(new Rule(ruleSection));
        }
    }

    /**
     * Gets all currently loaded rules.
     * @return All loaded rules, stored in a collection.
     */
    public Collection<Rule> getRules() {
        return rules;
    }
}