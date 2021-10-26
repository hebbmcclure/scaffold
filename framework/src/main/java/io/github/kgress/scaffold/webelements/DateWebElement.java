package io.github.kgress.scaffold.webelements;

import io.github.kgress.scaffold.BaseWebElement;
import io.github.kgress.scaffold.util.AutomationUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Scaffold's strongly typed interpretation of a date element.
 *
 * This will frequently need to be subclassed in order to work with particular javascript calendar controls, etc., but
 * the intention is to provide a simple way to put dates in fields, which can frequently be tricky.
 */
@Slf4j
public class DateWebElement extends BaseWebElement {

    // Global DateFormat to be used
    private static DateFormat dateFormat;

    // Local DateFormat which will override the global DateFormat
    private DateFormat localDateFormat;

    /**
     * Creates a new {@link DateWebElement}. It is highly recommended using {@link By#cssSelector(String)} over
     * another method, such as {@link By#xpath(String)}, in almost all cases as it can be less flaky and less reliant
     * on DOM hierarchy.
     *
     * @see BaseWebElement#BaseWebElement(String)
     * @param cssSelector   the value of the {@link By#cssSelector(String)}
     */
    public DateWebElement(String cssSelector) {
        super(cssSelector);
    }

    /**
     * Use this constructor when you'd like to locate an element with a {@link By} method different from
     * {@link By#cssSelector(String)}. We strongly recommend using {@link #DateWebElement(String cssSelector)}
     * in almost all cases.
     *
     * @see BaseWebElement#BaseWebElement(By)
     * @param by    the {@link By} locator
     */
    public DateWebElement(By by) {
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
    public DateWebElement(By by, By parentBy) {
        super(by, parentBy);
    }

    /**
     * Sets a DateFormat which will be globally used by all DateWebElements.
     *
     * @param dateFormat the {@link DateFormat} to use
     */
    public static void setGlobalDateFormat(DateFormat dateFormat) {
        DateWebElement.dateFormat = dateFormat;
    }

    /**
     * Sets a DateFormat which will be used by this DateWebElement only.
     *
     * @param dateFormat the {@link DateFormat} to use
     * @return the date as {@link DateWebElement}
     */
    public DateWebElement setDateFormat(DateFormat dateFormat) {
        this.localDateFormat = dateFormat;
        return this;
    }

    public Date getValue() {
        var value = getAttribute("value");
        Date d = null;
        if (value != null && value.length() > 0) {
            try {
                d = getDateFormat().parse(value);
            } catch (ParseException p) {
                log.error("Error parsing date: " + AutomationUtils.getStackTrace(p));
            }
        }
        return d;
    }

    /**
     * Returns the applicable DateFormat.
     *
     * @return the {@link DateFormat}
     */
    private DateFormat getDateFormat() {
        if (localDateFormat != null) {
            return localDateFormat;
        } else {
            return dateFormat;
        }
    }
}
