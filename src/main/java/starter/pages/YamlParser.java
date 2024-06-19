package starter.pages;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.yaml.snakeyaml.Yaml;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static starter.pages.SDFactory.LOCATOR_MAP;

public class YamlParser {
    private static final String SERENITY_YAML_DIRECTORY_PATH = "serenity.yaml.directory.path";
    private final String directoryPath;

    public YamlParser() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String path = environmentVariables.getProperty(SERENITY_YAML_DIRECTORY_PATH);

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Error: '" + SERENITY_YAML_DIRECTORY_PATH + "' environment variable is not set or is empty.");
        }

        directoryPath = path;
    }

    public By findSelector(String page, String name) {
        By selector = null;
        try {
            Optional<Path> pageFilePath = Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().replaceAll("\\.[^.]*$", "").equals(page))
                    .findFirst();

            if (pageFilePath.isPresent()) {
                try (FileInputStream pageFile = new FileInputStream(pageFilePath.get().toFile())) {
                    Yaml yaml = new Yaml();
                    Map<String, List<Map<String, Map<String, String>>>> data = yaml.load(pageFile);
                    if (data != null) {
                        for (List<Map<String, Map<String, String>>> list : data.values()) {
                            for (Map<String, Map<String, String>> map : list) {
                                for (Map<String, String> stringStringMap : map.values()) {
                                    if (stringStringMap.get("Name").equals(name)) {
                                        selector = getByFromElementData(stringStringMap);
                                        System.out.println(stringStringMap);
                                        break;
                                    }
                                }
                                if (selector != null) break;
                            }
                            if (selector != null) break;
                        }
                    }
                }
            } else {
                System.err.println("Error: File for page '" + page + "' not found in directory: " + directoryPath);
            }
        } catch (IOException e) {
            System.err.println("Error reading files from directory: " + directoryPath + " - " + e.getMessage());
        }
        return selector;
    }

    public static By getByFromElementData(Map<String, String> elementData) {
        String locatorType = elementData.get("Locator-Type");
        String locatorValue = elementData.get("Locator-Value");

        if (locatorType == null || locatorValue == null) {
            throw new IllegalArgumentException("Error: 'Locator-Type' or 'Locator-Value' is null in element data: " + elementData);
        }
        Function<String,By> byFunction = LOCATOR_MAP.get(locatorType);
        if (byFunction != null) {
            return byFunction.apply(locatorValue);
        } else {
            throw new IllegalArgumentException("Error: Unsupported locator type: " + locatorType);
        }
    }

    public static void main(String[] args) {
        YamlParser yamlParser = new YamlParser();
        By selector = yamlParser.findSelector("Login", "login");
        if (selector != null) {
            System.out.println("Selector found: " + selector);
        } else {
            System.out.println("Selector not found.");
        }
    }
}