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
package org.assertj.swing.format;

import static javax.swing.tree.TreeSelectionModel.CONTIGUOUS_TREE_SELECTION;
import static javax.swing.tree.TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;
import static org.assertj.core.util.Strings.quote;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.assertj.swing.util.Arrays;

/**
 * Formatter for {@code JTree}s.
 *
 * @author Alex Ruiz
 */
public class JTreeFormatter extends ComponentFormatterTemplate {
  private static final String[] EMPTY = {};

  private static final IntEnum SELECTION_MODES = new IntEnum();
  static {
    SELECTION_MODES.put(SINGLE_TREE_SELECTION, "SINGLE_TREE_SELECTION")
                   .put(CONTIGUOUS_TREE_SELECTION, "CONTIGUOUS_TREE_SELECTION")
                   .put(DISCONTIGUOUS_TREE_SELECTION, "DISCONTIGUOUS_TREE_SELECTION");
  }

  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JTree} (or
   * subclass).
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JTree}.
   */
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    JTree tree = (JTree) c;
    String format = "%s[name=%s, selectionCount=%d, selectionPaths=%s, selectionMode=%s, enabled=%b, visible=%b, showing=%b";
    return String.format(format, getRealClassName(c), quote(tree.getName()), tree.getSelectionCount(),
                         Arrays.format(selectionPaths(tree)), selectionMode(tree), tree.isEnabled(), tree.isVisible(),
                         tree.isShowing());
  }

  private @Nonnull String[] selectionPaths(@Nonnull JTree tree) {
    TreePath[] paths = tree.getSelectionPaths();
    if (paths == null) {
      return EMPTY;
    }
    int count = paths.length;
    if (count == 0) {
      return EMPTY;
    }
    String[] pathArray = new String[count];
    for (int i = 0; i < count; i++) {
      TreePath path = paths[i];
      pathArray[i] = path != null ? path.toString() : null;
    }
    return pathArray;
  }

  private @Nullable String selectionMode(JTree tree) {
    TreeSelectionModel model = tree.getSelectionModel();
    return SELECTION_MODES.get(model.getSelectionMode());
  }

  /**
   * @return {@code JTree.class}.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return JTree.class;
  }
}
