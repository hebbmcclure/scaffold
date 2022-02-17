package io.github.kgress.scaffold;

import io.github.kgress.scaffold.exception.ComponentException;
import io.github.kgress.scaffold.util.AutomationUtils;
import io.github.kgress.scaffold.webelements.ButtonWebElement;
import io.github.kgress.scaffold.webelements.CheckBoxWebElement;
import io.github.kgress.scaffold.webelements.DateWebElement;
import io.github.kgress.scaffold.webelements.DivWebElement;
import io.github.kgress.scaffold.webelements.DropDownWebElement;
import io.github.kgress.scaffold.webelements.ImageWebElement;
import io.github.kgress.scaffold.webelements.InputWebElement;
import io.github.kgress.scaffold.webelements.LinkWebElement;
import io.github.kgress.scaffold.webelements.RadioWebElement;
import io.github.kgress.scaffold.webelements.StaticTextWebElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class BaseComponent {

  /**
   * Builds a list of a {@link BaseComponent} class.
   *
   * @param listOfElements the list of elements to iterate through and convert to components
   * @param component      the {@link BaseComponent} class of the component we are converting the
   *                       list of elements to
   * @param <T>            the type reference for the components must extend {@link BaseComponent}
   * @param <X>            the type reference for the elements we're iterating through must extend
   *                       {@link BaseWebElement}
   * @return as a new list of components that extend {@link BaseComponent}
   */
  protected <T extends BaseComponent, X extends BaseWebElement> List<T> buildComponentList(
      List<X> listOfElements, Class<T> component) {
    // Create a new list of an object that extends BaseComponent
    var listOfComponents = new ArrayList<T>();

    /*
     Iterate through the listOfElements and create a new instance of the component, type T, to
     add to the listOfComponents that will be returned to the caller.
     */
    IntStream.range(0, listOfElements.size())
        .forEach(index -> {
          try {
            /*
             Get the CSS selector for the element in the list. This selector will become the new
             parent locator.
             */
            var elementBy = listOfElements.get(index).getBy();

            /*
            Check to see if the parentBy actually exists in this case. If there are any elements
            that have been constructed with a parent in mind, we want to make sure this parent
            isn't a xpath locator, along with the current elementBy.
             */
            var elementParentBy = listOfElements.get(index).getParentBy();

            /*
             Check to make sure the By locator for the parent is a type of CSS selector, where
             type is anything other than XPATH. Then, get the underlying locator as string.
             */
            if (elementBy instanceof By.ByXPath || elementParentBy instanceof By.ByXPath) {
              throw new ComponentException("Scaffold currently cannot build component lists using "
                  + "XPATH. Please use By locators that are a type of Css selector.");
            }
            var underlyingSelector = AutomationUtils.getUnderlyingLocatorByString(elementBy);

            /*
             Create a new locator that combines the parent (the underlyingLocator) and
             an :nth-child using the index. Because the element list starts at 0, we
             need to add 1 in order to adhere to correct CSS usage.
             */
            var fullNewSelector = String.format("%s:nth-child(%s)", underlyingSelector,
                index + 1);

            /*
             Create a new instance of the component passed in by the caller. The class
             extending off of BaseComponent should not have a constructor, otherwise this new
             instance will fail.
             */
            var componentInstance = component.getConstructor().newInstance();

            /*
             Iterate through the list of fields on the new instance of the component.
             We should only convert strong typed Scaffold elements but allow for
             additional fields, such as Strings (e.g. if Strings are being used as
             locators).
             */
            convertFieldsWithNewLocator(componentInstance, fullNewSelector);

            /*
             After the fields have been converted on the new instance of the component,
             add it to the list that we will return to the caller.
             */
            listOfComponents.add(componentInstance);
          } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
              NoSuchMethodException e) {
            throw new ComponentException(e);
          }
        });
    return listOfComponents;
  }

  /**
   * Converts {@link Field}'s from a class that extends off of {@link BaseComponent} from an
   * "inaccessible" state to "accessible." We will only modify the access of Scaffold strongly typed
   * elements. Afterwards, combines the parent and child together.
   *
   * @param componentInstance  the instance of the {@link BaseComponent}
   * @param fullParentSelector the parent selector being used as the prefix
   * @param <T>                the type reference of {@link BaseComponent}
   */
  private <T extends BaseComponent> void convertFieldsWithNewLocator(T componentInstance,
      String fullParentSelector) {
    var classFields = componentInstance.getClass().getDeclaredFields();

    Arrays.stream(classFields).forEach(field -> {
      var elementType = field.getGenericType();
      try {
        field.setAccessible(true);
        BaseWebElement convertedElement = null;
        if (elementType.getTypeName().contains("DivWebElement")) {
          convertedElement = (DivWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("ButtonWebElement")) {
          convertedElement = (ButtonWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("CheckBoxWebElement")) {
          convertedElement = (CheckBoxWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("DateWebElement")) {
          convertedElement = (DateWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("DropDownWebElement")) {
          convertedElement = (DropDownWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("ImageWebElement")) {
          convertedElement = (ImageWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("InputWebElement")) {
          convertedElement = (InputWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("LinkWebElement")) {
          convertedElement = (LinkWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("RadioWebElement")) {
          convertedElement = (RadioWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("StaticTextWebElement")) {
          convertedElement = (StaticTextWebElement) field.get(componentInstance);
        } else if (elementType.getTypeName().contains("BaseWebElement")) {
          convertedElement = (BaseWebElement) field.get(componentInstance);
        } else {
          log.debug(
              "Scaffold detected a field during component list building that is not a defined as "
                  + "a strongly typed element. Skipping modification of field.");
        }
        // Only convert the field if it's a scaffold element
        if (convertedElement != null) {
          convertField(componentInstance, convertedElement, field, fullParentSelector);
        }
        field.setAccessible(false);
      } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException |
          InstantiationException e) {
        throw new ComponentException(e);
      }
    });
  }

  /**
   * Converts a {@link Field}'s {@link By} locator from a {@link BaseComponent} class. Takes a full
   * parent selector and converted element to combine it into a fully qualified parent + child
   * {@link By} locator.
   *
   * @param componentInstance  the instance of the {@link BaseComponent}
   * @param convertedElement   the converted {@link BaseWebElement}
   * @param field              the {@link Field} we are converting
   * @param fullParentSelector the fully qualified parent selector
   * @param <T>                the type reference {@link BaseComponent}
   * @param <X>                the type referece {@link BaseWebElement}
   */
  private <T extends BaseComponent, X extends BaseWebElement> void convertField(T componentInstance,
      X convertedElement, Field field, String fullParentSelector)
      throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
      InstantiationException {
    var convertedElementUnderlyingLocator = AutomationUtils.getUnderlyingLocatorByString(
        convertedElement.getBy());
    var newByLocator = By.cssSelector(
        String.format("%s %s", fullParentSelector, convertedElementUnderlyingLocator));
    var constructor = convertedElement.getClass().getConstructor(By.class);
    var newElement = constructor.newInstance(newByLocator);
    field.set(componentInstance, newElement);
  }

  /**
   * Gets the Selenium based {@link Actions} object for the current thread. This is currently not
   * strongly typed and should be added in a future update.
   * <p>
   * TODO add a strongly typed {@link Actions} object
   *
   * @return {@link Actions}
   */
  protected Actions getActions() {
    return getWebDriverWrapper().getActions();
  }

  /**
   * Gets the selenium based {@link JavascriptExecutor} for the current thread.
   *
   * @return {@link JavascriptExecutor}
   */
  protected JavascriptExecutor getJavascriptExecutor() {
    return getWebDriverWrapper().getJavascriptExecutor();
  }

  /**
   * Gets the {@link AutomationWait} from the current thread's {@link WebDriverWrapper}
   *
   * @return as {@link AutomationWait}
   */
  protected AutomationWait getAutomationWait() {
    return getWebDriverWrapper().getAutomationWait();
  }

  /**
   * Gets the {@link WebDriverWrapper} for the current thread.
   *
   * @return {@link WebDriverWrapper}
   */
  private WebDriverWrapper getWebDriverWrapper() {
    return TestContext.baseContext().getWebDriverContext().getWebDriverManager()
        .getWebDriverWrapper();
  }


}
