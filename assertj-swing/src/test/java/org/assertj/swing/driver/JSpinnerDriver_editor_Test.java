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

import org.assertj.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link JSpinnerDriver#editor(javax.swing.JSpinner)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerDriver_editor_Test extends JSpinnerDriver_TestCase {
  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_JTextComponentEditor_Not_Found() {
    setJLabelAsEditor();
    showWindow();
    driver.editor(spinner);
  }
}
