package starter.pages;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static starter.tasks.TextUtils.formatText;

public class YamlParser {

    private final String directoryPath;

    public YamlParser() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        directoryPath = environmentVariables.getProperty("serenity.yaml.directory.path");
    }

    public List<Map<String, String>> getSelectorsFromFile(String filePath) {
        List<Map<String, String>> selectors = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(fis);

            data.values().forEach(value -> {
                List<Map<String, Map<String, String>>> elements = (List<Map<String, Map<String, String>>>) value;
                elements.forEach(elementMap -> elementMap.values().forEach(selectors::add));
            });
        } catch (IOException | YAMLException e) {
            System.err.println("Error reading/parsing file: " + filePath + " - " + e.getMessage());
        }
        return selectors;
    }

    public List<Map<String, String>> getAllSelectors() {
        List<Map<String, String>> allSelectors = new ArrayList<>();
        try {
            Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".yaml"))
                    .forEach(path -> allSelectors.addAll(getSelectorsFromFile(path.toString())));
        } catch (IOException e) {
            System.err.println("Error reading files from directory: " + directoryPath + " - " + e.getMessage());
        }
        return allSelectors;
    }
    public List<Map<String, String>> getAllSelectorsFromPage(String fileName) {
        List<Map<String, String>> allSelectors = new ArrayList<>();
        try {
            Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String fileNameWithoutExtension = path.getFileName().toString().replaceAll("\\.[^.]*$", "");
                        return fileNameWithoutExtension.equals(fileName);
                    })
                    .forEach(path -> allSelectors.addAll(getSelectorsFromFile(path.toString())));
        } catch (IOException e) {
            System.err.println("Error reading files from directory: " + directoryPath + " - " + e.getMessage());
        }
        return allSelectors;
    }

    public WebElement findElement(WebDriver driver, Map<String, String> elementData) {
        String locatorType = elementData.get("Locator-Type");
        String locatorValue = elementData.get("Locator-Value");

        if (locatorType == null || locatorValue == null) {
            System.err.println("Error: 'Locator-Type' or 'Locator-Value' is null in element data: " + elementData);
            return null;
        }

        By by;
        switch (locatorType.toUpperCase()) {
            case "ID":
                by = By.id(locatorValue);
                break;
            case "XPATH":
                by = By.xpath(locatorValue);
                break;
            case "CSS":
                by = By.cssSelector(locatorValue);
                break;
            case "NAME":
                by = By.name(locatorValue);
                break;
            case "CLASS":
                by = By.className(locatorValue);
                break;
            case "LINK":
                by = By.linkText(locatorValue);
                break;
            case "PARTIAL_LINK":
                by = By.partialLinkText(locatorValue);
                break;
            default:
                System.err.println("Error: Unsupported locator type: " + locatorType);
                return null;
        }
        return driver.findElement(by);
    }

    public static void main(String[] args) {
        String txt ="your car";
        System.out.println(formatText(txt));
        YamlParser yamlParser = new YamlParser();
        List<Map<String, String>> allSelectors = yamlParser.getAllSelectorsFromPage(formatText(txt));

        allSelectors.forEach(System.out::println);
    }
}