package network.melonmc.serverselector;

import lombok.Getter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import network.melonmc.serverselector.listener.ServersCommandListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public final class ServerSelector extends Plugin {

    private ServersCommandListener serversCommandListener;

    @Getter
    private Configuration config;

    @Getter
    private static ServerSelector plugin;

    @Getter
    private ServerConnector connector;

    @Getter
    private BungeeAudiences adventure;

    @Getter
    private MiniMessage miniMessage;

    @Override
    public void onEnable() {
        plugin = this;

        // Create entrypoint for Adventure API
        this.adventure = BungeeAudiences.create(this);
        this.miniMessage = MiniMessage.get();

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

        this.connector = new ServerConnector(this);

        // Register command listener
        serversCommandListener = ServersCommandListener.factory(this);

        //new GiveCompassItemTestCommand(this);

        //new LobbyJoinEvent(this); //Not working yet
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
