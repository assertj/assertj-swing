/*
 * Created on Aug 24, 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import static java.lang.String.valueOf;
import static java.util.logging.Logger.getAnonymousLogger;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

import java.util.logging.Logger;

import org.junit.Test;

/**
 * Tests for {@link Windows#markAsShowing(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
public class Windows_markAsShowing_Test extends Windows_TestCase {
  private static Logger logger = getAnonymousLogger();

  @Test
  public void should_mark_Window_as_showing() {
    windows.markAsShowing(window);
    assertThat(windows.isShowingButNotReady(window)).isTrue();
    int timeToPause = Windows.WINDOW_READY_DELAY * 2;
    logger.info(concat("Pausing for ", valueOf(timeToPause), " ms: waiting for window to be marked as showing"));
    pause(timeToPause);
    assertThat(windowState()).isReady();
  }
}
