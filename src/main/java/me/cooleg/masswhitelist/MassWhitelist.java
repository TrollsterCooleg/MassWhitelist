package me.cooleg.masswhitelist;

import me.cooleg.easycommands.CommandRegistry;
import me.cooleg.masswhitelist.commands.MassWhitelistCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MassWhitelist extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new CommandRegistry().registerCommand(new MassWhitelistCommand(this));
    }

}
