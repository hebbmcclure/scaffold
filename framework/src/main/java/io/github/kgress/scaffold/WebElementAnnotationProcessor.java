package io.github.kgress.scaffold;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

/**
 * This class provides a framework to retrieve annotations applied at the Field level and then pass them down
 * to a child object instance in the object graph.
 */
@Slf4j
abstract class WebElementAnnotationProcessor {

  /**
   * This method looks at each field on the class and if is decorated with annotations,
   * passes the annotations to the value of the field.  This currently only applies to fields
   * that have values that derive from {@link BaseWebElement}. The {@link BaseWebElement} will evaluate the provided
   * annotations.
   */
  protected void applyFieldAnnotations() {
    final var clazz = getClass();
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      final var annotations = field.getAnnotations();
      try {
        final var fieldValue = field.get(this);
        if (fieldValue instanceof BaseWebElement) {
          ((BaseWebElement) fieldValue).evaluateAppliedAnnotations(annotations);
        }
      } catch (IllegalAccessException ex) {
        log.error(String.format("Error getting field value for field '%s'.", field.getName()), ex);
      }
    }
  }

}
