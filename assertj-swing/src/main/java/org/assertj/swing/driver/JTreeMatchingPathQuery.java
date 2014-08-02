package org.assertj.swing.driver;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.assertj.swing.driver.JTreeAddRootIfInvisibleTask.addRootIfInvisible;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Finds a path in a {@code JTree} that matches a given {@code String}. This query is executed in the event dispatch
 * thread (EDT.)
 * 
 * @author Alex Ruiz
 * 
 * @see JTreePathFinder
 */
final class JTreeMatchingPathQuery {
  @RunsInEDT
  static @Nonnull TreePath verifyJTreeIsReadyAndFindMatchingPath(final @Nonnull JTree tree, final @Nonnull String path,
      final @Nonnull JTreePathFinder pathFinder) {
    TreePath result = execute(new GuiQuery<TreePath>() {
      @Override
      protected @Nonnull TreePath executeInEDT() {
        checkEnabledAndShowing(tree);
        return matchingPathWithRootIfInvisible(tree, path, pathFinder);
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  static @Nonnull TreePath matchingPathFor(final @Nonnull JTree tree, final @Nonnull String path,
      final @Nonnull JTreePathFinder pathFinder) {
    TreePath result = execute(new GuiQuery<TreePath>() {
      @Override
      protected @Nonnull TreePath executeInEDT() {
        return matchingPathWithRootIfInvisible(tree, path, pathFinder);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  static @Nonnull TreePath matchingPathWithRootIfInvisible(@Nonnull JTree tree, @Nonnull String path,
      @Nonnull JTreePathFinder pathFinder) {
    TreePath matchingPath = pathFinder.findMatchingPath(tree, path);
    return addRootIfInvisible(tree, matchingPath);
  }

  private JTreeMatchingPathQuery() {
  }
}