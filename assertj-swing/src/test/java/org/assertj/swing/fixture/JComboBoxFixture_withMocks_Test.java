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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JList;

import org.assertj.swing.cell.JComboBoxCellReader;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JComboBoxDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxFixture}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxFixture_withMocks_Test {
  private JComboBoxFixture fixture;

  @Before
  public void setUp() {
    fixture = new JComboBoxFixture(mock(Robot.class), mock(JComboBox.class));
    fixture.replaceDriverWith(mock(JComboBoxDriver.class));
  }

  @Test
  public void should_Return_Contents_Using_Driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    String[] contents = { "One", "Two", "Three" };
    when(driver.contentsOf(target)).thenReturn(contents);
    assertThat(fixture.contents()).isSameAs(contents);
    verify(driver).contentsOf(target);
  }

  @Test
  public void should_Call_ClearSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.clearSelection()).isSameAs(fixture);
    verify(fixture.driver()).clearSelection(fixture.target());
  }

  @Test
  public void should_Call_SelectItem_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.selectItem(6)).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), 6);
  }

  @Test
  public void should_Call_SelectItem_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.selectItem("Six")).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), "Six");
  }

  @Test
  public void should_Call_SelectItem_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.selectItem(pattern)).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), pattern);
  }

  @Test
  public void should_Return_Value_At_Index_Using_Driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    when(driver.value(target, 6)).thenReturn("Six");
    assertThat(fixture.valueAt(6)).isEqualTo("Six");
    verify(driver).value(target, 6);
  }

  @Test
  public void should_Call_RequireSelection_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.requireSelection(6)).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), 6);
  }

  @Test
  public void should_Call_RequireSelection_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.requireSelection("Six")).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), "Six");
  }

  @Test
  public void should_Call_RequireSelection_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireSelection(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), pattern);
  }

  @Test
  public void should_Call_RequireNoSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(fixture.driver()).requireNoSelection(fixture.target());
  }

  @Test
  public void should_Call_RequireItemCount_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.requireItemCount(6)).isSameAs(fixture);
    verify(fixture.driver()).requireItemCount(fixture.target(), 6);
  }

  @Test
  public void should_Return_Selection_Using_Driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    when(driver.selectedItemOf(target)).thenReturn("Six");
    assertThat(fixture.selectedItem()).isEqualTo("Six");
    verify(driver).selectedItemOf(target);
  }

  @Test
  public void should_Call_RequireEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireEditable(fixture.target());
  }

  @Test
  public void should_Call_RequireNotEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireNotEditable(fixture.target());
  }

  @Test
  public void should_Call_ReplaceText_In_Driver_And_Return_Self() {
    assertThat(fixture.replaceText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).replaceText(fixture.target(), "Hello");
  }

  @Test
  public void should_Call_SelectAllText_In_Driver_And_Return_Self() {
    assertThat(fixture.selectAllText()).isSameAs(fixture);
    verify(fixture.driver()).selectAllText(fixture.target());
  }

  @Test
  public void should_Call_EnterText_In_Driver_And_Return_Self() {
    assertThat(fixture.enterText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterText(fixture.target(), "Hello");
  }

  @Test
  public void should_Return_List_Using_Driver() {
    JComboBoxDriver driver = fixture.driver();
    JList list = mock(JList.class);
    when(driver.dropDownList()).thenReturn(list);
    assertThat(fixture.list()).isSameAs(list);
    verify(driver).dropDownList();
  }

  @Test
  public void should_Call_ReplaceCellReader_In_Driver() {
    JComboBoxCellReader cellReader = mock(JComboBoxCellReader.class);
    fixture.replaceCellReader(cellReader);
    verify(fixture.driver()).replaceCellReader(cellReader);
  }
}
