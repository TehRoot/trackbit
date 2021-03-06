import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static spark.Spark.*;

public class Main {
    static CoordinateService coordinateService = new CoordinateService();
    static LoginService loginService = new LoginService();
    //insert file reading to parse config
    //static UtilityMethods utilityMethods = new UtilityMethods();
    public static void main(String[] args) {
        final List<String> list;
        staticFileLocation("/public");
        port(6555);
        get("/", (req, res) -> {res.redirect("index.html"); return "";});

        //stub in authentication using //before in order to authorize posting routes/coordinates/other shit
        //general practical login methods

        post("/coordinate", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
            res.type("application/json");

            return "";
        });

        get("/polyline",(req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
            res.type("application/json");
            return coordinateService.returnPolyLine();
        });

        get("/token", (req, res) -> {
            res.type("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode requestNode = objectMapper.readTree(req.bodyAsBytes());
            //login service generate, store, and return token for user from a get request
            //checks database for a token
            //TODO -- Add token field to user database table
            return "";
        });

        before("/token", (req, res) -> {
            User u = LoginService.getSessionAuth(req);
            //tokenization for applications for signing post requests without submitting user credentials
            //token generation allows for division of login
            //if no session exists before logging in the service
            //requires a login in order for it to work
            if(u == null){
                halt(401);
            }
        });

        post("/login", (req, res) -> {
           res.type("application/json");
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode postNode = objectMapper.readTree(req.bodyAsBytes());
           if(loginService.checkUserLogin(postNode, req)){
                res.redirect("index.html");
           } else {
               halt(401);
           }
           return "";
        });

        get("/login", (req, res) -> {
            res.type("application/json");
            res.redirect("login.html");
            return "";
        });

        post("/register", (req, res) -> {
            //stub out generation here
            //user provides on registration page, username + password
            //need to stub out session shit as well but that needs to go in
            //login and transactional pages...
            res.type("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode postNode = objectMapper.readTree(req.bodyAsBytes());
            if(loginService.createUser(postNode)){
                res.redirect("index.html");
            } else {
                //stubbed to null for now?
                halt(401);
            }
            return "";
        });

        get("/register", (req, res) -> {
            res.type("application/json");
            res.redirect("register.html");
            return "";
        });
    }
}