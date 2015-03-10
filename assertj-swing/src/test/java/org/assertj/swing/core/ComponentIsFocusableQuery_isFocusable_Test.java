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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link ComponentIsFocusableQuery#isFocusable(Component)}.
 * 
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class ComponentIsFocusableQuery_isFocusable_Test extends EDTSafeTestCase {
  private final boolean isFocusable;
  private Component component;

  @Parameters
  public static @Nonnull Collection<Object[]> isFocusable() {
    return newArrayList(BooleanProvider.booleans());
  }

  public ComponentIsFocusableQuery_isFocusable_Test(boolean isFocusable) {
    this.isFocusable = isFocusable;
  }

  @Before
  public void setUp() {
    component = mock(Component.class);
  }

  @Test
  public void should_Return_Component_Is_Focusable() {
    when(component.isFocusable()).thenReturn(isFocusable);
    assertThat(ComponentIsFocusableQuery.isFocusable(component)).isEqualTo(isFocusable);
  }
}
