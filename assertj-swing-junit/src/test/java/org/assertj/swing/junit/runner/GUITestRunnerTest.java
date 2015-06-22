package org.assertj.swing.junit.runner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GUITestRunnerTest {

  private String javaAwtHeadlessBefore;

  @Before
  public void saveProperty() {
    javaAwtHeadlessBefore = System.getProperty("java.awt.headless");
  }

  @After
  public void resetProperty() {
    if (javaAwtHeadlessBefore != null) {
      System.setProperty("java.awt.headless", javaAwtHeadlessBefore);
    }
  }

  @SuppressWarnings("unused")
  @Test
  public void should_Throw_No_Error_When_In_Headless_Environment() throws Exception {
    System.setProperty("java.awt.headless", "true");

    new GUITestRunner(ClassUnderTest.class);
  }

  public static class ClassUnderTest {
    @Test
    public void test() {
      // do nothing
    }
  }
}