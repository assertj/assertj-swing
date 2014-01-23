/*
 * Created on Feb 24, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.beans.PropertyVetoException;

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.junit.Test;

/**
 * Tests for
 * {@link JInternalFrameDriver#failIfVetoed(javax.swing.JInternalFrame, JInternalFrameAction, UnexpectedException)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_failIfVetoed_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_throw_error_if_setProperty_is_vetoed() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    try {
      driver.failIfVetoed(internalFrame, action, new UnexpectedException(vetoed));
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).contains(action.name).contains("was vetoed: <Test>");
    }
  }

  @Test
  public void should_not_throw_error_if_setProperty_is_not_vetoed() {
    driver.failIfVetoed(internalFrame, MAXIMIZE, new UnexpectedException(new Exception()));
  }
}
