package lessons.lesson6.utils;

import lessons.lesson6.models.Flight;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Set;

public class YmlHandler {
    public static final Path SEAT_BOOKINGS_PATH = Paths.get(
        "src", "main", "java", "lessons", "lesson6", "filefolder", "flight-1.yml");

    public static void writeToYmlFile(Flight flight) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Use block style (expanded)
        options.setPrettyFlow(true);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN); // No quotes unless needed

        Yaml yaml = new Yaml(new NoTagRepresenter(options), options);
        String output = yaml.dump(flight);
        try {
            Files.createDirectories(SEAT_BOOKINGS_PATH.getParent()); // ensure folder exists
            Files.write(SEAT_BOOKINGS_PATH,
                output.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
            System.out.println("YAML written to: " + SEAT_BOOKINGS_PATH.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error while writing YAML file: " + e.getMessage());
        }
    }

    public static Flight readFromYmlFile() {
        if (!Files.exists(SEAT_BOOKINGS_PATH)) {
            System.err.println("[ERROR] File not found: " + SEAT_BOOKINGS_PATH.toAbsolutePath());
            return new Flight();
        }

        if (!Files.isReadable(SEAT_BOOKINGS_PATH)) {
            System.err.println("[ERROR] File is not readable: " + SEAT_BOOKINGS_PATH.toAbsolutePath());
            return new Flight();
        }

        try (InputStreamReader reader = new InputStreamReader(
            Files.newInputStream(SEAT_BOOKINGS_PATH), StandardCharsets.UTF_8)) {

            Yaml yaml = new Yaml();
            Flight map = yaml.loadAs(reader, Flight.class);

            if (map == null || map.getSeats() == null) {
                System.err.println("[WARN] YAML file loaded but content is empty or malformed.");
                return new Flight();
            }

            System.out.println("[INFO] YAML file successfully loaded.");
            return map;

        } catch (IOException e) {
            System.err.println("[ERROR] IO error reading YAML file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error while parsing YAML: " + e.getMessage());
        }

        return new Flight();
    }

    /**
     * This Representer class inheritor is implemented to suppress !!className in output
     */
    public static class NoTagRepresenter extends Representer {

        public NoTagRepresenter(DumperOptions options) {
            super(options);
        }

        @Override
        protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {

            // Represent bean as normal map node (property name -> value)
            MappingNode node = super.representJavaBean(properties, javaBean);

            // Remove the tag to suppress !!className in output
            node.setTag(Tag.MAP);

            return node;
        }
    }
}