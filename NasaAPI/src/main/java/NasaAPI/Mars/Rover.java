package NasaAPI.Mars;

public class Rover {

    private final String name;
    private final String landingDate;
    private final String maxDate;
    private final int maxSol;
    private final boolean isActive;
    private final int totalPhotos;

    Rover(String name, String landingDate, String maxDate, int maxSol, boolean isActive, int totalPhotos) {
        this.name = name;
        this.landingDate = landingDate;
        this.maxDate = maxDate;
        this.maxSol = maxSol;
        this.isActive = isActive;
        this.totalPhotos = totalPhotos;
    }

    public String getName() {
        return name;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }
}
