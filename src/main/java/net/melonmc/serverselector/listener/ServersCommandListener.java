package net.melonmc.serverselector.listener;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.melonmc.serverselector.Serverselector;
import net.melonmc.serverselector.select.SelectorUI;

public class ServersCommandListener extends Command {

    public ServersCommandListener(Serverselector plugin) {
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
