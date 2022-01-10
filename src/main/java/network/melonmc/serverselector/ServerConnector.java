package network.melonmc.serverselector;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class ServerConnector {

    private final AudienceProvider adventure;
    private final MiniMessage mm;

    private final String messageConnect, messageFailure;

    public ServerConnector(ServerSelector plugin) {
        this.adventure = plugin.getAdventure();
        this.mm = plugin.getMiniMessage();
        this.messageConnect = plugin.getConfig().getString("messages.connect");
        this.messageFailure = plugin.getConfig().getString("messages.failure");
    }

    public boolean connect(ProxiedPlayer player, String server) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("serverid", mm.stripTokens(server));
        placeholders.put("servername", mm.stripTokens(ServerSelector.getPlugin().getConfig().getString("servers."+server+".name")));

        adventure.player(player.getUniqueId()).sendMessage(mm.parse(messageConnect, placeholders));
        try {
            player.connect(ProxyServer.getInstance().getServerInfo(server));
            return true;
        } catch (Exception e) {
            adventure.player(player.getUniqueId()).sendMessage(mm.parse(messageFailure, placeholders));
        }
        return false;
    }

}
