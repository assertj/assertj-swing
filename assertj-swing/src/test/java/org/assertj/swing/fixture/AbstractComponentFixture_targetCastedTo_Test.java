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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.ComponentDriver;
import org.junit.Test;

/**
 * Tests for {@link AbstractComponentFixture#targetCastedTo(Class)}.
 * 
 * @author Christian Rösch
 */
public class AbstractComponentFixture_targetCastedTo_Test {

  @Test
  public void should_Return_Object_Reference_Passed_Via_Constructor() {
    Component table = new JTable();
    ConcreteComponentFixture fixture = new ConcreteComponentFixture(table);

    assertThat(fixture.targetCastedTo(JTable.class)).isSameAs(table);
  }

  @Test
  public void should_Return_Object_Of_Type_The_Component_Is_Casted_To() {
    Component table = new JTable();
    ConcreteComponentFixture fixture = new ConcreteComponentFixture(table);

    // check that it compiles
    @SuppressWarnings("unused")
    JTable castedTarget = fixture.targetCastedTo(JTable.class);
  }

  private static class ConcreteComponentFixture extends
      AbstractComponentFixture<ConcreteComponentFixture, Component, ComponentDriver> {
    public ConcreteComponentFixture(Component component) {
      super(ConcreteComponentFixture.class, mock(Robot.class), component);
    }

    @Override
    protected @Nonnull ComponentDriver createDriver(@Nonnull Robot robot) {
      return mock(ComponentDriver.class);
    }
  }
}
