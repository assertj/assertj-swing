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
package org.assertj.swing.jide.grids;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.AbstractComponentFixture;
import org.assertj.swing.jide.grids.driver.DateComboBoxDriver;

import com.jidesoft.combobox.DateComboBox;

/**
 * A Fixture for driving a DateComboBox using FEST.
 * 
 * @author Peter Murray
 */
public class DateComboBoxFixture extends
    AbstractComponentFixture<DateComboBoxFixture, DateComboBox, DateComboBoxDriver> {

  public DateComboBoxFixture(Robot robot, String comboBoxName) {
	super(DateComboBoxFixture.class, robot, comboBoxName, DateComboBox.class);
	createDriver(robot);
  }

  public DateComboBoxFixture(Robot robot, DateComboBox target) {
	super(DateComboBoxFixture.class, robot, target);
	createDriver(robot);
  }

  @Override
  protected DateComboBoxDriver createDriver(Robot robot) {
	return new DateComboBoxDriver(robot);
  }

  public Date date() {
	return driver().getSelectedDate(target());
  }

  public Calendar calendar() {
	return driver().getSelectedCalendar(target());
  }

  public DateComboBoxFixture selectToday() {
	driver().selectToday(target());
	return this;
  }

  public DateComboBoxFixture selectDate(Date d) {
	driver().selectDate(target(), d);
	return this;
  }

  public DateComboBoxFixture selectCalendar(Calendar cal) {
	driver().selectCalendar(target(), cal);
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
}