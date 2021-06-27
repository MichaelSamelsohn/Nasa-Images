package NasaAPI.NasaLibrary;

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

public class ImageLibrary extends Common {

    static final Logger log = LogManager.getLogger(ImageLibrary.class.getName());

    private static final int NUMBER_OF_PHOTOS_TO_COLLECT = 4;
    private static final String NASA_LIBRARY_BASE_URL = "https://images-api.nasa.gov/search?";


    public void getNasaLibraryImages(String[] q, String[] mediaType, String startYear, String endYear, String imagePath) {
        String[] url = getNasaLibraryDataUrl(q, mediaType, startYear, endYear);

        downloadImages(url,"wget -O", "NASA_", ".JPG", imagePath);

        log.info("For full API documentation - https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf");
    }

    private String[] getNasaLibraryDataUrl(String[] query, String[] mediaType, String startYear, String endYear) {
        log.debug("The start year is - {}", startYear);
        log.debug("The end year is - {}", endYear);

        String[] photosUrl = new String[NUMBER_OF_PHOTOS_TO_COLLECT];
        String urlComplement = "q=" + formatStringWithSeparator(query, "%20");
        log.debug("The URL complement before media type handling is - {}", urlComplement);
        if (mediaType != null) {
            urlComplement += "&" + "media_type=" + formatStringWithSeparator(mediaType, ",");
        }
        urlComplement += "&" + "year_start=" + startYear + "&" + "year_end=" + endYear;
        log.debug("The URL complement after media type handling is - {}", urlComplement);

        HttpResponse<String> response = null;
        try {
            response = Unirest.get(NASA_LIBRARY_BASE_URL + urlComplement).
                    asString();
            log.trace("The http response is - {}", response.getBody());

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject collection = jsonObject.getJSONObject("collection");
            JSONArray items = collection.getJSONArray("items");

            for (int i = 0, j = 0; (i < items.length()) && (j < NUMBER_OF_PHOTOS_TO_COLLECT); i++, j++) {
                JSONObject item = items.getJSONObject(i);
                JSONArray links = item.getJSONArray("links");
                JSONObject subItem = links.getJSONObject(0);
                photosUrl[j] = subItem.getString("href");
                log.debug("Added URL - {}", photosUrl[j]);
            }
        } catch (JSONException | UnirestException e) {
            log.error("An error has occurred while conducting the manifest", e);
            e.printStackTrace();
            exit(1);
        }

        return photosUrl;
    }
}
