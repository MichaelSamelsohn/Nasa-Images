package NasaAPI;

import static NasaAPI.EPIC.getNasaEpicImage;
import static NasaAPI.ImageLibrary.getNasaLibraryImages;
import static NasaAPI.MarsRovers.getMarsRoverImages;

public class Main {
    public static void main(String[] args) {
    getMarsRoverImages("Curiosity", false, "2012-12-23");
    getNasaEpicImage();
    getNasaLibraryImages(new String[]{"crab", "nebula"}, new String[]{"image"}, "1920", "2020");
    }
}
