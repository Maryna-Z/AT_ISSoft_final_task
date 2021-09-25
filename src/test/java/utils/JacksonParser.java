package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDTO;

import java.io.File;
import java.io.IOException;


public class JacksonParser {
    private final static String JSON = "src/test/resources/user.json";
    File file = new File(JSON);

    public void parseJackson() {
        try {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = mapper.readValue(file, UserDTO.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
