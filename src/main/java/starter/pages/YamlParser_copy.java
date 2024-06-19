package starter.pages;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class YamlParser_copy {

    private final String directoryPath;

    public YamlParser_copy() {
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

    public List<Map<String, String>> getAllSelectorsFromPage(String fileName) {
        List<Map<String, String>> allSelectors = new ArrayList<>();
        try {
            allSelectors = Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().replaceAll("\\.[^.]*$", "").equals(fileName))
                    .flatMap(path -> getSelectorsFromFile(path.toString()).stream())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading files from directory: " + directoryPath + " - " + e.getMessage());
        }
        return allSelectors;
    }

    public By findSelector(String page, String name) {
        List<Map<String,String>> selector = getAllSelectorsFromPage(page);
        return selector.stream()
                .filter(selector1 -> name.equals(selector1.get("Name")))
                .findFirst()
                .map(this::getByFromElementData)
                .orElse(null);
    }

    //public Optional<Map<String, String>> findSelector(String page, String name) {
        //List<Map<String, String>> selectors = getAllSelectorsFromPage(page);
        //return selectors.stream()
          //      .filter(selector -> name.equals(selector.get("Name")))
            //    .findFirst();

        //Optional<Map<String, String>> selector = findSelector(page, name);
        //return selector.map(this::getByFromElementData).orElse(null);
        //return null;
    //}

    public By getByFromElementData(Map<String, String> elementData) {
        String locatorType = elementData.get("Locator-Type");
        String locatorValue = elementData.get("Locator-Value");

        if (locatorType == null || locatorValue == null) {
            System.err.println("Error: 'Locator-Type' or 'Locator-Value' is null in element data: " + elementData);
            return null;
        }

        switch (locatorType.toUpperCase()) {
            case "ID":
                return By.id(locatorValue);
            case "XPATH":
                return By.xpath(locatorValue);
            case "CSS":
                return By.cssSelector(locatorValue);
            case "NAME":
                return By.name(locatorValue);
            case "CLASS":
                return By.className(locatorValue);
            case "LINK":
                return By.linkText(locatorValue);
            case "PARTIAL_LINK":
                return By.partialLinkText(locatorValue);
            default:
                System.err.println("Error: Unsupported locator type: " + locatorType);
                return null;
        }
    }

    /*
    public By findBySelector(String page, String name) {
        Optional<Map<String, String>> selector = findSelector(page, name);
        return selector.map(this::getByFromElementData).orElse(null);
    }
     */

    /*public WebElement findElementByReferenceText(WebDriver driver, String page, String name) {
        By by = findBySelector(page, name);
        return by != null ? driver.findElement(by) : null;
    }*/

    public static void main(String[] args) {
        YamlParser_copy yamlParser = new YamlParser_copy();
        String page = "Login";
        String name = "login";

        List<Map<String, String>> allSelectors = yamlParser.getAllSelectorsFromPage(page);
        allSelectors.forEach(System.out::println);

        By selector = yamlParser.findSelector(page, name);
        System.out.println(selector != null ? selector.toString() : "Selector not found.");
    }
}