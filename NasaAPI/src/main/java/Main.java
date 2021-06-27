import NasaAPI.APOD.APOD;
import NasaAPI.EPIC.EPIC;
import NasaAPI.MarsRover.MarsRoverImages;
import NasaAPI.NasaLibrary.ImageLibrary;

public class Main {
    public static void main(String[] args) {
//        MarsRoverImages marsRoverImages = new MarsRoverImages();
//        marsRoverImages.getMarsRoverImages("Spirit", 1,"2022-12-23", "/Users/michaelsamelsohn/Desktop/");

        EPIC epic = new EPIC();
        epic.getEpicImages(1, "/Users/michaelsamelsohn/Desktop/");

        ImageLibrary imageLibrary = new ImageLibrary();
        imageLibrary.getNasaLibraryImages(new String[]{"crab", "nebula"}, new String[]{"image"}, "1920", "2020",
                "/Users/michaelsamelsohn/Desktop/");

        APOD apod = new APOD();
        apod.getAstronomyPictureOfTheDay("2019-05-17",false,"/Users/michaelsamelsohn/Desktop/");
    }
}
