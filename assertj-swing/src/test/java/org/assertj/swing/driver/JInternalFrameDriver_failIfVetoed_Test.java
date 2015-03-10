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
package org.assertj.swing.driver;

import static org.assertj.swing.driver.JInternalFrameAction.MAXIMIZE;

import java.beans.PropertyVetoException;

import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.exception.UnexpectedException;
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
  public void should_Throw_Error_If_SetProperty_Is_Vetoed() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    thrown.expect(ActionFailedException.class, action.name);
    thrown.expectMessageToContain("was vetoed: <Test>");
    driver.failIfVetoed(internalFrame, action, new UnexpectedException(vetoed));
  }

  @Test
  public void should_Not_Throw_Error_If_SetProperty_Is_Not_Vetoed() {
    driver.failIfVetoed(internalFrame, MAXIMIZE, new UnexpectedException(new Exception()));
  }
}
