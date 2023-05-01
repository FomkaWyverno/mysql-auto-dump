package ua.wyverno.config;

import ua.wyverno.config.exceptions.ConfigException;
import ua.wyverno.config.exceptions.NotCorrectValueInPropertiesException;
import ua.wyverno.config.exceptions.PropertiesNotFoundException;
import ua.wyverno.config.exceptions.PropertiesNotHasKeysException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private final Properties properties;

    private final String user;
    private final String password;
    private final String database;
    private final String docker_container;
    private final String directory_backup;
    private final String file_backup;
    private final int max_backup;
    private final int repeat_interval;

    public Config(Path path) throws IOException, ConfigException {
        System.out.println("Loading properties...");
        this.properties = new Properties();
        if (Files.exists(path)) {
            System.out.println("Property is exists!");

            BufferedReader propertiesReader = Files.newBufferedReader(path);
            this.properties.load(propertiesReader);
            propertiesReader.close();

            if (this.isHasAllProperties()) {
                System.out.println("Properties successfully load");
                this.user = this.properties.getProperty("user");
                this.password = this.properties.getProperty("password");
                this.database = this.properties.getProperty("database");
                this.docker_container = this.properties.getProperty("docker-container");
                this.directory_backup = this.properties.getProperty("directory-backup");
                this.file_backup = this.properties.getProperty("file-backup");
                try {
                    this.max_backup = Integer.parseInt(this.properties.getProperty("max-backup"));
                    this.repeat_interval = Integer.parseInt(this.properties.getProperty("repeat-interval"));
                } catch (NumberFormatException e) {
                    throw new NotCorrectValueInPropertiesException("Not correct value in field max_backup or repeat_interval");
                }

            } else {
                System.out.println("Not has all keys in properties!");
                this.initEmptyValue(path);
                throw new PropertiesNotHasKeysException("Properties keys is not all!");
            }
        } else {
            System.out.println("Property is not exists!");
            this.initEmptyValue(path);
            throw new PropertiesNotFoundException("Properties is not exists!");
        }
    }

    private void initEmptyValue(Path path) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(path);

        writer.write("#By Fomka_Wyverno\n" +
                "\n" +
                "#MySQL-user\n" +
                "user=\n" +
                "#Password from user MySQL-user\n" +
                "password=\n" +
                "#Database when need backup\n"+
                "database=\n"+
                "\n" +
                "#Docker-container MySQL\n" +
                "docker-container=\n" +
                "\n" +
                "#Direcory for backups\n" +
                "directory-backup=\n" +
                "#Name file backup without .sql\n" +
                "file-backup=\n" +
                "#Max backups\n" +
                "max-backup=\n"+
                "#Repeat interval to create backup (MINUTES)\n"+
                "repeat-interval=\n");

        writer.close();
    }

    private boolean isHasAllProperties() {
        return
            this.properties.containsKey("user") &&
            this.properties.containsKey("password") &&
            this.properties.containsKey("database") &&
            this.properties.containsKey("docker-container") &&
            this.properties.containsKey("directory-backup") &&
            this.properties.containsKey("file-backup") &&
            this.properties.containsKey("max-backup") &&
            this.properties.containsKey("repeat-interval");
    }

    public Properties getProperties() {
        return properties;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDocker_container() {
        return docker_container;
    }

    public String getDirectory_backup() {
        return directory_backup;
    }

    public String getFile_backup() {
        return file_backup;
    }

    public int getMax_backup() {
        return max_backup;
    }

    public int getRepeat_interval() {
        return repeat_interval;
    }

    public String getDatabase() {
        return database;
    }
}
