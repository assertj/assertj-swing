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

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JFileChooserDriver;
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
  public void should_Call_Approve_In_Driver() {
    fixture.approve();
    verify(driver).clickApproveButton(target);
  }

  @Test
  public void should_Return_Approve_Button_Using_Driver() {
    JButton approveButton = mock(JButton.class);
    when(driver.approveButton(target)).thenReturn(approveButton);
    JButtonFixture buttonFixture = fixture.approveButton();
    assertThat(buttonFixture.target()).isSameAs(approveButton);
    verify(driver).approveButton(target);
  }

  @Test
  public void should_Call_Cancel_In_Driver() {
    fixture.cancel();
    verify(driver).clickCancelButton(target);
  }

  @Test
  public void should_Return_Cancel_Button_Using_Driver() {
    JButton cancelButton = mock(JButton.class);
    when(driver.cancelButton(target)).thenReturn(cancelButton);
    JButtonFixture buttonFixture = fixture.cancelButton();
    assertThat(buttonFixture.target()).isSameAs(cancelButton);
    verify(driver).cancelButton(target);
  }

  @Test
  public void should_Return_File_Name_TextBox_Using_Driver() {
    JTextField fileNameTextBox = mock(JTextField.class);
    when(driver.fileNameTextBox(target)).thenReturn(fileNameTextBox);
    JTextComponentFixture textComponentFixture = fixture.fileNameTextBox();
    assertThat(textComponentFixture.target()).isSameAs(fileNameTextBox);
    verify(driver).fileNameTextBox(target);
  }

  @Test
  public void should_Call_SelectFile_In_Driver_And_Return_Self() {
    File file = mock(File.class);
    assertThat(fixture.selectFile(file)).isSameAs(fixture);
    verify(driver).selectFile(target, file);
  }

  @Test
  public void should_Call_SelectFiles_In_Driver_And_Return_Self() {
    File[] files = { mock(File.class) };
    assertThat(fixture.selectFiles(files)).isSameAs(fixture);
    verify(driver).selectFiles(target, files);
  }

  @Test
  public void should_Call_SetCurrentDirectory_In_Driver_And_Return_Self() {
    File dir = mock(File.class);
    assertThat(fixture.setCurrentDirectory(dir)).isSameAs(fixture);
    verify(driver).setCurrentDirectory(target, dir);
  }
}
