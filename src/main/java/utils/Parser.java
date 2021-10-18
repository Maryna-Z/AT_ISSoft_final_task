package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDTO;
import exceptions.CommonException;

import java.io.File;
import java.io.IOException;

public class Parser {
    private final static String JSONPath = "src/test/resources/user.json";

    public static UserDTO readFromFile() {
        try {
            return new ObjectMapper().readValue(new File(JSONPath), UserDTO.class);
        } catch (IOException ex) {
            throw new CommonException("Error parse file", ex);
        }
    }
}
