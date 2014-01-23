/*
 * Created on Jun 5, 2008
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
package org.fest.swing.core;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Sets.newLinkedHashSet;

import java.awt.Component;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.hierarchy.ComponentHierarchy;

/**
 * Finds all the AWT and Swing {@code Components} in a {@link ComponentHierarchy} that match the search criteria
 * specified in a {@link ComponentMatcher}.
 * 
 * @author Alex Ruiz
 */
final class FinderDelegate {
  @RunsInEDT
  @Nonnull Collection<Component> find(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
    Set<Component> found = newLinkedHashSet();
    for (Component c : rootsOf(h)) {
      find(h, m, checkNotNull(c), found);
    }
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
  private static @Nonnull Collection<Component> childrenOfComponent(
      final @Nonnull Component c, final @Nonnull ComponentHierarchy h) {
    Collection<Component> children = execute(new GuiQuery<Collection<Component>>() {
      @Override
      protected Collection<Component> executeInEDT() {
        return h.childrenOf(c);
      }
    });
    return checkNotNull(children);
  }

  @RunsInEDT
  private static boolean isMatching(@Nonnull final Component c, @Nonnull final ComponentMatcher m) {
    Boolean matching = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return m.matches(c);
      }
    });
    return checkNotNull(matching);
  }

  @RunsInEDT
  @Nonnull <T extends Component> Collection<T> find(@Nonnull ComponentHierarchy h, @Nonnull GenericTypeMatcher<T> m) {
    Set<T> found = newLinkedHashSet();
    for (Component c : rootsOf(h)) {
      find(h, m, checkNotNull(c), found);
    }
    return found;
  }

  @RunsInEDT
  private static @Nonnull Collection<? extends Component> rootsOf(final @Nonnull ComponentHierarchy h) {
    Collection<? extends Component> roots = execute(new GuiQuery<Collection<? extends Component>>() {
      @Override
      protected Collection<? extends Component> executeInEDT() {
        return h.roots();
      }
    });
    return checkNotNull(roots);
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
    Boolean matching = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return m.matches(c);
      }
    });
    return checkNotNull(matching);
  }
}
