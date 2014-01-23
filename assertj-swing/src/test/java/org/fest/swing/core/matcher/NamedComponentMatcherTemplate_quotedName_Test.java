/*
 * Created on May 1, 2009
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link NamedComponentMatcherTemplate#quotedName()}.
 *
 * @author Alex Ruiz
 */
public class NamedComponentMatcherTemplate_quotedName_Test extends NamedComponentMatcherTemplate_TestCase {
  @Before
  public final void setUp() {
    matcher = new Matcher(JLabel.class);
  }

  @Test
  public void should_not_quote_name_if_it_is_any() {
    assertThat(matcher.quotedName()).isSameAs(NamedComponentMatcherTemplate.anyValue());
  }

  @Test
  public void should_quote_name_if_it_is_not_any() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.quotedName()).isEqualTo("'hello'");
  }
}
