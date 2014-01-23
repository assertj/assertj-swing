/*
 * Created on Oct 20, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Component;

import org.junit.Test;

/**
 * Tests for {@link ExistingHierarchy#contains(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy_contains_Test extends ExistingHierarchy_TestCase {
  @Test
  public void should_always_return_true() {
    Component component = textField().createNew();
    assertThat(hierarchy.contains(component)).isTrue();
  }
}
