package io.github.kgress.scaffold.webelements;

import io.github.kgress.scaffold.BaseWebElement;
import org.openqa.selenium.By;

/**
 * Scaffold's strongly typed interpretation of a link element.
 */
public class LinkWebElement extends BaseClickableWebElement {

    /**
     * Creates a new {@link LinkWebElement}. It is highly recommended using {@link By#cssSelector(String)} over
     * another method, such as {@link By#xpath(String)}, in almost all cases as it can be less flaky and less reliant
     * on DOM hierarchy.
     *
     * @see BaseWebElement#BaseWebElement(String)
     * @param cssSelector   the value of the {@link By#cssSelector(String)}
     */
    public LinkWebElement(String cssSelector) {
        super(cssSelector);
    }

    /**
     * Use this constructor when you'd like to locate an element with a {@link By} method different from
     * {@link By#cssSelector(String)}. We strongly recommend using {@link #LinkWebElement(String cssSelector)}
     * in almost all cases.
     *
     * @see BaseWebElement#BaseWebElement(By)
     * @param by    the {@link By} locator
     */
    public LinkWebElement(By by) {
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
    public LinkWebElement(By by, By parentBy) {
        super(by, parentBy);
    }

    /**
     * Returns the link text as rendered in the UI
     *
     * @return  the link as {@link String}
     */
    public String getLinkText() {
        return getRawWebElement().getText();
    }

    /**
     * Returns the link href (the destination URL)
     *
     * @return  the link's URL as {@link String}
     */
    public String getLinkHref() {
        return getRawWebElement().getAttribute("href");
    }
}
