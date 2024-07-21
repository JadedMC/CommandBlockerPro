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

/**
 *  Represents the different types of rules that can be used.
 *  {@link #BLACKLIST}
 *  {@link #HIDE}
 *  {@link #WHITELIST}
 */
public enum RuleType {
    /**
     * Blacklists all commands listed.
     * Hides the commands from tab complete and blocks their use.
     */
    BLACKLIST,

    /**
     * Hides all commands listed from tab complete, without blocking their use.
     */
    HIDE,

    /**
     * Creates fake commands that do nothing but display the rule's block message.
     * Does not override a command if it already exists.
     */
    MESSAGE,

    /**
     * Blocks all commands except those in the list.
     * Hides non-whitelisted commands from tab complete and blocks their use.
     */
    WHITELIST,
}