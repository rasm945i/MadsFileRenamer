package dk.rasmusbendix.filerenamer;

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

        ImageRenamer renamer = new ImageRenamer(id, System.getProperty("user.dir"));
        renamer.renameAllFiles();


    }

}
