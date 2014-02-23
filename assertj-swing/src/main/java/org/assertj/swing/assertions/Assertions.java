package org.assertj.swing.assertions;

import java.awt.image.BufferedImage;

import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.IterableAssert;

/**
 * Entry point for assertion methods for different data types (including swing types). Each method in this class is a
 * static factory for the type-specific assertion objects. The purpose of this class is to make test code more readable.
 * <p>
 * For example:
 * 
 * <pre>
 * int removed = employees.removeFired();
 * {@link Assertions#assertThat(int) assertThat}(removed).{@link IntegerAssert#isZero isZero}();
 * 
 * List&lt;Employee&gt; newEmployees = employees.hired(TODAY);
 * {@link Assertions#assertThat(Iterable) assertThat}(newEmployees).{@link IterableAssert#hasSize(int) hasSize}(6);
 * </pre>
 */
public class Assertions extends org.assertj.core.api.Assertions {

  /**
   * Creates a new instance of <code>{@link ImageAssert}</code>.
   * 
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static ImageAssert assertThat(BufferedImage actual) {
    return new ImageAssert(actual);
  }
}
