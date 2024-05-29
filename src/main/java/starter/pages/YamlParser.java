package starter.pages;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class YamlParser {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\biboy\\IdeaProjects\\serenity\\src\\main\\java\\starter\\pages\\archivo.yaml";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(fis);
            // Iterar sobre los elementos en el YAML y encontrarlos en la página
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                List<Map<String, String>> elements = (List<Map<String, String>>) entry.getValue();

                for (Map<String, String> elementData : elements) {
                    System.out.println(elementData);
//                    WebElement element = findElement(driver, elementData);
//                    if (element != null) {
//                        System.out.println("Elemento " + elementData.get("Name") + " encontrado: " + element.getText());
//                    } else {
//                        System.out.println("No se pudo encontrar el elemento " + elementData.get("Name"));
//                    }
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
    }
}