package starter.pages;

import net.serenitybdd.core.Serenity;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlParser {

    public List<Map<String, String>> getSelectors(String filePath) {
        List<Map<String, String>> selectors = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(fis);

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                List<Map<String, Map<String, String>>> elements = (List<Map<String, Map<String, String>>>) entry.getValue();
                for (Map<String, Map<String, String>> elementMap : elements) {
                    for (Map.Entry<String, Map<String, String>> elementEntry : elementMap.entrySet()) {
                        selectors.add(elementEntry.getValue());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + filePath);
            e.printStackTrace();
        } catch (YAMLException e) {
            System.out.println("Error al parsear el archivo YAML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return selectors;
    }

    public WebElement findElement(WebDriver driver, Map<String, String> elementData) {
        String locatorType = elementData.get("Locator-Type");
        String locatorValue = elementData.get("Locator-Value");

        if (locatorType == null || locatorValue == null) {
            System.out.println("Error: 'Locator-Type' o 'Locator-Value' es nulo en los datos del elemento: " + elementData);
            return null;
        }

        By by = null;
        switch (locatorType.toUpperCase()) {
            case "ID":
                by = By.id(locatorValue);
                break;
            case "NAME":
                by = By.name(locatorValue);
                break;
            case "XPATH":
                by = By.xpath(locatorValue);
                break;
            case "CSS":
                by = By.cssSelector(locatorValue);
                break;
            // Añadir más tipos de localizadores si es necesario
            default:
                System.out.println("Error: Tipo de localizador no soportado: " + locatorType);
                return null;
        }
        return driver.findElement(by);
    }

    public static void main(String[] args) {
        YamlParser parser = new YamlParser();
        String filePath = "/Users/bcnc/IdeaProjects/serenity-cucumber-page-objects-starter/src/main/java/starter/pages/archivo.yaml";

        List<Map<String, String>> selectors = parser.getSelectors(filePath);
        for (Map<String, String> selector : selectors) {
            WebElement element = parser.findElement(Serenity.getDriver(), selector);
            if (element != null) {
                System.out.println("Elemento " + selector.get("Name") + " encontrado: " + element.getText());
                System.out.println(selector);
            } else {
                System.out.println("No se pudo encontrar el elemento " + selector.get("Name"));
            }
        }
    }
}