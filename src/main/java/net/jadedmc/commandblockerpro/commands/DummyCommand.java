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

import net.jadedmc.commandblockerpro.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a fake command being registered for the "MESSAGE" Rule Type.
 * Only function is to display the Rule's block message.
 */
public class DummyCommand extends BukkitCommand {
    private final String blockMessage;

    /**
     * Creates the dummy command.
     * @param name Name of the dummy command.
     * @param blockMessage Block message to be displayed when the dummy command is run.
     */
    public DummyCommand(@NotNull String name, @NotNull final String blockMessage) {
        super(name);
        this.blockMessage = blockMessage;
    }

    /**
     * Runs when the command is executed.
     * @param commandSender Sender of the command.
     * @param s Command label.
     * @param strings Arguments of the command.
     * @return true.
     */
    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        ChatUtils.chat(commandSender, blockMessage);
        return true;
    }
}