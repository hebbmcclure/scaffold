package io.github.kgress.scaffold.webelements;

import io.github.kgress.scaffold.BaseWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class represents base level clickable options and actions for elements that can be interacted with through
 * a click. Clickable elements should also inherit all properties of a {@link BaseWebElement}.
 *
 * TODO add retry logic to click actions https://github.com/kgress/scaffold/issues/90
 */
public class BaseClickableWebElement extends BaseWebElement {

    /**
     * Creates a new {@link ButtonWebElement}. It is highly recommended using {@link By#cssSelector(String)} over
     * another method, such as {@link By#xpath(String)}, in almost all cases as it can be less flaky and less reliant
     * on DOM hierarchy.
     *
     * @see BaseWebElement#BaseWebElement(String)
     * @param cssSelector   the value of the {@link By#cssSelector(String)}
     */
    public BaseClickableWebElement(String cssSelector) {
        super(cssSelector);
    }

    /**
     * Use this constructor when you'd like to locate an element with a {@link By} method different from
     * {@link By#cssSelector(String)}. We strongly recommend using {@link #BaseClickableWebElement(String)} in almost
     * all cases.
     *
     * @see BaseWebElement#BaseWebElement(By)
     * @param by    the {@link By} locator
     */
    public BaseClickableWebElement(By by) {
        super(by);
    }

    /**
     * Use this constructor when you'd like to locate an element with a child and parent {@link By} together. Useful
     * when you want a more verbose element definition in context of your websites' DOM.
     *
     * @see BaseWebElement#BaseWebElement(By, By)
     * @param by        the {@link By} locator for the child element
     * @param parentBy  the {@link By} locator for the parent element
     */
    public BaseClickableWebElement(By by, By parentBy) {
        super(by, parentBy);
    }

    /**
     * Performs a click on the given element with the following process:
     *
     * - Scrolling the element into current view
     * - Performing the click action on the element
     * - Waits for the page to load prior to proceeding
     *
     * @see WebElement#click()
     */
    public void click() {
        scrollIntoView();
        getRawWebElement().click();
        getWebElementWait().waitUntilPageIsLoaded();
    }
}
