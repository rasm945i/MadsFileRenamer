package dk.rasmusbendix.filerenamer;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.err.println("You must provide at least one argument, specifying the ID you want for the images");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println(args[0] + " is not an integer. Specify ID for the images please.");
            return;
        }

        String path = System.getProperty("user.dir");
        File directory = new File(path);
        if(verifyDirectory(directory)) {

//            ImageRenamer renamer = new ImageRenamer(id, directory);
//            renamer.renameAllFiles();

            System.out.println("Handler being created!");
            ImageHandler handler = new ImageHandler(directory);
            System.out.println("Handler call initiate!");
            handler.analyzeAll();

        } else {
            System.out.println("Cancelled renaming/analyzing!");
        }


    }

    private static boolean verifyDirectory(File dir) {
        if(!dir.isDirectory()) {
            System.out.println("Path is not a directory, cancelling!");
            return false;
        }

        File[] files = dir.listFiles();
        if(files == null) {
            System.out.println("The directory contained no files!");
            return false;
        }

        for(File f : files) {
            System.out.println(f.getName());
        }
        System.out.println("The path is a directory, do you wish to rename the following " + files.length + " files? (y/n)");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes")) {
            System.out.println("Cancelling renaming! No names has been changed.");
            return false;
        }
        System.out.println("Renaming shit!");
        return true;
    }

}
