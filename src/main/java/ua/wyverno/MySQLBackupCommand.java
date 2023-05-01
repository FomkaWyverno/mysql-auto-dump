package ua.wyverno;

import java.util.ArrayList;
import java.util.List;

public class MySQLBackupCommand {

    private List<String> command;
    public MySQLBackupCommand(String user, String password, String database, String docker_container) {
        this.command = new ArrayList<>();
        this.command.add("docker");
        this.command.add("exec");
        this.command.add(docker_container);
        this.command.add("mysqldump");
        this.command.add("-u"+user);
        this.command.add("-p"+password);
        this.command.add(database);
    }

    public List<String> getCommand() {
        return new ArrayList<>(this.command);
    }

    public String getUser() {
        return this.command.get(4);
    }

    public void setUser(String user) {
        this.command.set(4,"-u"+user);
    }

    public String getPassword() {
        return this.command.get(5);
    }

    public void setPassword(String password) {
        this.command.set(5,"-p"+password);
    }

    public String getDatabase() {
        return this.command.get(6);
    }

    public void setDatabase(String database) {
        this.command.set(6,database);
    }

    public String getDocker_container() {
        return this.command.get(2);
    }

    public void setDocker_container(String docker_container) {
        this.command.set(2,docker_container);
    }
}
