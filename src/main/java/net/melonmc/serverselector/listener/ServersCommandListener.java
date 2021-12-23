package net.melonmc.serverselector.listener;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.melonmc.serverselector.ServerSelector;
import net.melonmc.serverselector.select.SelectorUI;

public class ServersCommandListener extends Command {

    public ServersCommandListener(ServerSelector plugin) {

        super("Servers", "melonmc.serverselector", "srv");
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            new SelectorUI((ProxiedPlayer) sender).show();
        }
    }

}
