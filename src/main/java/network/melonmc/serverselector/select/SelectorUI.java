package network.melonmc.serverselector.select;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import network.melonmc.serverselector.ServerConnector;
import network.melonmc.serverselector.ServerSelector;

import java.util.HashMap;
import java.util.Map;

public class SelectorUI {

    private final ProxiedPlayer player;
    private final Configuration config;
    Map<Integer, String> slots = new HashMap<>();

    public SelectorUI(ProxiedPlayer proxyplayer) {
        this.player = proxyplayer;
        this.config = ServerSelector.getPlugin().getConfig();
    }

    public void show() {
        int rows = config.getInt("rows");
        String title = config.getString("title");

        Inventory inventory = new Inventory(InventoryType.chestInventoryWithRows(rows));
        inventory.title(title);

        // If config requests the blank spaces be filled
        if (config.getBoolean("padding.fill")) {
            for (int i = 0; i < rows*9; i++) {
                inventory.item(i, new ItemStack(ItemType.valueOf(config.getString("padding.item"))).displayName(""));
            }
        }

        for (String serverKey : config.getSection("servers").getKeys()) {
            Configuration serverConfig = config.getSection("servers."+serverKey);
            int slot = serverConfig.getInt("slot");
            ItemType item = ItemType.valueOf(serverConfig.getString("item"));
            String name = serverConfig.getString("name");

            // Put item in inventory
            inventory.item(slot, new ItemStack(item).displayName(name));
            slots.put(slot, serverKey);
        }

        inventory.onClick(this::onClick);

        ProtocolizePlayer protoPlayer = Protocolize.playerProvider().player(player.getUniqueId());
        protoPlayer.openInventory(inventory);
    }

    private void onClick(InventoryClick click) {
        if (!slots.containsKey(click.slot())) {
            return;
        }

        String server = slots.get(click.slot());
        ProtocolizePlayer protoplayer = click.player();

        ServerConnector.connectPlayerTo(player, server);

        protoplayer.closeInventory();
    }
}
