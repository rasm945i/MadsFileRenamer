package dk.rasmusbendix.filerenamer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageHandler {

    private ArrayList<ImageAnalyzer> analyzers;
    private final File directory;
    private static final int IMAGE_SECTIONS = 10; // I would use 32, but that causes fractions in the height

    public ImageHandler(File imageFolder) {
        this.analyzers = new ArrayList<>();
        this.directory = imageFolder;
        File[] files = directory.listFiles();
        if(files == null) {
            System.out.println("No files found in the provided directory!");
            return;
        }

        for(File file : files) {

            if(!hasImageExtension(file.getName())) {
                System.out.println(file.getName() + " does not seem to be an image.");
                continue;
            }

            try {
                BufferedImage image = ImageIO.read(file);
                analyzers.add(new ImageAnalyzer(file, image, IMAGE_SECTIONS));
                System.out.println("Added analyzer");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Created " + analyzers.size() + " image analyzers ready for use.");

    }

    public void analyzeAll() {
        System.out.println("Image Handler analyzing all images! Files in directory: " + directory.listFiles().length);
        AtomicInteger num = new AtomicInteger();
        analyzers.forEach(ia -> {
            System.out.println("Analyzing " + num.get());
            ia.analyze();
            num.getAndIncrement();
        });

        // Retrieve best matches for each individual image section
        HashMap<ImageSection, ImageSection> bestMatches = compareSections();


        // TODO From each image section, determine which images has the most total matches.
        //      Get this into a list which then is sorted from most matches to fewest matches
        for(ImageSection match : bestMatches.keySet()) {

            ImageSection is = bestMatches.get(match);
            System.out.println("Match: " + match.getAnalyzer().getName() + " -> " + is.getAnalyzer().getName());

        }

    }

    private HashMap<ImageSection, ImageSection> compareSections() {

        HashMap<ImageSection, ImageSection> bestMatches = new HashMap<>();

        // Loop through all analyzers
        for (int i = 0; i < analyzers.size(); i++) {

            ImageAnalyzer currentAnalyzer = analyzers.get(i);

            // Loop through all image sections for that analyzer
            ArrayList<ImageSection> sections = currentAnalyzer.getSections();
            for (int j = 0; j < sections.size(); j++) {
                ImageSection currentSection = sections.get(j);

                // A maxed out ColorCombo so it will always be the worst option
                ColorCombo bestMatch = new ColorCombo(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
                if(bestMatches.containsKey(currentSection) || bestMatches.containsValue(currentSection)) {
                    // The section has already found its best match
                    System.out.println("Section has already found best match! Continuing!");
                    continue;
                }

                // Only use analyzers from the current point and onwards
                System.out.println("Getting analyzers from index " + i + " to " + analyzers.size());
                for(int k = i+1; k < analyzers.size(); k++) {

                    ArrayList<ImageSection> innerSections = analyzers.get(k).getSections();
                    for(int secondSection = 0; secondSection < innerSections.size(); secondSection++) {

                        ColorCombo currentCombo = innerSections.get(secondSection).getColor();
                        ColorCombo matchResult = currentSection.getColor().matchAgainst(currentCombo);

                        if(matchResult.getTotal() < bestMatch.getTotal()) {
                            bestMatch = matchResult;
                            bestMatches.put(currentSection, innerSections.get(secondSection));
                        }

                    }

                }

            }

        }

        return bestMatches;

    }

    private boolean hasImageExtension(String name) {

        name = name.toLowerCase();
        return name.endsWith(".jpeg") || name.endsWith(".jpg") || name.endsWith(".png");

    }

}
