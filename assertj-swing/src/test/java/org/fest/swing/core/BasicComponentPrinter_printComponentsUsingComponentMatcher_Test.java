/*
 * Created on Jul 25, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.format.Formatting.format;

import org.junit.Test;

/**
 * Tests for {@link BasicComponentPrinter#printComponents(java.io.PrintStream, ComponentMatcher)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicComponentPrinter_printComponentsUsingComponentMatcher_Test extends BasicComponentPrinter_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_OutputStream_is_null() {
    printer.printComponents(null, new NameMatcher("button1"));
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_ComponentMatcher_is_null() {
    ComponentMatcher matcher = null;
    printer.printComponents(out, matcher);
  }

  @Test
  public void should_print_all_matching_Components() {
    printer.printComponents(out, new NameMatcher("button1"));
    assertThat(out.printed()).containsOnly(format(windowOne.button));
  }
}
