package NasaAPI.APOD;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class PictureOfTheDay {

    static final Logger log = LogManager.getLogger(PictureOfTheDay.class.getName());

    private final String date;
    private final String explanation;
    private final String mediaType;
    private final String serviceVersion;
    private final String title;
    private final String url;

    PictureOfTheDay(JSONObject photoManifest) throws JSONException {
            this.date = photoManifest.getString("date");
            this.explanation = photoManifest.getString("explanation");
            this.mediaType = photoManifest.getString("media_type");
            this.serviceVersion = photoManifest.getString("service_version");
            this.title = photoManifest.getString("title");
            this.url = photoManifest.getString("url");
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void printInformation() {
        System.out.println("\nAll the information about the APOD (Astronomy Picture Of the Day) is displayed below:");
        System.out.println("DATE - " + this.date);
        System.out.println("TITLE - " + this.title);
        System.out.println("EXPLANATION - " + this.explanation);
        System.out.println("URL - " + this.url);
        System.out.println("MEDIA TYPE - " + this.mediaType);
        System.out.println("SERVICE VERSION - " + this.serviceVersion + "\n");
    }
}
