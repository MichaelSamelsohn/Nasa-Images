package NasaAPI.APOD;

import NasaAPI.Common;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.exit;

public class APOD extends Common {

    static final Logger log = LogManager.getLogger(APOD.class.getName());

    private final String API_KEY = "api_key=fymalkzvEUpMBhhBIpi39IQu0zqsjMy7K2AYhiwJ";
    private final String APOD_BASE_URL = "https://api.nasa.gov/planetary/apod?";

    public void getAstronomyPictureOfTheDay(String date, boolean hd, String imagePath) {
        log.debug("Retrieving APOD (Astronomy Picture Of the Day) image");
        log.info("The selected directory is - {}", imagePath);
        log.info("Selected date is - {}", date);
        log.warn("Date format has to be YYYY-MM-DD of an existing date");
        log.info("HD version of the image - {}", hd);

        downloadImages(new String[]{getAstronomyPictureOfTheDayUrl(date, hd)},
                "wget -O", "APOD_", ".JPG", imagePath);

        log.info("For full API documentation - https://api.nasa.gov/");
    }

    private String getAstronomyPictureOfTheDayUrl(String date, boolean hd) {
        String urlComplement = "date=" + date + "&" + "hd=" + hd + "&" + API_KEY;
        HttpResponse<String> response = null;
        PictureOfTheDay picture = null;
        try {
            log.debug("The URL is - {}{}", APOD_BASE_URL, urlComplement);
            response = Unirest.get(APOD_BASE_URL + urlComplement).
                    asString();
            log.trace("The http response is - {}", response.getBody());
            log.debug("The response code is - {}", response.getCode());
            if (response.getCode() == 400) {
                throw new IllegalArgumentException("Perhaps bad date format or non-existing date given?");
            }

            JSONObject jsonObject = new JSONObject(response.getBody());

            picture = new PictureOfTheDay(jsonObject);
            picture.printInformation();
        } catch (JSONException | UnirestException e) {
            log.error("An error has occurred while conducting the url list", e);
            exit(1);
        }

        return picture.getUrl();
    }

}
