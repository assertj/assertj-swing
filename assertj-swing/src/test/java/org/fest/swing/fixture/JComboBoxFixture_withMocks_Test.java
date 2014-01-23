/*
 * Created on Jul 26, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JList;

import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComboBoxDriver;
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
  public void should_return_contents_using_driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    String[] contents = { "One", "Two", "Three" };
    when(driver.contentsOf(target)).thenReturn(contents);
    assertThat(fixture.contents()).isSameAs(contents);
    verify(driver).contentsOf(target);
  }

  @Test
  public void should_call_clearSelection_in_driver_and_return_self() {
    assertThat(fixture.clearSelection()).isSameAs(fixture);
    verify(fixture.driver()).clearSelection(fixture.target());
  }

  @Test
  public void should_call_selectItem_with_index_in_driver_and_return_self() {
    assertThat(fixture.selectItem(6)).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), 6);
  }

  @Test
  public void should_call_selectItem_with_text_in_driver_and_return_self() {
    assertThat(fixture.selectItem("Six")).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), "Six");
  }

  @Test
  public void should_call_selectItem_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.selectItem(pattern)).isSameAs(fixture);
    verify(fixture.driver()).selectItem(fixture.target(), pattern);
  }

  @Test
  public void should_return_value_at_index_using_driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    when(driver.value(target, 6)).thenReturn("Six");
    assertThat(fixture.valueAt(6)).isEqualTo("Six");
    verify(driver).value(target, 6);
  }

  @Test
  public void should_call_requireSelection_with_index_in_driver_and_return_self() {
    assertThat(fixture.requireSelection(6)).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), 6);
  }

  @Test
  public void should_call_requireSelection_with_text_in_driver_and_return_self() {
    assertThat(fixture.requireSelection("Six")).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), "Six");
  }

  @Test
  public void should_call_requireSelection_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireSelection(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireSelection(fixture.target(), pattern);
  }

  @Test
  public void should_call_requireNoSelection_in_driver_and_return_self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(fixture.driver()).requireNoSelection(fixture.target());
  }

  @Test
  public void should_call_requireItemCount_with_index_in_driver_and_return_self() {
    assertThat(fixture.requireItemCount(6)).isSameAs(fixture);
    verify(fixture.driver()).requireItemCount(fixture.target(), 6);
  }

  @Test
  public void should_return_selection_using_driver() {
    JComboBoxDriver driver = fixture.driver();
    JComboBox target = fixture.target();
    when(driver.selectedItemOf(target)).thenReturn("Six");
    assertThat(fixture.selectedItem()).isEqualTo("Six");
    verify(driver).selectedItemOf(target);
  }

  @Test
  public void should_call_requireEditable_in_driver_and_return_self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireEditable(fixture.target());
  }

  @Test
  public void should_call_requireNotEditable_in_driver_and_return_self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireNotEditable(fixture.target());
  }

  @Test
  public void should_call_replaceText_in_driver_and_return_self() {
    assertThat(fixture.replaceText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).replaceText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_selectAllText_in_driver_and_return_self() {
    assertThat(fixture.selectAllText()).isSameAs(fixture);
    verify(fixture.driver()).selectAllText(fixture.target());
  }

  @Test
  public void should_call_enterText_in_driver_and_return_self() {
    assertThat(fixture.enterText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterText(fixture.target(), "Hello");
  }

  @Test
  public void should_return_list_using_driver() {
    JComboBoxDriver driver = fixture.driver();
    JList list = mock(JList.class);
    when(driver.dropDownList()).thenReturn(list);
    assertThat(fixture.list()).isSameAs(list);
    verify(driver).dropDownList();
  }

  @Test
  public void should_call_replaceCellReader_in_driver() {
    JComboBoxCellReader cellReader = mock(JComboBoxCellReader.class);
    fixture.replaceCellReader(cellReader);
    verify(fixture.driver()).replaceCellReader(cellReader);
  }
}
