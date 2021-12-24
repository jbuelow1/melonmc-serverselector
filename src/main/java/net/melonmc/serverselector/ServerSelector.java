package net.melonmc.serverselector;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.melonmc.serverselector.listener.LobbyJoinEvent;
import net.melonmc.serverselector.listener.ServersCommandListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public final class ServerSelector extends Plugin {

    private ServersCommandListener serversCommandListener;
    private LobbyJoinEvent lobbyJoinEvent;

    @Getter
    private Configuration config;

    @Getter
    private static ServerSelector plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Save default config
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Attempt to open config file
        try {
             config = ConfigurationProvider.getProvider(
                    YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to load config file for ServerSelector!", e);
        }

        // Register command listener
        serversCommandListener = ServersCommandListener.factory(this);

//        lobbyJoinEvent = new LobbyJoinEvent(this); //Not working yet
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
