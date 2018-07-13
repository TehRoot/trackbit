import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static spark.Spark.*;
public class Main {

    static CoordinateService coordinateService = new CoordinateService();
    static List<String> list;
    static byte[] byteList;
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(8080);
        get("/", (req, res) -> {res.redirect("index.html"); return "";});
        post("/coordinate", (request, response) -> {
            try {
                byteList = request.bodyAsBytes();
                ObjectMapper objectMapper = new ObjectMapper();
                coordinate coordinate = objectMapper.readValue(byteList, coordinate.class);
                //double latitude = Math.round(Double.parseDouble(list.get(0).replaceAll("\\[|\\]", "")) * 100000d) / 100000d;
                //double longitude = Math.round(Double.parseDouble(list.get(1).replaceAll("\\[|\\]", "")) * 100000d) / 100000d;
                //coordinateService.add(latitude,longitude);
            } catch (ArrayIndexOutOfBoundsException aie) {
                aie.printStackTrace();
            }
            return "";
        });
        get("/coordinate",(request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.type("application/json");
            return coordinateService.returnPolyLine();
        });
        post("/clear", ((request, response) -> coordinateService.clearPolyLine()));
    }
}