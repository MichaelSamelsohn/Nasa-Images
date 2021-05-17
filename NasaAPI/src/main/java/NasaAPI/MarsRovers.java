package NasaAPI;

import CMD.Terminal;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MarsRovers {

    private static final int NUMBER_OF_PHOTOS_TO_COLLECT = 4;
    private static final String MARS_ROVER_PHOTOS_BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/";
    private static final String API_KEY = "api_key=fymalkzvEUpMBhhBIpi39IQu0zqsjMy7K2AYhiwJ";

    static int getMarsRoverImages(String rover, boolean sol, String date) {
        HashMap<Object, Object> obj = getMarsRoverManifest(rover);
        String[] photos = getMarsRoverImagesUrl(obj, sol, date);

        for (int i = 0; i < photos.length; i++) {
            if (photos[i] != null) {
                String[] commands = {
                        "cd",
                        "cd /Users/michaelsamelsohn/IdeaProjects/NasaImages/Nasa/src/main/resources/MarsRoverImages/",
                        "wget -O MARS_" + i + ".JPG " + photos[i]};
                Terminal.runCMD(commands);
            } else {
                break;
            }
        }

        return photos.length;
    }

    private static String[] getMarsRoverImagesUrl(HashMap<Object, Object> photoManifest, boolean sol, String date) {
        String[] photosUrl = new String[NUMBER_OF_PHOTOS_TO_COLLECT];
        String urlComplement;
        String rover = (String) photoManifest.get("roverName");
        if (sol) { // Mars sol.
            urlComplement = "rovers/" + rover + "/photos?sol=" + date + "&" + API_KEY;
        } else { // Earth date.
            urlComplement = "rovers/" + rover + "/photos?earth_date=" + date + "&" + API_KEY;
        }
        HttpResponse<String> response = null;
        HashMap<Object, Object> roverMap = new HashMap<Object, Object>();
        try {
            response = Unirest.get(MARS_ROVER_PHOTOS_BASE_URL + urlComplement).
                    asString();

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray photos = jsonObject.getJSONArray("photos");

            int j = 0;
            for (int i = 0; (i < photos.length()) && (j < NUMBER_OF_PHOTOS_TO_COLLECT); i++) {
                JSONObject photo = photos.getJSONObject(i);
                photosUrl[j] = photo.getString("img_src");
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photosUrl;
    }

    private static HashMap<Object, Object> getMarsRoverManifest(String rover) {
        String urlComplement = "manifests/" + rover + "?" + API_KEY;
        HttpResponse<String> response = null;
        HashMap<Object, Object> roverMap = new HashMap<Object, Object>();
        try {
            response = Unirest.get(MARS_ROVER_PHOTOS_BASE_URL + urlComplement).
                    asString();

            JSONObject jsonManifest = new JSONObject(response.getBody());
            JSONObject jsonPhotoManifest = jsonManifest.getJSONObject("photo_manifest");
            String landingDate = jsonPhotoManifest.getString("landing_date");
            String maxDate = jsonPhotoManifest.getString("max_date");
            int maxSol = jsonPhotoManifest.getInt("max_sol");
            String status = jsonPhotoManifest.getString("status");
            int totalPhotos = jsonPhotoManifest.getInt("total_photos");

            roverMap.put("roverName", rover);
            roverMap.put("landingDate", landingDate);
            roverMap.put("maxDate", maxDate);
            roverMap.put("maxSol", maxSol);
            roverMap.put("status", status);
            roverMap.put("totalPhotos", totalPhotos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roverMap;
    }

}
