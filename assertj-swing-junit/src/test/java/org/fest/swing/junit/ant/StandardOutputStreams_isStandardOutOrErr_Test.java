/*
 * Created on Apr 2, 2009
 *
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link StandardOutputStreams#isStandardOutOrErr(OutputStream)}</code>.
 *
 * @author Alex Ruiz
 */
public class StandardOutputStreams_isStandardOutOrErr_Test {

  private static StandardOutputStreams streams;

  @BeforeClass
  public static void setUpOnce() {
    streams = new StandardOutputStreams();
  }

  @Test
  public void should_return_true_if_Out_is_SystemOut() {
    assertThat(streams.isStandardOutOrErr(System.out)).isTrue();
  }

  @Test
  public void should_return_true_if_Out_is_SystemErr() {
    assertThat(streams.isStandardOutOrErr(System.err)).isTrue();
  }

  @Test
  public void should_return_false_if_out_is_not_standard() {
    OutputStream out = new ByteArrayOutputStream();
    assertThat(streams.isStandardOutOrErr(out)).isFalse();
  }
}
