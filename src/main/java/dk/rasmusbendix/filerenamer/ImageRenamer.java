package dk.rasmusbendix.filerenamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageRenamer {

    private int id;
    private File directory;

    public ImageRenamer(int id, File directory) {
        this.id = id;
        this.directory = directory;
    }

    public void renameAllFiles() {

        File[] files = directory.listFiles();
        if(files == null) {
            System.out.println("No files found, files[] is null!");
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
