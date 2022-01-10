package org.example.plugin;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.plugin.handler.RgClaimEventHandler;

import java.io.File;


public class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getLogger().info(ChatColor.AQUA + "Enabling WGDefaultFlags");
        insertConfig();
        getServer().getPluginManager().registerEvents(new RgClaimEventHandler(this), this);
    }

    public void insertConfig()
    {
        File configFile = new File(this.getDataFolder(), "config.yml");
        String configPath = configFile.getPath();
        if(!configFile.exists())
        {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }
}
