/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
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
