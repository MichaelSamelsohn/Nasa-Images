package UnitTests.MarsRoverTests;

import NasaAPI.MarsRover.MarsRoverImages;
import NasaAPI.MarsRover.Rover;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class MarsRoverParametrizedTests extends MarsRoverImages {

    static final Logger log = LogManager.getLogger(MarsRoverParametrizedTests.class.getName());

    private static final String JSON_ROVER = System.getProperty("user.dir") + "/src/test/resources/MarsRoversJson/";
    private static final String[] ROVERS_JSON_PATHS = {
            JSON_ROVER + "CuriosityRoverManifest.json",
            JSON_ROVER + "OpportunityRoverManifest.json",
            JSON_ROVER + "SpiritRoverManifest.json"
    };
    private final String ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE = "The returned value doesn't match the information in the JSON file";

    private static Map<String, Boolean> statusMap;
    static {
        statusMap = new HashMap<>();
        statusMap.put("active", true);
        statusMap.put("inactive", false);
    }

    @Parameterized.Parameters
    public static ArrayList<JSONObject> data() {
        return getRoverManifest();
    }

    @Parameterized.Parameter
    public JSONObject roverManifest;

    private static ArrayList<JSONObject> getRoverManifest() {
        ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (String roverJsonPath : MarsRoverParametrizedTests.ROVERS_JSON_PATHS) {
            try {
                JSONObject jsonObject =
                        new JSONObject(readFileAndReturnAsString(roverJsonPath, Charset.defaultCharset()));
                jsonObjects.add(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonObjects;
    }

    @Ignore("The third value always fails (could be related to NASA API consecutive usage limitations)")
    @Test
    public void testGetMarsRoverManifestMethodFunctionality() throws JSONException {
        // TODO: Investigate why test fails for third value (no matter the order).
        // TODO: Move to Integration tests (there is a dependency on the Rover class).
        log.debug("Getting the relevant rover map");
        Rover rover = getMarsRoverManifest(roverManifest.getString("name"));
        log.debug("Asserting that returned rover name matches the actual rover name");
        Assert.assertEquals("The rover name doesn't match the name it was called with",
                roverManifest.getString("name"), rover.getName());
        log.debug("Asserting that returned rover landing date matches the actual landing date");
        Assert.assertEquals("The rover landing date doesn't match the date it was called with",
                roverManifest.getString("landing_date"), rover.getLandingDate());
        log.debug("Asserting that returned rover status matches the actual status");
        Assert.assertEquals("The rover status doesn't match the status it was called with",
                statusMap.get(roverManifest.getString("status")), rover.isActive());
    }

    @Test
    public void marsRoverConstructorExtractsCorrectInformation() throws JSONException {
        Rover rover = new Rover(roverManifest.getString("name"), roverManifest);

        log.debug("Asserting all the rover fields match the ones from the JSON manifest");
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                roverManifest.getString("name"), rover.getName());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                roverManifest.getString("landing_date"), rover.getLandingDate());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                roverManifest.getString("max_date"), rover.getMaxDate());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                Integer.parseInt(roverManifest.getString("max_sol")), rover.getMaxSol());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                statusMap.get(roverManifest.getString("status")), rover.isActive());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                Integer.parseInt(roverManifest.getString("total_photos")), rover.getTotalPhotos());
    }
}
