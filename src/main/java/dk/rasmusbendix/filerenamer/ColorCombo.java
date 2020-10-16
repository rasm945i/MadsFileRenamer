package dk.rasmusbendix.filerenamer;

public class ColorCombo {

    private int r,g,b;
    private boolean locked;

    public ColorCombo() {
        this(0,0,0);
    }

    public ColorCombo(int red, int green, int blue) {
        this.locked = false;
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public void setRed(int red) {
        if(!locked)
            this.r = red;
    }

    public void setGreen(int green) {
        if(!locked)
            this.g = green;
    }

    public void setBlue(int blue) {
        if(!locked)
            this.b = blue;
    }

    public void increaseRed(int red) {
        setRed(getRed() + red);
    }

    public void increaseGreen(int green) {
        setGreen(getGreen() + green);
    }

    public void increaseBlue(int blue) {
        setBlue(getBlue() + blue);
    }

    public long getTotal() {
        return getRed() + getGreen() + getBlue();
    }

    public ColorCombo matchAgainst(ColorCombo combo) {

        ColorCombo c = new ColorCombo();
        c.setRed(Math.abs(getRed() - combo.getRed()));
        c.setGreen(Math.abs(getGreen() - combo.getGreen()));
        c.setBlue(Math.abs(getBlue() - combo.getBlue()));
        return c;

    }

    @Override
    public String toString() {
        return "ColorCombo{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
