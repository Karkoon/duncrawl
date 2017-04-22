package ashlified.components;

/**
 * Created by karkoon on 01.04.17.
 */
public final class RateOfAppearanceComponent {

    private int levelOfFirstAppearance;
    private int levelOfLastAppearance;
    private int rateOfAppearance;
    private String themeOfPlaceOfAppearance;

    public int getLevelOfFirstAppearance() {
        return levelOfFirstAppearance;
    }

    public void setLevelOfFirstAppearance(int levelOfFirstAppearance) {
        this.levelOfFirstAppearance = levelOfFirstAppearance;
    }

    public int getLevelOfLastAppearance() {
        return levelOfLastAppearance;
    }

    public void setLevelOfLastAppearance(int levelOfLastAppearance) {
        this.levelOfLastAppearance = levelOfLastAppearance;
    }

    public int getRateOfAppearance() {
        return rateOfAppearance;
    }

    public void setRateOfAppearance(int rateOfAppearance) {
        this.rateOfAppearance = rateOfAppearance;
    }

    public String getThemeOfPlaceOfAppearance() {
        return themeOfPlaceOfAppearance;
    }

    public void setThemeOfPlaceOfAppearance(String themeOfPlaceOfAppearance) {
        this.themeOfPlaceOfAppearance = themeOfPlaceOfAppearance;
    }
}
