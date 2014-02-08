package org.assertj.swing.test.assertion;

import java.awt.image.BufferedImage;

import org.assertj.core.api.Assertions;

public class TestAssertions extends Assertions {
  public static ImageAssert assertThat(BufferedImage actual) {
    return new ImageAssert(actual);
  }
}
