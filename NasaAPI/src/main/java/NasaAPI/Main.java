package NasaAPI;

import static NasaAPI.Mars.MarsRoverImages.getMarsRoverImages;

public class Main {
    public static void main(String[] args) {
        getMarsRoverImages("Curiosity", -4,"2022-12-23", "/Users/michaelsamelsohn/Desktop/");
//        downloadEpicImages("/Users/michaelsamelsohn/Desktop/");
//        getNasaLibraryImages(new String[]{"crab", "nebula"}, new String[]{"image"}, "1920", "2020");
    }
}
