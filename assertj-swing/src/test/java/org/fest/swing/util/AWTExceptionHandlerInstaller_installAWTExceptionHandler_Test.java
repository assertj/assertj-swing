/*
 * Created on Jan 5, 2010
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
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AWTExceptionHandlerInstaller#installAWTExceptionHandler(Class, SystemPropertyWriter)}.
 *
 * @author Alex Ruiz
 */
public class AWTExceptionHandlerInstaller_installAWTExceptionHandler_Test {
  private SystemPropertyWriter writer;

  @Before
  public void setUp() {
    writer = mock(SystemPropertyWriter.class);
  }

  @Test
  public void should_install_AWT_event_handler() {
    Class<CorrectEventHandler> exceptionHandlerType = CorrectEventHandler.class;
    AWTExceptionHandlerInstaller.installAWTExceptionHandler(exceptionHandlerType, writer);
    verify(writer).updateSystemProperty("sun.awt.exception.handler", exceptionHandlerType.getName());
 }

  @Test
  public void should_throw_error_if_AWT_event_handler_type_does_not_have_default_constructor() {
    try {
      AWTExceptionHandlerInstaller.installAWTExceptionHandler(WrongEventHandler.class, writer);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("The exception handler type should have a default constructor");
    }
  }

  static class CorrectEventHandler {}

  static class WrongEventHandler {
    public WrongEventHandler(String something) {
      if (something == null) {
        return;
      }
    }
  }
}
