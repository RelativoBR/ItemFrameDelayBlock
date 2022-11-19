package com.github.relativobr.configuration;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public class Config {

    private final File file;
    protected FileConfiguration fileConfig;

    public Config(@Nonnull Plugin plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        this.file = new File("plugins/" + plugin.getName().replace(" ", "_"), "config.yml");
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfig.options().copyDefaults(true);
    }

    public Config(@Nonnull Plugin plugin, @Nonnull String name) {
        this.file = new File("plugins/" + plugin.getName().replace(" ", "_"), name);
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfig.options().copyDefaults(true);
    }

    @Nonnull
    public FileConfiguration getConfiguration() {
        return this.fileConfig;
    }

    public int getInt(@Nonnull String path) {
        return this.fileConfig.getInt(path);
    }

    public boolean getBoolean(@Nonnull String path) {
        return this.fileConfig.getBoolean(path);
    }

    @Nullable
    public String getString(@Nonnull String path) {
        return this.fileConfig.getString(path);
    }

    @Nonnull
    public List<String> getStringList(@Nonnull String path) {
        return this.fileConfig.getStringList(path);
    }

    @Nonnull
    public List<Integer> getIntList(@Nonnull String path) {
        return this.fileConfig.getIntegerList(path);
    }

    public void reload() {
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
    }

}
