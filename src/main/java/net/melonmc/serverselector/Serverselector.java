package net.melonmc.serverselector;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.melonmc.serverselector.listener.LobbyJoinEvent;
import net.melonmc.serverselector.listener.ServersCommandListener;

public final class Serverselector extends Plugin {

    private ServersCommandListener serversCommandListener;
    private LobbyJoinEvent lobbyJoinEvent;

    @Override
    public void onEnable() {
        // Plugin startup logic
        serversCommandListener = new ServersCommandListener(this);
//        lobbyJoinEvent = new LobbyJoinEvent(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
