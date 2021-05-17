package NasaAPI;

import CMD.Terminal;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

public class ImageLibrary {

    private static final int NUMBER_OF_PHOTOS_TO_COLLECT = 4;
    private static final String NASA_LIBRARY_BASE_URL = "https://images-api.nasa.gov/search?";


    static int getNasaLibraryImages(String[] q, String[] mediaType, String startYear, String endYear) {
        String[] photos = getNasaLibraryDataUrl(q, mediaType, startYear, endYear);

        for (int i = 0; i < photos.length; i++) {
            if (photos[i] != null) {
                String[] commands = {
                        "cd",
                        "cd /Users/michaelsamelsohn/IdeaProjects/NasaImages/Nasa/src/main/resources/NasaLibraryImages/",
                        "wget -O NASA_" + i + ".JPG " + photos[i]};
                Terminal.runCMD(commands);
            } else {
                break;
            }
        }

        return photos.length;
    }

    private static String[] getNasaLibraryDataUrl(String[] q, String[] mediaType, String startYear, String endYear) {
        String[] photosUrl = new String[NUMBER_OF_PHOTOS_TO_COLLECT];
        String urlComplement = "q=" + formatStringWithSpace(q, "%20");
        if (mediaType != null) {
            urlComplement += "&" + "media_type=" + formatStringWithSpace(mediaType, ",");
        }
        urlComplement += "&" + "year_start=" + startYear + "&" + "year_end=" + endYear;

        HttpResponse<String> response = null;
        try {
            response = Unirest.get(NASA_LIBRARY_BASE_URL + urlComplement).
                    asString();

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject collection = jsonObject.getJSONObject("collection");
            JSONArray items = collection.getJSONArray("items");

            int j = 0;
            for (int i = 0; (i < items.length()) && (j < NUMBER_OF_PHOTOS_TO_COLLECT); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONArray links = item.getJSONArray("links");
                JSONObject subItem = links.getJSONObject(0);
                photosUrl[j] = subItem.getString("href");
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photosUrl;
    }

    private static String formatStringWithSpace(String[] strings, String separator) {
        String formattedString = "";
        for (int i = 0; i < strings.length; i++) {
            if (i != strings.length - 1) {
                formattedString += strings[i] + separator;
            } else {
                formattedString += strings[i];
            }
        }
        return formattedString;
    }

}
