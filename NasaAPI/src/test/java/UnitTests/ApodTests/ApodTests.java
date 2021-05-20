package UnitTests.ApodTests;

import NasaAPI.APOD.APOD;
import NasaAPI.APOD.PictureOfTheDay;
import NasaAPI.MarsRover.Rover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

public class ApodTests extends APOD {

    static final Logger log = LogManager.getLogger(ApodTests.class.getName());

    private final String APOD_20200517_JSON = System.getProperty("user.dir") + "/src/test/resources/APOD_20200517.json";

    private final String APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE = "The returned value doesn't match the information in the JSON file";

    @Test
    public void marsRoverConstructorExtractsCorrectInformation() {
        // TODO: Add logs.
        PictureOfTheDay picture = null;
        try {
            JSONObject roverManifest =
                    new JSONObject(readFile(APOD_20200517_JSON, Charset.defaultCharset()));
            picture = new PictureOfTheDay(roverManifest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert picture != null;
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "2020-05-17", picture.getDate());
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "What's happening over the water?  " +
                        "Pictured here is one of the better images yet recorded of a waterspout, " +
                        "a type of tornado that occurs over water.  " +
                        "Waterspouts are spinning columns of rising moist air that typically form over warm water. " +
                        "Waterspouts can be as dangerous as tornadoes and can feature wind speeds over 200 kilometers per hour.  " +
                        "Some waterspouts form away from thunderstorms and even during relatively fair weather.  " +
                        "Waterspouts may be relatively transparent and initially visible only by an unusual pattern they create on the water.  " +
                        "The featured image was taken in 2013 July near Tampa Bay, Florida. " +
                        "The Atlantic Ocean off the coast of Florida is arguably the most active area in the world for waterspouts, " +
                        "with hundreds forming each year.    " +
                        "Experts Debate: How will humanity first discover extraterrestrial life?",
                picture.getExplanation());
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "image", picture.getMediaType());
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "A Waterspout in Florida", picture.getTitle());
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE, "v1", picture.getServiceVersion());
        Assert.assertEquals(APOD_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "https://apod.nasa.gov/apod/image/2005/waterspout_mole_960.jpg", picture.getUrl());
    }
}
