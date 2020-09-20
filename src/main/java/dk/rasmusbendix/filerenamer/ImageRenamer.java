package dk.rasmusbendix.filerenamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ImageRenamer {

    private int id;
    private String path;

    public ImageRenamer(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public void renameAllFiles() {

        File dir = new File(path);
        if(!dir.isDirectory()) {
            System.out.println("Path is not a directory, cancelling!");
            return;
        }

        File[] files = dir.listFiles();
        if(files == null) {
            System.out.println("The directory contained no files!");
            return;
        }

        for(File f : files) {
            System.out.println(f.getName());
        }
        System.out.println("The path is a directory, do you wish to rename the following " + files.length + " files? (y/n)");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes")) {
            System.out.println("Cancelling renaming! No names has been changed.");
            return;
        }

        int num = 1;
        for(File f : files) {

            renameFile(f, id + "-" + num);
            num++;

        }

    }

    private void renameFile(File file, String newName) {

        String extension = resolveExtension(file);
        Path newPath = file.toPath().resolveSibling(newName + extension);
        try {
            Files.move(file.toPath(), newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String resolveExtension(File file) {

        if(file == null)
            return "";

        String fullName = file.getName();
        if(!fullName.contains(".")) {
            System.out.println("File '" + file.getName() + "' does not contain a dot!");
            return "";
        }

        return fullName.substring(fullName.lastIndexOf('.'));

    }

}
