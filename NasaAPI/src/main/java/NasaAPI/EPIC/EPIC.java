package NasaAPI.EPIC;

import NasaAPI.Common;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.exit;

public class EPIC extends Common {

    static final Logger log = LogManager.getLogger(EPIC.class.getName());

    private int numberOfPhotosToCollect = 1; // Default is 1.
    private static final String EPIC_BASE_URL = "https://epic.gsfc.nasa.gov/";

    public void getEpicImages(int numOfImages, String imagePath) {
        log.debug("The selected number of images is - {}", numOfImages);
        log.debug("The selected image path is - {}", imagePath);

        numberOfPhotosToCollect = numberOfImagesToDownload(numOfImages);

        downloadImages(getNasaEpicImagesUrl(),"curl -o", "EPIC_", ".png", imagePath);

        log.info("For full API documentation - https://epic.gsfc.nasa.gov/about/api");
    }

    private String[] getNasaEpicImagesUrl() {
        String[] photosUrl = new String[numberOfPhotosToCollect];
        String urlComplement = "api/images.php";
        HttpResponse<String> response = null;
        try {
            log.debug("The full URL is - {}{}", EPIC_BASE_URL, urlComplement);
            response = Unirest.get(EPIC_BASE_URL + urlComplement).
                    asString();
            log.debug("The response code is - {}", response.getCode());
            log.trace("The response code is - {}", response.getBody());

            JSONArray jsonArray = new JSONArray(response.getBody()); // JSON array of all images.

            for (int i = 0, j = 0; (i < jsonArray.length()) && (j < numberOfPhotosToCollect); i++, j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // Get first image JSON.
                String imageID = jsonObject.getString("image");
                String imageDate = jsonObject.getString("date");

                // Split the date to year, month and day.
                String[] temp = imageDate.split(" ");
                String year = temp[0].split("-")[0];
                String month = temp[0].split("-")[1];
                String day = temp[0].split("-")[2];

                photosUrl[j] = EPIC_BASE_URL + "archive/natural/" + year + "/" + month + "/" + day + "/png/" + imageID + ".png";
                log.debug("{}) Photo URL is - {}", j, photosUrl[j]);
            }

            return photosUrl;
        } catch (JSONException | UnirestException e) {
            log.error("An error has occurred while conducting the manifest", e);
            e.printStackTrace();
            exit(1);
        }
        return photosUrl;
    }
}
