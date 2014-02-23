package org.assertj.swing.internal.assertions;

import static org.assertj.core.data.Offset.offset;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.mockito.Mockito.spy;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.data.Offset;
import org.assertj.core.internal.Failures;
import org.assertj.swing.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

/**
 * Base class for {@link Images} unit tests
 * <p>
 * Is in <code>org.fest.assertions.internal</code> package to be able to set {@link Images} attributes appropriately.
 * 
 * @author Joel Costigliola
 * 
 */
public class ImagesBaseTest {

  @Rule
  public ExpectedException thrown = none();

  protected BufferedImage actual;
  protected Offset<Integer> offset;
  protected Failures failures;
  protected Images images;

  @Before
  public void setUp() {
    failures = spy(Failures.instance());
    images = new Images();
    images.failures = failures;
    actual = fivePixelBlueImage();
    offset = offset(0);
  }

  protected Dimension sizeOf(BufferedImage actual) {
    return Images.sizeOf(actual);
  }

  private static final AssertionInfo ASSERTION_INFO = new WritableAssertionInfo();

  protected AssertionInfo someInfo() {
    return ASSERTION_INFO;
  }
}