package ua.wyverno;

import ua.wyverno.config.Config;
import ua.wyverno.config.exceptions.ConfigException;
import ua.wyverno.config.exceptions.NotCorrectValueInPropertiesException;
import ua.wyverno.config.exceptions.PropertiesNotFoundException;
import ua.wyverno.config.exceptions.PropertiesNotHasKeysException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try {
            Config config = new Config(Paths.get("./settings.properties"));

            Timer timer = new Timer();

            MySQLBackupCommand mySQLBackupCommand =
                    new MySQLBackupCommand(config.getUser(), config.getPassword(), config.getDatabase(), config.getDocker_container());

            timer.schedule(new MySQLBackupTask(mySQLBackupCommand, Paths.get(config.getDirectory_backup()), config.getFile_backup(),config.getMax_backup()),
                    TimeUnit.MINUTES.toMillis(3),TimeUnit.MINUTES.toMillis(config.getRepeat_interval()));

        } catch (NotCorrectValueInPropertiesException e) {
            System.out.println(e.getMessage());
            System.out.println("Please correct your properties");
        } catch (PropertiesNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Properties created! Please fill out the properties file");
        } catch (PropertiesNotHasKeysException e) {
            System.out.println(e.getMessage());
            System.out.println("Properties recreated! Please fill out the properties file");
        } catch (IOException | ConfigException e) {
            throw new RuntimeException(e);
        }
    }
}
