package org.assertj.swing.core;

import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;

/**
 * Base case for tests for {@link BasicRobot} that test clicks.
 * 
 * @author Christian Rösch
 */
public class BasicRobot_ClickTestCase extends BasicRobot_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();
}
