/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.grids;

import java.util.Calendar;
import java.util.Date;
import com.jidesoft.combobox.DateComboBox;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.core.*;
import org.fest.swing.jide.grids.driver.DateComboBoxDriver;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.fixture.*;
import org.fest.swing.timing.Timeout;

/**
 * A Fixture for driving a DateComboBox using FEST.
 * @author Peter Murray
 */
public class DateComboBoxFixture extends ComponentFixture<DateComboBox> {

  private DateComboBoxDriver _driver;

  public DateComboBoxFixture(Robot robot, String comboBoxName) {
    super(robot, comboBoxName, DateComboBox.class);
    createDriver();
  }

  public DateComboBoxFixture(Robot robot, DateComboBox target) {
    super(robot, target);
    createDriver();
  }

  protected void createDriver() {
    _driver = new DateComboBoxDriver(robot);
  }

  protected ComponentDriver driver() {
    return _driver;
  }

  protected ComponentFixture<DateComboBox> pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    _driver.pressAndReleaseKey(target, keyPressInfo.keyCode(), keyPressInfo.modifiers());
    return this;
  }

  public Date date() {
    return _driver.getSelectedDate(target);
  }

  public Calendar calendar() {
    return _driver.getSelectedCalendar(target);
  }

  public DateComboBoxFixture selectToday() {
    _driver.selectToday(target);
    return this;
  }

//  public DateComboBoxFixture selectNow() {
//    _driver.selectToday(target);
//    return this;
//  }

  public DateComboBoxFixture selectDate(Date d) {
    _driver.selectDate(target, d);
    return this;
  }

  public DateComboBoxFixture selectCalendar(Calendar cal) {
    _driver.selectCalendar(target, cal);
    return this;
  }

  public DateComboBoxFixture requireDate(Date d) {
    Date currentVal = date();
    assertThat(currentVal).isEqualTo(d);
    return this;
  }

  public DateComboBoxFixture requireCalendar(Calendar cal) {
    Calendar currentVal = calendar();
    assertThat(currentVal).isEqualTo(cal);
    return this;
  }

  public DateComboBoxFixture click() {
    _driver.click(target);
    return this;
  }

  public DateComboBoxFixture click(MouseButton mouseButton) {
    _driver.click(target, mouseButton);
    return this;
  }

  public DateComboBoxFixture click(MouseClickInfo mouseClickInfo) {
    _driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  public DateComboBoxFixture doubleClick() {
    _driver.doubleClick(target);
    return this;
  }

  public DateComboBoxFixture focus() {
    _driver.focus(target);
    return this;
  }

  public DateComboBoxFixture pressAndReleaseKeys(int... ints) {
    _driver.pressAndReleaseKeys(target, ints);
    return this;
  }

  public DateComboBoxFixture pressKey(int i) {
    _driver.pressKey(target, i);
    return this;
  }

  public DateComboBoxFixture releaseKey(int i) {
    _driver.releaseKey(target, i);
    return this;
  }

  public DateComboBoxFixture requireDisabled() {
    _driver.requireDisabled(target);
    return this;
  }

  public DateComboBoxFixture requireEnabled() {
    _driver.requireEnabled(target);
    return this;
  }

  public DateComboBoxFixture requireNotVisible() {
    _driver.requireNotVisible(target);
    return this;
  }

  public DateComboBoxFixture requireVisible() {
    _driver.requireVisible(target);
    return this;
  }

  public DateComboBoxFixture rightClick() {
    _driver.rightClick(target);
    return this;
  }

  protected ComponentFixture<DateComboBox> requireEnabled(Timeout timeout) {
    _driver.requireEnabled(target, timeout);
    return this;
  }
}