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
package org.assertj.swing.jide.grids.driver;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JComponentDriver;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;

import com.jidesoft.combobox.DateChooserPanel;
import com.jidesoft.combobox.DateComboBox;

/**
 * A basic rudimentary Driver for the {@link com.jidesoft.combobox.DateComboBox} class.
 * 
 * @author Peter Murray
 */
public class DateComboBoxDriver extends JComponentDriver {

  public DateComboBoxDriver(Robot robot) {
	super(robot);
  }

  @RunsInCurrentThread
  public Date getSelectedDate(final DateComboBox combo) {
	GuiQuery<Date> query = new GuiQuery<Date>() {
	  @Override
	  protected Date executeInEDT() throws Throwable {
		return combo.getDate();
	  }
	};
	return GuiActionRunner.execute(query);
  }

  @RunsInCurrentThread
  public Calendar getSelectedCalendar(final DateComboBox combo) {
	GuiQuery<Calendar> query = new GuiQuery<Calendar>() {
	  @Override
	  protected Calendar executeInEDT() throws Throwable {
		return combo.getCalendar();
	  }
	};
	return GuiActionRunner.execute(query);
  }

  @RunsInCurrentThread
  public void selectToday(final DateComboBox combo) {
	showPopup(combo);
	String buttonName = combo.isTimeDisplayed() ? "Date.now" : "Date.today";

	ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
	final JButton btn = finder.findByName(buttonName, JButton.class);
	if (btn == null) {
	  throw new IllegalStateException("Could not find the today/now button to click.");
	}
	robot.click(btn);
  }

  @RunsInCurrentThread
  public void selectDate(DateComboBox combo, final Date d) {
	Date current = combo.getDate();
	if (current == null && d == null || current != null && current.equals(d)) {
	  return;
	}
	showPopup(combo);
	DateChooserPanel panel = (DateChooserPanel) combo.getPopupPanel();
	selectDateOnPanel(panel, d);
  }

  @RunsInCurrentThread
  public void selectCalendar(DateComboBox combo, final Calendar cal) {
	selectDate(combo, cal.getTime());
  }

  @RunsInCurrentThread
  private static void showPopup(final DateComboBox combo) {
	GuiTask task = new GuiTask() {
	  @Override
	  protected void executeInEDT() throws Throwable {
		if (!combo.isPopupVisible()) {
		  combo.showPopup();
		}
		assertTrue("The popup should be visible", combo.isPopupVisible());
	  }
	};
	GuiActionRunner.execute(task);
  }

  @RunsInCurrentThread
  private static void selectDateOnPanel(final DateChooserPanel panel, final Date d) {
	GuiTask task = new GuiTask() {
	  @Override
	  protected void executeInEDT() throws Throwable {
		panel.setSelectedDate(d);
	  }
	};
	GuiActionRunner.execute(task);
  }
}
