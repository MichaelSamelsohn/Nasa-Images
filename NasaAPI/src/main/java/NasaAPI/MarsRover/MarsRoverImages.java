package NasaAPI.MarsRover;

import NasaAPI.Common;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.exit;

public class MarsRoverImages extends Common {

    static final Logger log = LogManager.getLogger(MarsRoverImages.class.getName());

    private int numberOfPhotosToCollect = 1; // Default is 1.
    private static final String MARS_ROVER_PHOTOS_BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/";
    private static final String API_KEY = "api_key=fymalkzvEUpMBhhBIpi39IQu0zqsjMy7K2AYhiwJ";

    public void getMarsRoverImages(@NotNull String roverName, int numOfImages, Object date, String imagePath) {
        log.debug("The selected rover name is - {}", roverName);
        log.debug("The selected number of images is - {}", numOfImages);
        log.debug("The selected date is - {}", date);
        log.debug("The selected image path is - {}", imagePath);

        numberOfPhotosToCollect = numberOfImagesToDownload(numOfImages);

        String[] url = getMarsRoverImagesUrl(getMarsRoverManifest(roverName), date);
        downloadImages(url,"wget -O", "MARS_", ".JPG", imagePath);

        log.info("For full API documentation - https://api.nasa.gov/");
    }

    protected String[] getMarsRoverImagesUrl(Rover rover, Object date) {
        String[] photosUrl = new String[numberOfPhotosToCollect];
        String urlComplement;
        String validatedDate;

        if (date instanceof Integer) { // Mars sol.
            log.debug("The provide date is of type int, therefore, Mars sol date");
            validatedDate = String.valueOf(validateNumberInInterval(0, rover.getMaxSol(), (Integer) date));
            urlComplement = "rovers/" + rover.getName() + "/photos?sol=" + validatedDate + "&" + API_KEY;
        } else if (date instanceof String) { // Earth date.
            log.debug("The provide date is of type String, therefore, Earth date");
            validatedDate = checkEarthDate(rover.getLandingDate(), rover.getMaxDate(), (String) date);
            urlComplement = "rovers/" + rover.getName() + "/photos?earth_date=" + validatedDate + "&" + API_KEY;
        } else {
            throw new IllegalArgumentException (
                    "Wrong type of date given." +
                    "Should be either integer (for Mars sol) or string (for Earth date)");
        }
        log.debug("The URL complement after the date handling is - {}", urlComplement);

        HttpResponse<String> response = null;
        try {
            response = Unirest.get(MARS_ROVER_PHOTOS_BASE_URL + urlComplement).
                    asString();
            log.trace("The http response is - {}", response.getBody());
            log.debug("The response code is - {}", response.getCode());

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray photos = jsonObject.getJSONArray("photos");

            for (int i = 0, j = 0; (i < photos.length()) && (j < numberOfPhotosToCollect); i++,j++) {
                JSONObject photo = photos.getJSONObject(i);
                photosUrl[j] = photo.getString("img_src");
            }
        } catch (JSONException | UnirestException e) {
            log.error("An error has occurred while conducting the url list", e);
            exit(1);
        }

        return photosUrl;
    }

    protected Rover getMarsRoverManifest(String roverName) {
        String urlComplement = "manifests/" + roverName + "?" + API_KEY;
        log.debug("The URL complement is - {}", urlComplement);
        HttpResponse<String> response = null;
        Rover rover = null;

        try {
            response = Unirest.get(MARS_ROVER_PHOTOS_BASE_URL + urlComplement).
                    asString();
            log.debug("The response code is - {}", response.getCode());
            log.trace("The response is - {}", response.getBody());
            if (response.getBody().equals("{\"errors\":\"Invalid Rover Name\"}")) {
                // Assert that the rover name provided matches an available option.
                throw new IllegalArgumentException
                        ("Bad rover name. The acceptable choices are - Curiosity, Opportunity and Spirit");
            }

            JSONObject jsonManifest = new JSONObject(response.getBody());
            JSONObject jsonRoverManifest = jsonManifest.getJSONObject("photo_manifest");

            rover = new Rover(roverName, jsonRoverManifest);
            rover.printInformation();
        } catch (JSONException | UnirestException e) {
            log.error("An error has occurred while conducting the manifest", e);
            e.printStackTrace();
            exit(1);
        }

        return rover;
    }

    public String checkEarthDate(String minDate, String maxDate, String date) {
        // maxDate - Latest date available for the particular rover.
        // minDate - Earliest date available for the particular rover.
        // date - Provided date.
        // The format of the dates is - "YYYY-MM-DD".
        log.debug("The provided maxDate parameter is - {}", maxDate);
        log.debug("The provided minDate parameter is - {}", minDate);
        log.debug("The provided date parameter is - {}", date);

        // Extract the year, month and day of he provided date.
        int maxDateComponents = Integer.parseInt(maxDate.replace("-", ""));
        int minDateComponents = Integer.parseInt(minDate.replace("-", ""));
        int dateComponents = Integer.parseInt(date.replace("-", ""));

        String validatedDate = String.valueOf(
                validateNumberInInterval(minDateComponents, maxDateComponents, dateComponents));
        validatedDate =
                validatedDate.substring(0,4) + "-" + // Year value.
                validatedDate.substring(4,6) + "-" + // Month value.
                validatedDate.substring(6); // Day value.
        return validatedDate;
    }
}
