package io.github.kgress.scaffold.webelements;

import io.github.kgress.scaffold.BaseWebElement;
import org.openqa.selenium.By;

/**
 * Scaffold's strongly typed interpretation of a checkbox element.
 */
public class CheckBoxWebElement extends BaseClickableWebElement {

    /**
     * Creates a new {@link CheckBoxWebElement}. It is highly recommended using {@link By#cssSelector(String)} over
     * another method, such as {@link By#xpath(String)}, in almost all cases as it can be less flaky and less reliant
     * on DOM hierarchy.
     *
     * @see BaseWebElement#BaseWebElement(String)
     * @param cssSelector   the value of the {@link By#cssSelector(String)}
     */
    public CheckBoxWebElement(String cssSelector) {
        super(cssSelector);
    }

    /**
     * Use this constructor when you'd like to locate an element with a {@link By} method different from
     * {@link By#cssSelector(String)}. We strongly recommend using {@link #CheckBoxWebElement(String cssSelector)}
     * in almost all cases.
     *
     * @see BaseWebElement#BaseWebElement(By)
     * @param by    the {@link By} locator
     */
    public CheckBoxWebElement(By by) {
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
    public CheckBoxWebElement(By by, By parentBy) {
        super(by, parentBy);
    }

    /**
     * Performs a check action on a checkbox element based on the value passed in.
     *
     * @param value the state in which the checkbox should be in.
     */
    public void check(boolean value) {
        if (value) {
            check();
        } else {
            uncheck();
        }
    }

    /**
     * Checks the checkbox in {@link #check(boolean)}
     */
    private void check() {
        if (!getRawWebElement().isSelected()) {
            getRawWebElement().click();
        }
    }

    /**
     * Unchecks the checkbox in {@link #check(boolean)}
     */
    private void uncheck() {
        if (getRawWebElement().isSelected()) {
            getRawWebElement().click();
        }
    }
}
