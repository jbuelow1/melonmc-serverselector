package network.melonmc.serverselector.select;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.PlayerInventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import network.melonmc.serverselector.ServerSelector;

public class SelectionItem {

    private final ServerSelector plugin;
    private final ProxiedPlayer player;
    private final ProtocolizePlayer protoPlayer;

    public SelectionItem(ServerSelector plugin, ProxiedPlayer player) {
        this.plugin = plugin;
        this.player = player;
        this.protoPlayer = Protocolize.playerProvider().player((player).getUniqueId());
    }

    public void give() {
        PlayerInventory inv = protoPlayer.proxyInventory();
        inv.item(36, new ItemStack(ItemType.COMPASS).displayName("Server Selector"));
        inv.update();

        protoPlayer.onInteract(interact -> {
            if (interact.currentItem().itemType() == ItemType.COMPASS) {
                new SelectorUI(player).show();
            }
        });
    }
}
