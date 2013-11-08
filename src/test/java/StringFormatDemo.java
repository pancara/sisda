import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 5:59 PM
 */
public class StringFormatDemo {
    public static void main(String[] args) throws IOException {
        Map<String, List<String>> mapTypes = populateContentType();
        for (Map.Entry<String, List<String>> entry : mapTypes.entrySet()) {
            System.out.print(entry.getKey());
            System.out.print("\t");

            Iterator<String> i = entry.getValue().iterator();
            while (i.hasNext()) {
                String ext = i.next();
                System.out.print(ext);
                if (i.hasNext())
                    System.out.print("\t");
            }

            System.out.println();
        }
    }

    private static Map<String, List<String>> populateContentType() throws IOException {
        ClassPathResource resource = new ClassPathResource("content-type.properties");
        BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
        Map<String, List<String>> typeMap = new LinkedHashMap<String, List<String>>();
        while (reader.ready()) {
            String line = reader.readLine();
            String[] tokens = StringUtils.split(line, "=");
            String ext = StringUtils.trim(tokens[0]);
            String contentType = StringUtils.trim(tokens[1]);

            if (!typeMap.containsKey(contentType)) {
                typeMap.put(contentType, new LinkedList<String>());
            }

            typeMap.get(contentType).add(ext);
        }
        reader.close();

        return typeMap;
    }
}
