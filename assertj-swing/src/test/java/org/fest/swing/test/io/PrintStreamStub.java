/*
 * Created on Nov 22, 2007
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
package org.fest.swing.test.io;

import static org.fest.util.Strings.concat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Logger;

import org.fest.util.Lists;

/**
 * @author Alex Ruiz
 */
public class PrintStreamStub extends PrintStream {
  private static Logger logger = Logger.getLogger(PrintStreamStub.class.getName());

  private final List<String> printed = Lists.newArrayList();

  public PrintStreamStub() {
    super(new ByteArrayOutputStream());
  }

  @Override
  public void println(String s) {
    printed.add(s);
    logger.info(concat("printed: ", s));
  }

  public String[] printed() {
    return printed.toArray(new String[0]);
  }

  public void clearPrinted() {
    printed.clear();
  }
}
