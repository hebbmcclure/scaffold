package io.github.kgress.scaffold.webelement;

import io.github.kgress.scaffold.BaseUnitTest;
import io.github.kgress.scaffold.MockBaseWebElement;
import io.github.kgress.scaffold.SharedTestVariables;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BaseWebElementTests extends BaseUnitTest {

    // For testing non findElement or findElements methods
    private final TestBaseWebElement elementByCssSelector = new TestBaseWebElement(SharedTestVariables.CSS_SELECTOR1);
    private final TestBaseWebElement elementByClass = 
            new TestBaseWebElement(By.className(SharedTestVariables.CLASS_NAME));

    // For testing findElement or findElements methods
    private final MockBaseWebElement parentBaseWebElementByCss = new MockBaseWebElement(
            SharedTestVariables.MOCK_PARENT_ELEMENT_SELECTOR);
    private final MockBaseWebElement parentBaseWebElementByClass = new MockBaseWebElement(
            By.className(SharedTestVariables.CLASS_NAME));
    private final MockBaseWebElement parentBaseWebElementBy = new MockBaseWebElement(
            By.cssSelector(SharedTestVariables.MOCK_PARENT_ELEMENT_SELECTOR));
    private final MockBaseWebElement parentBaseWebElementByWithParent = new MockBaseWebElement(
            By.cssSelector(SharedTestVariables.MOCK_PARENT_ELEMENT_SELECTOR),
            By.cssSelector(SharedTestVariables.MOCK_CHILD_ELEMENT_SELECTOR));
    private final By expectedByCssSelector = By.cssSelector(SharedTestVariables.MOCK_PARENT_ELEMENT_SELECTOR);
    private final By expectedByClassName = By.className(SharedTestVariables.CLASS_NAME);
    private final By expectedCombinedBy = By.cssSelector(SharedTestVariables.EXPECTED_COMBINED_SELECTOR);
    private final By expectedBy = By.cssSelector(SharedTestVariables.CSS_SELECTOR1);
    private MockBaseWebElement foundElement;

    @Test
    public void testIsEnabled_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.isEnabled()).thenReturn(true);
        assertTrue(elementByCssSelector.isEnabled());
    }

    @Test
    public void testIsDisabled_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.isEnabled()).thenReturn(false);
        assertFalse(elementByCssSelector.isEnabled());
    }

    @Test
    public void testIsDisplayed_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.isDisplayed()).thenReturn(true);
        assertTrue(elementByCssSelector.isDisplayed());
    }

    @Test
    public void testIsNotDisplayed_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.isDisplayed()).thenReturn(false);
        assertFalse(elementByCssSelector.isDisplayed());
    }

    @Test
    public void testIsActive_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.ACTIVE_CLASS_NAME);
        assertTrue(elementByCssSelector.isActive());
    }

    @Test
    public void testIsNotActive_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertFalse(elementByCssSelector.isActive());
    }

    @Test
    public void testHasClass_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertTrue(elementByCssSelector.hasClass(SharedTestVariables.CLASS_NAME));
    }

    @Test
    public void testDoesNotHaveClass_byCss() {
        setBaseWhen(elementByCssSelector);
        var notExpectingText = "NOT HERE <.< >.>";
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertFalse(elementByCssSelector.hasClass(notExpectingText));
    }

    @Test
    public void testGetAttribute_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertEquals(SharedTestVariables.CLASS_NAME,
                elementByCssSelector.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE));
    }

    @Test
    public void testGetText_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getText()).thenReturn(SharedTestVariables.TEXT_1);
        assertEquals(SharedTestVariables.TEXT_1, elementByCssSelector.getText());
    }

    @Test
    public void testGetTagName_byCss() {
        setBaseWhen(elementByCssSelector);
        when(mockRawWebElement.getTagName()).thenReturn(SharedTestVariables.TAG_NAME_1);
        assertEquals(SharedTestVariables.TAG_NAME_1, elementByCssSelector.getTagName());
    }

    @Test
    public void testGetLocation_byCss() {
        setBaseWhen(elementByCssSelector);
        var testPoint = new Point(1, 1);
        when(mockRawWebElement.getLocation()).thenReturn(testPoint);
        assertEquals(testPoint, elementByCssSelector.getLocation());
    }

    @Test
    public void testGetSize_byCss() {
        setBaseWhen(elementByCssSelector);
        var testDimension = new Dimension(1,1);
        when(mockRawWebElement.getSize()).thenReturn(testDimension);
        assertEquals(testDimension, elementByCssSelector.getSize());
    }

    @Test
    public void testGetRect_byCss() {
        setBaseWhen(elementByCssSelector);
        var testRectangle = new Rectangle(1, 1, 1, 1);
        when(mockRawWebElement.getRect()).thenReturn(testRectangle);
        assertEquals(testRectangle, elementByCssSelector.getRect());
    }

    @Test
    public void testGetCssValue_byCss() {
        setBaseWhen(elementByCssSelector);
        var testCssProperty = "testProperty";
        var expectedTestCssValue = "Dagobah";
        when(mockRawWebElement.getCssValue(testCssProperty)).thenReturn(expectedTestCssValue);
        assertEquals(expectedTestCssValue, elementByCssSelector.getCssValue(testCssProperty));
    }

    @Test
    public void testGetRawWebElement_byCss_success() {
        setBaseWhen(elementByCssSelector);
        assertEquals(mockRawWebElement, elementByCssSelector.getRawWebElement());
    }

    @Test
    public void testGetRawWebElement_byCss_fail() {
        setBaseWhen(elementByCssSelector);
        when(elementByCssSelector.getRawWebElement()).thenThrow(TimeoutException.class);
        assertThrows(TimeoutException.class, elementByCssSelector::getRawWebElement);
    }

    @Test
    public void testGetRawParentWebElement_byCss_success() {
        setBaseWhen(elementByCssSelector);
        setWhenGetRawParentElementSucceed();
        assertEquals(mockParentRawWebElement, elementByCssSelector.getRawParentWebElement());
    }

    @Test
    public void testGetRawParentWebElement_byCss_fail() {
        setBaseWhen(elementByCssSelector);
        setWhenGetRawParentElementFail();
        assertThrows(TimeoutException.class, elementByCssSelector::getRawParentWebElement);
    }

    @Test
    public void testScrollIntoView_byCss_success() {
        setBaseWhen(elementByCssSelector);
        setWhenScrollIntoViewSucceed();
        assertEquals(mockRawWebElement, elementByCssSelector.scrollIntoView());
    }

    @Test
    public void testScrollIntoView_byCss_fail() {
        setBaseWhen(elementByCssSelector);
        setWhenScrollIntoViewFail();
        assertThrows(TimeoutException.class, elementByCssSelector::scrollIntoView);
    }

    @Test
    public void testIsEnabled_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.isEnabled()).thenReturn(true);
        assertTrue(elementByClass.isEnabled());
    }

    @Test
    public void testIsDisabled_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.isEnabled()).thenReturn(false);
        assertFalse(elementByClass.isEnabled());
    }

    @Test
    public void testIsDisplayed_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.isDisplayed()).thenReturn(true);
        assertTrue(elementByClass.isDisplayed());
    }

    @Test
    public void testIsNotDisplayed_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.isDisplayed()).thenReturn(false);
        assertFalse(elementByClass.isDisplayed());
    }

    @Test
    public void testIsActive_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.ACTIVE_CLASS_NAME);
        assertTrue(elementByClass.isActive());
    }

    @Test
    public void testIsNotActive_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertFalse(elementByClass.isActive());
    }

    @Test
    public void testHasClass_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertTrue(elementByClass.hasClass(SharedTestVariables.CLASS_NAME));
    }

    @Test
    public void testDoesNotHaveClass_byClass() {
        setBaseWhen(elementByClass);
        var notExpectingText = "NOT HERE <.< >.>";
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertFalse(elementByClass.hasClass(notExpectingText));
    }

    @Test
    public void testGetAttribute_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE))
                .thenReturn(SharedTestVariables.CLASS_NAME);
        assertEquals(SharedTestVariables.CLASS_NAME,
                elementByClass.getAttribute(SharedTestVariables.CLASS_ATTRIBUTE));
    }

    @Test
    public void testGetText_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getText()).thenReturn(SharedTestVariables.TEXT_1);
        assertEquals(SharedTestVariables.TEXT_1, elementByClass.getText());
    }

    @Test
    public void testGetTagName_byClass() {
        setBaseWhen(elementByClass);
        when(mockRawWebElement.getTagName()).thenReturn(SharedTestVariables.TAG_NAME_1);
        assertEquals(SharedTestVariables.TAG_NAME_1, elementByClass.getTagName());
    }

    @Test
    public void testGetLocation_byClass() {
        setBaseWhen(elementByClass);
        var testPoint = new Point(1, 1);
        when(mockRawWebElement.getLocation()).thenReturn(testPoint);
        assertEquals(testPoint, elementByClass.getLocation());
    }

    @Test
    public void testGetSize_byClass() {
        setBaseWhen(elementByClass);
        var testDimension = new Dimension(1,1);
        when(mockRawWebElement.getSize()).thenReturn(testDimension);
        assertEquals(testDimension, elementByClass.getSize());
    }

    @Test
    public void testGetRect_byClass() {
        setBaseWhen(elementByClass);
        var testRectangle = new Rectangle(1, 1, 1, 1);
        when(mockRawWebElement.getRect()).thenReturn(testRectangle);
        assertEquals(testRectangle, elementByClass.getRect());
    }

    @Test
    public void testGetCssValue_byClass() {
        setBaseWhen(elementByClass);
        var testCssProperty = "testProperty";
        var expectedTestCssValue = "Dagobah";
        when(mockRawWebElement.getCssValue(testCssProperty)).thenReturn(expectedTestCssValue);
        assertEquals(expectedTestCssValue, elementByClass.getCssValue(testCssProperty));
    }

    @Test
    public void testGetRawWebElement_byClass_success() {
        setBaseWhen(elementByClass);
        assertEquals(mockRawWebElement, elementByClass.getRawWebElement());
    }

    @Test
    public void testGetRawWebElement_byClass_fail() {
        setBaseWhen(elementByClass);
        when(elementByClass.getRawWebElement()).thenThrow(TimeoutException.class);
        assertThrows(TimeoutException.class, elementByClass::getRawWebElement);
    }

    @Test
    public void testGetRawParentWebElement_byClass_success() {
        setBaseWhen(elementByClass);
        setWhenGetRawParentElementSucceed();
        assertEquals(mockParentRawWebElement, elementByClass.getRawParentWebElement());
    }

    @Test
    public void testGetRawParentWebElement_byClass_fail() {
        setBaseWhen(elementByClass);
        setWhenGetRawParentElementFail();
        assertThrows(TimeoutException.class, elementByClass::getRawParentWebElement);
    }

    @Test
    public void testScrollIntoView_byClass_success() {
        setBaseWhen(elementByClass);
        setWhenScrollIntoViewSucceed();
        assertEquals(mockRawWebElement, elementByClass.scrollIntoView());
    }

    @Test
    public void testScrollIntoView_byClass_fail() {
        setBaseWhen(elementByClass);
        setWhenScrollIntoViewFail();
        assertThrows(TimeoutException.class, elementByClass::scrollIntoView);
    }

    // These tests below all use MockBaseWebElement and NOT the TestBaseWebElement from base unit test
    @Test
    public void testFindBaseWebElement_css_combinedBy() {
        foundElement = parentBaseWebElementByCss
                .findElement(MockBaseWebElement.class, SharedTestVariables.CSS_SELECTOR1);
        assertEquals(expectedCombinedBy, foundElement.getBy());
    }

    @Test
    public void testFindBaseWebElement_css_className() {
        foundElement = parentBaseWebElementByCss
                .findElement(MockBaseWebElement.class, By.className(SharedTestVariables.CLASS_NAME));
        assertEquals(expectedByClassName, foundElement.getBy());
        assertEquals(expectedByCssSelector, foundElement.getParentBy());
    }

    @Test
    public void testFindBaseWebElement_by_combinedBy() {
        foundElement = parentBaseWebElementBy
                .findElement(MockBaseWebElement.class, SharedTestVariables.CSS_SELECTOR1);
        assertEquals(expectedCombinedBy, foundElement.getBy());
    }

    @Test
    public void testFindBaseWebElement_by_className() {
        foundElement = parentBaseWebElementBy
                .findElement(MockBaseWebElement.class, By.className(SharedTestVariables.CLASS_NAME));
        assertEquals(expectedByClassName, foundElement.getBy());
        assertEquals(expectedByCssSelector, foundElement.getParentBy());
    }

    @Test
    public void testFindBaseWebElement_childByAndParentBy_combinedBy() {
        foundElement = parentBaseWebElementByWithParent
                .findElement(MockBaseWebElement.class, By.cssSelector(SharedTestVariables.CSS_SELECTOR1));
        assertEquals(expectedCombinedBy, foundElement.getBy());
    }

    @Test
    public void testFindBaseWebElement_childByAndParentBy_className() {
        foundElement = parentBaseWebElementByWithParent
                .findElement(MockBaseWebElement.class, By.className(SharedTestVariables.CLASS_NAME));
        assertEquals(expectedByClassName, foundElement.getBy());
        assertEquals(expectedByCssSelector, foundElement.getParentBy());
    }

    @Test
    public void testFindBaseWebElements_css_combinedBy() {
        List<MockBaseWebElement> foundElements;
        foundElements = parentBaseWebElementByCss
                .findElements(MockBaseWebElement.class, SharedTestVariables.CSS_SELECTOR1);
        assertEquals(2, foundElements.size());
        foundElements.forEach(element -> assertEquals(expectedCombinedBy, element.getBy()));
    }

    @Test
    public void testFindBaseWebElements_class_combinedBy() {
        List<MockBaseWebElement> foundElements;
        foundElements = parentBaseWebElementByClass
                .findElements(MockBaseWebElement.class, SharedTestVariables.CSS_SELECTOR1);
        assertEquals(2, foundElements.size());
        foundElements.forEach(element -> assertEquals(expectedBy, element.getBy()));
    }
}
