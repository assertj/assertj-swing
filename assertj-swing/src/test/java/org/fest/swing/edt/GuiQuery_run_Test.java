/*
 * Created on Jul 29, 2008
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
package org.fest.swing.edt;

import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link GuiQuery#run()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class GuiQuery_run_Test {
  @Test(expected = ActionFailedException.class)
  public void should_throw_error_if_not_called_in_EDT() {
    new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() throws Throwable {
        return null;
      }
    }.run();
  }
}
