package io.github.kgress.scaffold.webelements;

import io.github.kgress.scaffold.BaseWebElement;
import org.openqa.selenium.By;

/**
 * Scaffold's strongly typed interpretation of a radio element.
 *
 * A RadioWebElement and a CheckBoxElement can sometimes be interchangeable. In the future, we should distinguish these
 * by adding functionality that is specific to their concept.
 */
public class RadioWebElement extends BaseClickableWebElement {

    /**
     * Creates a new {@link RadioWebElement}. It is highly recommended using {@link By#cssSelector(String)} over
     * another method, such as {@link By#xpath(String)}, in almost all cases as it can be less flaky and less reliant
     * on DOM hierarchy.
     *
     * @see BaseWebElement#BaseWebElement(String)
     * @param cssSelector   the value of the {@link By#cssSelector(String)}
     */
    public RadioWebElement(String cssSelector) {
        super(cssSelector);
    }

    /**
     * Use this constructor when you'd like to locate an element with a {@link By} method different from
     * {@link By#cssSelector(String)}. We strongly recommend using {@link #RadioWebElement(String cssSelector)}
     * in almost all cases.
     *
     * @see BaseWebElement#BaseWebElement(By)
     * @param by    the {@link By} locator
     */
    public RadioWebElement(By by) {
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
    public RadioWebElement(By by, By parentBy) {
        super(by, parentBy);
    }

    /**
     * Indicates if an element is selected.
     *
     @return the result as {@link boolean}
     */
    public boolean isSelected() {
        return getRawWebElement().isSelected();
    }
}
