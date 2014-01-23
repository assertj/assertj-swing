/*
 * Created on Aug 23, 2008
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

import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.quote;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Lookup of {@code TreePath}s which text matches the given one.
 * 
 * @author Alex Ruiz
 */
class JTreePathFinder {
  // TODO TEST
  private static final String SEPARATOR = "/";

  private JTreeCellReader cellReader;
  private String separator;

  JTreePathFinder() {
    replaceCellReader(new BasicJTreeCellReader());
    replaceSeparator(SEPARATOR);
  }

  @RunsInCurrentThread
  @Nonnull TreePath findMatchingPath(@Nonnull JTree tree, @Nonnull String path) {
    String[] pathStrings = splitPath(path);
    TreeModel model = tree.getModel();
    List<Object> newPathValues = newArrayList();
    Object node = model.getRoot();
    int pathElementCount = pathStrings.length;
    for (int stringIndex = 0; stringIndex < pathElementCount; stringIndex++) {
      String pathString = pathStrings[stringIndex];
      Object match = null;
      if (stringIndex == 0 && tree.isRootVisible()) {
        if (!pathString.equals(value(tree, node))) {
          throw pathNotFound(path);
        }
        newPathValues.add(node);
        continue;
      }
      int childCount = model.getChildCount(node);
      for (int childIndex = 0; childIndex < childCount; childIndex++) {
        Object child = model.getChild(node, childIndex);
        if (pathString.equals(value(tree, child))) {
          if (match != null) {
            throw multipleMatchingNodes(pathString, value(tree, node));
          }
          match = child;
        }
      }
      if (match == null) {
        throw pathNotFound(path);
      }
      newPathValues.add(match);
      node = match;
    }
    return new TreePath(newPathValues.toArray());
  }

  private @Nonnull LocationUnavailableException pathNotFound(@Nonnull String path) {
    throw new LocationUnavailableException(String.format("Unable to find path %s", quote(path)));
  }

  private @Nonnull String[] splitPath(@Nonnull String path) {
    List<String> result = newArrayList();
    int separatorSize = separator.length();
    int index = 0;
    int pathSize = path.length();
    while (index < pathSize) {
      int separatorPosition = path.indexOf(separator, index);
      if (separatorPosition == -1) {
        separatorPosition = pathSize;
      }
      result.add(path.substring(index, separatorPosition));
      index = separatorPosition + separatorSize;
    }
    return result.toArray(new String[result.size()]);
  }

  private @Nonnull LocationUnavailableException multipleMatchingNodes(@Nonnull String matchingText,
      @Nullable Object parentText) {
    String msg = String.format("There is more than one node with value '%s' under", matchingText, quote(parentText));
    throw new LocationUnavailableException(msg);
  }

  private @Nullable String value(@Nonnull JTree tree, @Nullable Object modelValue) {
    return cellReader.valueAt(tree, modelValue);
  }

  @Nonnull String separator() {
    return separator;
  }

  void replaceSeparator(@Nonnull String newSeparator) {
    separator = newSeparator;
  }

  void replaceCellReader(@Nonnull JTreeCellReader newCellReader) {
    cellReader = newCellReader;
  }

  @Nonnull JTreeCellReader cellReader() {
    return cellReader;
  }
}
