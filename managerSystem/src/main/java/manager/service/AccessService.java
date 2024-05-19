package manager.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
@Service
public class AccessService {

    private static final String ACCESS_FILE = "src/main/resources/accessdata.txt";

    public void addUserAccess(int userId, List<String> endpoints) throws IOException {
        StringBuilder data = new StringBuilder();
        for (String endpoint : endpoints) {
            data.append(userId).append(",").append(endpoint).append("\n");
        }
        writeToFile(data.toString());
    }

    public boolean checkAccess(int userId, String resource) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ACCESS_FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == userId && parts[1].equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private void writeToFile(String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ACCESS_FILE, true));
        writer.write(data);
        writer.close();
    }
}
