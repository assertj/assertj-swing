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
package org.assertj.swing.junit.ant;

import static java.util.Calendar.AM;
import static java.util.Calendar.AM_PM;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MILLISECOND;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link TimestampFormatter#format(java.util.Date)}</code>.
 * 
 * @author Alex Ruiz
 */
public class TimestampFormatter_format_Test {

  public static TimestampFormatter formatter;

  @BeforeClass
  public static void setUpOnce() {
    formatter = new TimestampFormatter();
  }

  @Test
  public void should_Format_Date_Using_ISO8601_Pattern() {
    // make sure we have the right timezone so that the expected string matches the result
    TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT"));

    String formatted = formatter.format(due().getTime());
    assertThat(formatted).isEqualTo("2009-06-13T08:06:10");
  }

  private Calendar due() {
    // June 13th 2009 08:06:10 AM
    Calendar calendar = new GregorianCalendar(2009, JUNE, 13, 8, 6, 10);
    calendar.set(MILLISECOND, 0);
    calendar.set(AM_PM, AM);
    return calendar;
  }
}
