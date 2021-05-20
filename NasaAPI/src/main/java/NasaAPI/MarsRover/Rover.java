package NasaAPI.MarsRover;

import org.json.JSONException;
import org.json.JSONObject;

public class Rover {

    private final String name;
    private final String landingDate;
    private final String maxDate;
    private final int maxSol;
    private final boolean isActive;
    private final int totalPhotos;

    public Rover(String name, JSONObject roverManifest) throws JSONException {
        this.name = name;
        this.landingDate = roverManifest.getString("landing_date");
        this.maxDate = roverManifest.getString("max_date");
        this.maxSol = roverManifest.getInt("max_sol");
        this.isActive = roverManifest.getString("status").equals("active");
        this.totalPhotos = roverManifest.getInt("total_photos");
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

    public void printInformation() {
        System.out.println("\nAll the information about the selected rover is displayed below:");
        System.out.println("NAME - " + this.name);
        System.out.println("LANDING DATE - " + this.landingDate);
        System.out.println("MAX DATE (Earth date) - " + this.maxDate);
        System.out.println("MAX SOL (Mars date) - " + this.maxSol);
        System.out.println("IS ACTIVE - " + this.isActive);
        System.out.println("TOTAL PHOTOS - " + this.totalPhotos + "\n");
    }
}
