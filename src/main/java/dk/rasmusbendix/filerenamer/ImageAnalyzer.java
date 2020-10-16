package dk.rasmusbendix.filerenamer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageAnalyzer {

    // Set of all image-sections we calculate colors on
    private ArrayList<ImageSection> sections;
    // The image we want to inspect
    private BufferedImage image;
    // Amount of sections is only 1-way. So if 5 is entered, its 5 horizontal and 5 vertical
    private int amountOfSections;

    private File sourceFile;

    private boolean canAlterSections = true;

    public ImageAnalyzer(File source, BufferedImage image, int amountOfSections) {
        this.sourceFile = source;
        this.image = image;
        this.sections = new ArrayList<>();
        this.amountOfSections = amountOfSections;
    }

    public String getName() {
        return sourceFile.getName();
    }

    public void analyze() {

        System.out.println("Analyzing image and creating sections!");

        canAlterSections = false;

        int sectionWidth = image.getWidth() / amountOfSections;
        int sectionHeight = image.getHeight() / amountOfSections;

        for(int x = 0; x < image.getWidth(); x+=sectionWidth) {

            for(int y = 0; y < image.getHeight(); y+=sectionHeight) {

                ImageSection section = new ImageSection(this, x, x + sectionWidth, y, y + sectionHeight, image);
                section.calculateCombinedColor();
                sections.add(section);

            }

        }

    }

    public ArrayList<ImageSection> getSections() {
        return sections;
    }

    public int getAmountOfSections() {
        return amountOfSections;
    }

    public void setAmountOfSections(int amountOfSections) throws Exception {
        if(canAlterSections)
            this.amountOfSections = amountOfSections;
        else
            throw new Exception("Cannot alter amount of sections after 'analyze' has been called!");
    }
}
