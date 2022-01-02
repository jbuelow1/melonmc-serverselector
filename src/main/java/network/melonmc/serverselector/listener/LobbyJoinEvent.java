package network.melonmc.serverselector.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import network.melonmc.serverselector.ServerSelector;
import network.melonmc.serverselector.select.SelectionItem;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyJoinEvent implements Listener {

    private final ServerSelector plugin;

    public LobbyJoinEvent(ServerSelector plugin) {
        this.plugin = plugin;
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onConnectedEvent(ServerConnectedEvent e) {
        for (String server : plugin.getConfig().getStringList("compassServers")) {
            if (Objects.equals(e.getServer().getInfo().getName(), server)) {
                CompassGiverTask task = new CompassGiverTask(e.getPlayer());
                task.begin();
            }
        }
    }

    private class CompassGiverTask extends TimerTask {

        private final ProxiedPlayer player;
        private int fails = 0;

        public CompassGiverTask(ProxiedPlayer player) {
            this.player = player;
        }

        public void begin() {
            new Timer().schedule(this, 500);
        }

        @Override
        public void run() {
            if (fails > 5) { return; }
            try {
                SelectionItem select = new SelectionItem(plugin, player, player.getServer());
                select.give();
            } catch (Exception ignored) {
                fails++;
                begin();
            }
        }

    }

}
