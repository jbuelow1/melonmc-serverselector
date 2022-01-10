package network.melonmc.serverselector;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerConnector {

    public static boolean connectPlayerTo(ProxiedPlayer player, String server) {
        player.sendMessage("§1Connecting to " + server + "...");
        try {
            player.connect(ProxyServer.getInstance().getServerInfo(server));
            return true;
        } catch (Exception e) {
            player.sendMessage("§4Could not connect to "+server);
        }
        return false;
    }

}
