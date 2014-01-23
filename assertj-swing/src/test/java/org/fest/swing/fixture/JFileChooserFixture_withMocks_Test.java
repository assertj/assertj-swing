/*
 * Created on Jul 9, 2007
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JFileChooserDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserFixture_withMocks_Test {
  private JFileChooserDriver driver;
  private JFileChooser target;

  private JFileChooserFixture fixture;

  @Before
  public void setUp() {
    fixture = new JFileChooserFixture(mock(Robot.class), mock(JFileChooser.class));
    fixture.replaceDriverWith(mock(JFileChooserDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_call_approve_in_driver() {
    fixture.approve();
    verify(driver).clickApproveButton(target);
  }

  @Test
  public void should_return_approve_button_using_driver() {
    JButton approveButton = mock(JButton.class);
    when(driver.approveButton(target)).thenReturn(approveButton);
    JButtonFixture buttonFixture = fixture.approveButton();
    assertThat(buttonFixture.target()).isSameAs(approveButton);
    verify(driver).approveButton(target);
  }

  @Test
  public void should_call_cancel_in_driver() {
    fixture.cancel();
    verify(driver).clickCancelButton(target);
  }

  @Test
  public void should_return_cancel_button_using_driver() {
    JButton cancelButton = mock(JButton.class);
    when(driver.cancelButton(target)).thenReturn(cancelButton);
    JButtonFixture buttonFixture = fixture.cancelButton();
    assertThat(buttonFixture.target()).isSameAs(cancelButton);
    verify(driver).cancelButton(target);
  }

  @Test
  public void should_return_file_name_textBox_using_driver() {
    JTextField fileNameTextBox = mock(JTextField.class);
    when(driver.fileNameTextBox(target)).thenReturn(fileNameTextBox);
    JTextComponentFixture textComponentFixture = fixture.fileNameTextBox();
    assertThat(textComponentFixture.target()).isSameAs(fileNameTextBox);
    verify(driver).fileNameTextBox(target);
  }

  @Test
  public void should_call_selectFile_in_driver_and_return_self() {
    File file = mock(File.class);
    assertThat(fixture.selectFile(file)).isSameAs(fixture);
    verify(driver).selectFile(target, file);
  }

  @Test
  public void should_call_selectFiles_in_driver_and_return_self() {
    File[] files = { mock(File.class) };
    assertThat(fixture.selectFiles(files)).isSameAs(fixture);
    verify(driver).selectFiles(target, files);
  }

  @Test
  public void should_call_setCurrentDirectory_in_driver_and_return_self() {
    File dir = mock(File.class);
    assertThat(fixture.setCurrentDirectory(dir)).isSameAs(fixture);
    verify(driver).setCurrentDirectory(target, dir);
  }
}
