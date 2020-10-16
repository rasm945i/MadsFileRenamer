package dk.rasmusbendix.filerenamer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSection {

    private int xStart, xEnd;
    private int yStart, yEnd;
    private final BufferedImage image;

    private ColorCombo color;
    private final ImageAnalyzer analyzer;

    public ImageSection(ImageAnalyzer analyzer, int xStart, int xEnd, int yStart, int yEnd, BufferedImage image) {
        this.analyzer = analyzer;
        this.color = new ColorCombo();
        this.xStart = xStart;
        this.xEnd   = xEnd;
        this.yStart = yStart;
        this.yEnd   = yEnd;
        this.image  = image;
    }

    public ImageAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void calculateCombinedColor() {

        if(color.isLocked()) {
            System.err.println("Already analyzed image and locked the combo color!");
            return;
        }

        if(xEnd > image.getWidth()) {
            System.out.println(xEnd + " is greater than the image width. Correcting it to " + image.getWidth());
            xEnd = image.getWidth();
        }

        if(yEnd > image.getHeight()) {
            System.out.println(xEnd + " is greater than the image height. Correcting it to " + image.getWidth());
            yEnd = image.getHeight();
        }

        Color c;
        int pixelsCounted = 0;

        for(; xStart < xEnd; xStart++) {

            for(; yStart < yEnd; yStart++) {

                c = new Color(image.getRGB(xStart, yStart), true);
                color.increaseRed(c.getRed());
                color.increaseGreen(c.getGreen());
                color.increaseBlue(c.getBlue());
                pixelsCounted++;

            }

        }

        color.lock();
//        System.out.println("Pixels counted: " + pixelsCounted);
//        System.out.println("Total color: " + color);

    }


    public ColorCombo getColor() {
        return color;
    }
}
