package network.melonmc.serverselector.listener;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import network.melonmc.serverselector.ServerSelector;
import network.melonmc.serverselector.select.SelectionItem;

public class GiveCompassItemTestCommand extends Command {

    private final ServerSelector plugin;

    public GiveCompassItemTestCommand(ServerSelector plugin) {
        super("giveselectorcompass");
        this.plugin = plugin;

        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("ok");
        SelectionItem select = new SelectionItem(plugin, (ProxiedPlayer) sender);
        select.give();
    }
}
