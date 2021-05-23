import NasaAPI.APOD.APOD;
import NasaAPI.MarsRover.MarsRoverImages;

public class Main {
    public static void main(String[] args) {
        MarsRoverImages marsRoverImages = new MarsRoverImages();
        marsRoverImages.getMarsRoverImages("Spirit", -4,"2022-12-23", "/Users/michaelsamelsohn/Desktop/");

//        EPIC epic = new EPIC();
//        epic.getEpicImages(1, "/Users/michaelsamelsohn/Desktop/");

//        getNasaLibraryImages(new String[]{"crab", "nebula"}, new String[]{"image"}, "1920", "2020");

//        APOD mwsa = new APOD();
//        mwsa.getAstronomyPictureOfTheDay("2020-05-17",false,"/Users/michaelsamelsohn/Desktop/");
    }
}
