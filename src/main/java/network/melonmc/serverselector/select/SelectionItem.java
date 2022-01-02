package network.melonmc.serverselector.select;

import dev.simplix.protocolize.api.PlayerInteract;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.PlayerInventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import network.melonmc.serverselector.ServerSelector;

public class SelectionItem {

    private final ServerSelector plugin;
    private final ProxiedPlayer player;
    private final ProtocolizePlayer protoPlayer;
    private final Server server;

    public SelectionItem(ServerSelector plugin, ProxiedPlayer player, Server hubserver) {
        this.plugin = plugin;
        this.player = player;
        this.server = hubserver;
        this.protoPlayer = Protocolize.playerProvider().player((player).getUniqueId());
    }

    public void give() {
        PlayerInventory inv = protoPlayer.proxyInventory();
        inv.item(36, new ItemStack(ItemType.COMPASS).displayName("Server Selector"));
        inv.update();

        protoPlayer.onInteract(this::onUse);
    }

    public void onUse(PlayerInteract interact) {
        if (!server.equals(player.getServer())) { return; }
        if (interact.currentItem().itemType() == ItemType.COMPASS) {
            new SelectorUI(player).show();
        }
    }
}
