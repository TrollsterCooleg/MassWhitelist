package me.cooleg.masswhitelist.commands;

import me.cooleg.easycommands.Command;
import me.cooleg.easycommands.commandmeta.SubCommand;
import me.cooleg.easycommands.commandmeta.TabCompleter;
import me.cooleg.masswhitelist.MassWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.List;

public class MassWhitelistCommand implements Command {
    private final MassWhitelist main;
    private FileConfiguration config;
    public MassWhitelistCommand(MassWhitelist main) {
        this.main = main;
        this.config = main.getConfig();
    }

    @Override
    public boolean rootCommand(CommandSender commandSender, String s) {
        commandSender.sendMessage(ChatColor.RED + "Invalid command. Use reload or whitelist");
        return true;
    }

    @Override
    public boolean noMatch(CommandSender commandSender, String s, String[] strings) {
        return rootCommand(commandSender, s);
    }

    @SubCommand("whitelist")
    private boolean whitelistSubcommand(CommandSender commandSender, String alias, String[] strings) {
        List<String> players = config.getStringList(strings[1]);
        for (String playerName : players) {
            Bukkit.getOfflinePlayer(playerName).setWhitelisted(true);
        }
        commandSender.sendMessage(ChatColor.GREEN + "Whitelisted everyone in that group, if it exists.");
        return true;
    }

    @SubCommand("remove")
    private boolean removeSubcommand(CommandSender commandSender, String alias, String[] strings) {
        List<String> players = config.getStringList(strings[1]);
        for (String playerName : players) {
            Bukkit.getOfflinePlayer(playerName).setWhitelisted(false);
        }
        commandSender.sendMessage(ChatColor.GREEN + "Removed everyone in that group, if it exists.");
        return true;
    }

    @TabCompleter("whitelist")
    @TabCompleter("remove")
    private List<String> whitelistGroupCompleter(CommandSender commandSender, String s, String[] strings) {
        return config.getKeys(false).stream().toList();
    }

    @SubCommand("reload")
    private boolean reloadConfig(CommandSender commandSender, String alias, String[] strings) {
        main.reloadConfig();
        config = main.getConfig();
        commandSender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        return true;
    }


    @Nonnull
    @Override
    public String name() {
        return "masswhitelist";
    }

    @Override
    public List<String> aliases() {
        return List.of("masswl", "wl");
    }

    @Override
    public String permission() {
        return "masswhitelist.whitelist";
    }
}
