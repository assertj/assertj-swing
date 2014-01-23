/*
 * Created on Jun 21, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.exception;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link LocationUnavailableException#LocationUnavailableException(String, Throwable)}.
 * 
 * @author Alex Ruiz
 */
public class LocationUnavailableException_constructor_withCause_Test {
  @Test
  public void should_create_error_with_cause() {
    Throwable cause = new Throwable();
    LocationUnavailableException e = new LocationUnavailableException("A Failure", cause);
    assertThat(e.getCause()).isSameAs(cause);
  }
}
