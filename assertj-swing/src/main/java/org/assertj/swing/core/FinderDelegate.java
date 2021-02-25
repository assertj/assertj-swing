/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.core;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.hierarchy.ComponentHierarchy;

/**
 * Finds all the AWT and Swing {@code Components} in a {@link ComponentHierarchy} that match the search criteria
 * specified in a {@link ComponentMatcher}.
 *
 * @author Alex Ruiz
 */
final class FinderDelegate {
  @RunsInEDT
  @Nonnull
  Collection<Component> find(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
    Set<Component> found = newLinkedHashSet();
    execute(() -> {
      for (Component c : rootsOf(h)) {
        find(h, m, checkNotNull(c), found);
      }
    });
    return found;
  }

  @RunsInEDT
  private void find(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m, @Nonnull Component root,
                    @Nonnull Set<Component> found) {
    for (Component c : childrenOfComponent(root, h)) {
      find(h, m, checkNotNull(c), found);
    }
    if (isMatching(root, m)) {
      found.add(root);
    }
  }

  @RunsInEDT
  @Nonnull private static Collection<Component> childrenOfComponent(final @Nonnull Component c,
                                                                    final @Nonnull ComponentHierarchy h) {
    Collection<Component> children = h.childrenOf(c);
    return checkNotNull(children);
  }

  @RunsInEDT
  private static boolean isMatching(@Nonnull final Component c, @Nonnull final ComponentMatcher m) {
    Boolean matching = m.matches(c);
    return checkNotNull(matching);
  }

  @RunsInEDT
  @Nonnull
  <T extends Component> Collection<T> find(@Nonnull ComponentHierarchy h, @Nonnull GenericTypeMatcher<T> m) {
    Set<T> found = newLinkedHashSet();
    execute(() -> {
      for (Component c : rootsOf(h)) {
        find(h, m, checkNotNull(c), found);
      }
    });
    return found;
  }

  @RunsInEDT
  @Nonnull private static Collection<? extends Component> rootsOf(final @Nonnull ComponentHierarchy h) {
    return checkNotNull(h.roots());
  }

  @RunsInEDT
  private <T extends Component> void find(@Nonnull ComponentHierarchy h, @Nonnull GenericTypeMatcher<T> m,
                                          @Nonnull Component root, Set<T> found) {
    for (Component c : childrenOfComponent(root, h)) {
      find(h, m, checkNotNull(c), found);
    }
    if (isMatching(root, m)) {
      found.add(m.supportedType().cast(root));
    }
  }

  @RunsInEDT
  private static <T extends Component> boolean isMatching(final @Nonnull Component c,
                                                          final @Nonnull GenericTypeMatcher<T> m) {
    Boolean matching = m.matches(c);
    return checkNotNull(matching);
  }
}
