/*
 * Created on Mar 10, 2010
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
package org.fest.swing.driver;

import static org.fest.swing.driver.JTreeMatchingPathQuery.matchingPathWithRootIfInvisible;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the text of a node in a {@code JTree}.
 *
 * @author Alex Ruiz
 *
 * @since 1.2
 */
final class JTreeNodeTextQuery {
  @RunsInEDT
  static @Nullable String nodeText(final @Nonnull JTree tree, final int row, final @Nonnull JTreeLocation location,
      final @Nonnull JTreePathFinder pathFinder) {
    return execute(new GuiQuery<String>() {
      @Override protected String executeInEDT() {
        TreePath matchingPath = location.pathFor(tree, row);
        return pathFinder.cellReader().valueAt(tree, checkNotNull(matchingPath.getLastPathComponent()));
      }
    });
  }

  @RunsInEDT
  static @Nullable String nodeText(final @Nonnull JTree tree, final @Nonnull String path,
      final @Nonnull JTreePathFinder pathFinder) {
    return execute(new GuiQuery<String>() {
      @Override protected String executeInEDT() {
        TreePath matchingPath = matchingPathWithRootIfInvisible(tree, path, pathFinder);
        return pathFinder.cellReader().valueAt(tree, checkNotNull(matchingPath.getLastPathComponent()));
      }
    });
  }

  private JTreeNodeTextQuery() {}
}
