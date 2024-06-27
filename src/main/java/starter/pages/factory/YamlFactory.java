package starter.pages.factory;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import starter.pages.AbstractPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static starter.pages.factory.PageFactory.LOCATOR_MAP;

public class YamlFactory implements SelectorFactory {

    private final String directoryPath;

    public YamlFactory() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        directoryPath = environmentVariables.getProperty("serenity.yaml.directory.path");
    }

    private List<Map<String, String>> getSelectorsFromFile(String filePath) {
        List<Map<String, String>> selectors = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, Map<String, String>>>> data = yaml.load(fis);
            if (data != null) {
                data.values().forEach(
                        list -> list.forEach(
                                map -> map.values().forEach(
                                        selectors::add
                                )
                        )
                );
            }
        } catch (IOException | YAMLException e) {
            System.err.println("Error reading/parsing file: " + filePath + " - " + e.getMessage());
        }
        return selectors;
    }

    private List<Map<String, String>> getAllSelectorsFromPage(String fileName) {
        List<Map<String, String>> allSelectors = new ArrayList<>();
        try {
            allSelectors = Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().replaceAll("\\.[^.]*$", "").equalsIgnoreCase(fileName))
                    .flatMap(path -> getSelectorsFromFile(path.toString()).stream())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading files from directory: " + directoryPath + " - " + e.getMessage());
        }
        return allSelectors;
    }

    @Override
    public By getSelector(String name) {
        String page = PageFactory.getCurrentPageName();
        List<Map<String, String>> selector = getAllSelectorsFromPage(page);
        return selector.stream()
                .filter(query -> name.equalsIgnoreCase(query.get("name")))
                .findFirst()
                .map(this::getByFromElementData)
                .orElse(null);
    }

    private By getByFromElementData(Map<String, String> elementData) {
        String locatorType = elementData.get("locator-type");
        String locatorValue = elementData.get("locator-value");

        if (locatorType == null || locatorValue == null) {
            System.err.println("Error: 'Locator-Type' or 'Locator-Value' is null in element data: " + elementData);
            return null;
        }

        Function<String, By> byFunction = LOCATOR_MAP.get(locatorType);
        if (byFunction != null) {
            return byFunction.apply(locatorValue);
        } else {
            System.err.println("Error: Unsupported locator type: " + locatorType);
            return null;
        }
    }
}