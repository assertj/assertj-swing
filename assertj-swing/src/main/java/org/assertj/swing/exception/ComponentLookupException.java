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
package org.assertj.swing.exception;

import static java.util.Collections.unmodifiableCollection;
import static org.assertj.core.util.Lists.newArrayList;

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nonnull;

/**
 * Error thrown when looking up a component using a {@link org.assertj.swing.core.ComponentFinder}.
 *
 * @author Alex Ruiz
 */
public class ComponentLookupException extends RuntimeException {
  /** Generated serial version UID. */
  private static final long serialVersionUID = 8565803426992774170L;
  private final Collection<Component> found = newArrayList();

  /**
   * Creates a new {@link ComponentLookupException}.
   *
   * @param message the detail message.
   * @param found the AWT and Swing {@code Component}s found by the lookup (if any).
   */
  public ComponentLookupException(@Nonnull String message, @Nonnull Collection<? extends Component> found) {
    this(message);
    this.found.addAll(found);
  }

  /**
   * Creates a new {@link ComponentLookupException}.
   *
   * @param message the detail message.
   */
  public ComponentLookupException(@Nonnull String message) {
    super(message);
  }

  /**
   * @return the AWT and Swing {@code Component}s found by the lookup (if any).
   */
  public final @Nonnull Collection<Component> found() {
    return unmodifiableCollection(found);
  }
}
