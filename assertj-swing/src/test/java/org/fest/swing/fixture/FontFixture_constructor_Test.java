/*
 * Created on Apr 16, 2008
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
package org.fest.swing.fixture;

import java.awt.Font;

import org.junit.Test;

/**
 * Tests for {@link FontFixture#FontFixture(Font)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture_constructor_Test {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_font_is_null() {
    new FontFixture(null);
  }
}
