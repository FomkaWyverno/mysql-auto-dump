package ua.wyverno;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class MySQLBackupTask extends TimerTask {

    private final MySQLBackupCommand command;
    private final Path directory_backup;
    private final String file_name_backup;
    private final int max_backup;

    public MySQLBackupTask(MySQLBackupCommand command,
                           Path directory_backup,
                           String file_name_backup,
                           int max_backup) throws IOException {
        if (Files.isRegularFile(directory_backup))
            throw new NotDirectoryException(directory_backup + " is not directory!");
        this.command = command;
        this.directory_backup = directory_backup;
        this.file_name_backup = file_name_backup;
        this.max_backup = max_backup;

        if (Files.notExists(directory_backup)) {
            System.out.println("Create directories -> " + this.directory_backup);
            Files.createDirectories(this.directory_backup);
        }
    }

    @Override
    public void run() {
        System.out.println("Start backup!");

        String backup = getBackup();

        if (backup.isEmpty()) {
            System.out.println("Backup is empty!!");
            return;
        }

        List<File> filesBackup = this.getBackupFiles();
        System.out.println(filesBackup);

        filesBackup.sort(new ComparatorFile());
        renameFilesBackup(filesBackup);

        File backupFile = new File(this.directory_backup + "\\" + this.file_name_backup + "1.sql");

        try (BufferedWriter writer = Files.newBufferedWriter(backupFile.toPath())) {
            System.out.println("Start write backup");
            writer.write(backup);
            System.out.println("End write backup");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End backup!");
    }

    private List<File> getBackupFiles() {

        List<File> backupFiles = new ArrayList<>();
        for (File file : Objects.requireNonNull(this.directory_backup.toFile().listFiles())) {
            //System.out.println("File: " + file);
            if (file.getName().endsWith(".sql")) {
               // System.out.println("File .sql: " + file.getName());
                String fileName = file.getName().replaceFirst("\\d+[.][^.]+$", "");
               // System.out.println("File without .sql: " + fileName);

                //System.out.println("Simple file: " + fileName);
                if (fileName.equals(this.file_name_backup)) {
                    //System.out.println("File is backup -> " + file);
                    backupFiles.add(file);
                }
            }
        }

        return backupFiles;
    }

    private void renameFilesBackup(List<File> listFilesBackup) {
        for (int i = listFilesBackup.size()-1; i >= 0; i--) {
            File file = listFilesBackup.get(i);
            int indexFile = Integer.parseInt(file.getName().replaceFirst("[.][^.]+$", "").replace(this.file_name_backup, ""));
            if (indexFile >= this.max_backup) {
                if (file.delete()) {
                    //System.out.println("Delete backup file -> " + file);
                } else {
                    //System.out.println("Can't delete backup file -> " + file);
                }
            } else {
                File newFile = new File(file.getParentFile() + "\\" + this.file_name_backup + (indexFile + 1) + ".sql");
                if (file.renameTo(newFile)) {
                    //System.out.println("File renamed successfully. " + file + " to " + newFile);
                } else {
                    //System.out.println("Failed to rename file. " + file + " to " + newFile);
                }
            }
        }
    }

    private String getBackup() {
        ProcessBuilder pb = new ProcessBuilder()
                .redirectError(ProcessBuilder.Redirect.INHERIT);

        //System.out.println("Command List -> " + this.command.getCommand());
        pb.command(this.command.getCommand());

        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            reader.close();
            process.waitFor();
            return result.toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
