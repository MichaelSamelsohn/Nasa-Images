package NasaAPI;

import CMD.Terminal;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EPIC {

    static final Logger log = LogManager.getLogger(EPIC.class.getName());

    private static final String EPIC_BASE_URL = "https://epic.gsfc.nasa.gov/";
    private static final int NUMBER_OF_PHOTOS_TO_COLLECT = 1;

    public static int downloadEpicImages() {
        String[] url = getNasaEpicImagesUrl();

        for (int i = 0; i < url.length; i++) {
            if (url[i] != null) {
                String[] commands = {
                        "cd",
                        // TODO: Use relative path, not full one.
                        "cd /Users/michaelsamelsohn/IdeaProjects/Nasa-Images/NasaAPI/src/main/resources/NasaEpicImages/",
                        "curl -o EPIC_" + i + ".png " + url[i]};
                Terminal.runCMD(commands);
            } else {
                break;
            }
        }

        return url.length;
    }

    private static String[] getNasaEpicImagesUrl() {
        String[] photosUrl = new String[NUMBER_OF_PHOTOS_TO_COLLECT];
        String urlComplement = "api/images.php";
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(EPIC_BASE_URL + urlComplement).
                    asString();

            JSONArray jsonArray = new JSONArray(response.getBody()); // JSON array of all images.

            int j = 0;
            for (int i = 0; (i < jsonArray.length()) && (j < NUMBER_OF_PHOTOS_TO_COLLECT); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // Get first image JSON.
                String imageID = jsonObject.getString("image");
                String imageDate = jsonObject.getString("date");

                // Split the date to year, month and day.
                String[] temp = imageDate.split(" ");
                String year = temp[0].split("-")[0];
                String month = temp[0].split("-")[1];
                String day = temp[0].split("-")[2];

                photosUrl[j] = EPIC_BASE_URL + "archive/natural/" + year + "/" + month + "/" + day + "/png/" + imageID + ".png";
                j++;
            }

            return photosUrl;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
