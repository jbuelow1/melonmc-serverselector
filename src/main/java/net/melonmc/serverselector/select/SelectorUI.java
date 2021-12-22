package net.melonmc.serverselector.select;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SelectorUI {

    private final ProxiedPlayer player;

    public SelectorUI(ProxiedPlayer proxyplayer) {
        this.player = proxyplayer;
    }

    public void show() {
        Inventory inventory = new Inventory(InventoryType.GENERIC_9X1);
        inventory.title("§9MelonMC Server Selector");


        inventory.item(0, player.hasPermission("bungeecord.server.hub") ?
                new ItemStack(ItemType.SPAWNER).displayName("§5Hub") :
                new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(1, new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(2, player.hasPermission("bungeecord.server.survival") ?
                new ItemStack(ItemType.CRAFTING_TABLE).displayName("§5Survival") :
                new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(3, new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(4, player.hasPermission("bungeecord.server.creative") ?
                new ItemStack(ItemType.STICKY_PISTON).displayName("§5Creative") :
                new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(5, new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(6, player.hasPermission("bungeecord.server.modpack") ?
                new ItemStack(ItemType.REDSTONE_ORE).displayName("§5Modpack") :
                new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(7, new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));
        inventory.item(8, player.hasPermission("bungeecord.server.ftb") ?
                new ItemStack(ItemType.IRON_ORE).displayName("§5FTB Revelations") :
                new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(""));

        ProtocolizePlayer protoPlayer = Protocolize.playerProvider().player(player.getUniqueId());

        inventory.onClick(click -> {
            String server = null;
            switch (click.slot()) {
                case 0:
                    server = player.hasPermission("bungeecord.server.hub") ? "Hub" : null;
                    break;

                case 2:
                    server = player.hasPermission("bungeecord.server.survival") ? "Survival" : null;
                    break;

                case 4:
                    server = player.hasPermission("bungeecord.server.creative") ? "Creative" : null;
                    break;

                case 6:
                    server = player.hasPermission("bungeecord.server.modpack") ? "Modpack" : null;
                    break;

                case 8:
                    server = player.hasPermission("bungeecord.server.ftb") ? "FTB" : null;
                    break;

                default:
                    return;
            }

            player.sendMessage("§1Connecting to " + server + "...");
            try {
                player.connect(ProxyServer.getInstance().getServerInfo(server));
            } catch (Exception e) {
                player.sendMessage("§4Could not connect to "+server);
            }

            protoPlayer.closeInventory();

        });

        protoPlayer.openInventory(inventory);
    }
}
