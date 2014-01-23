/*
 * Created on Oct 21, 2008
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
package org.fest.swing.driver;

import java.awt.Component;

import javax.annotation.Nullable;

/**
 * Implementation of {@link CellRendererReader} that returns a pre-set value as the value read from a cell renderer
 * component.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CellRendererComponentReaderStub implements CellRendererReader {
  private final String valueToReturn;
  private Component cellRendererComponent;

  /**
   * Creates a new {@link CellRendererComponentReaderStub}.
   *
   * @param valueToReturn the value to return from the method {@code valueFrom}.
   */
  public CellRendererComponentReaderStub(String valueToReturn) {
    this.valueToReturn = valueToReturn;
  }

  @Override
  public String valueFrom(@Nullable Component c) {
    this.cellRendererComponent = c;
    return valueToReturn;
  }

  /**
   * Returns the cell renderer component that was passed to {@link #valueFrom(Component)}.
   *
   * @return the cell renderer component that whose value was read.
   */
  public Component cellRendererComponent() {
    return cellRendererComponent;
  }
}
