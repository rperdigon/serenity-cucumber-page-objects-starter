package starter.pages.factory;

import org.openqa.selenium.By;

public interface SelectorFactory {
    By getSelector(String name);
}