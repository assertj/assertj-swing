/*
 * Created on Aug 25, 2007
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
package org.fest.swing.test.swing;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * <p>
 * {@code JList} that requires a name and supports drag and drop.
 * </p>
 *
 * <p>
 * Adapted from the tutorial <a href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html"
 * target="_blank">Introduction to Drag and Drop and Data Transfer</a>.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class TestList extends JList {
  final DefaultListModel model = new DefaultListModel();

  @RunsInCurrentThread
  public TestList(String... elements) {
    this(null, newArrayList(elements));
  }

  @RunsInCurrentThread
  public TestList(String name, List<String> elements) {
    setDragEnabled(true);
    setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
    for (String e : elements) {
      model.addElement(e);
    }
    setModel(model);
    setName(name);
    setTransferHandler(new ListTransferHandler());
  }

  @RunsInEDT
  public String[] elements() {
    return execute(new GuiQuery<String[]>() {
      @Override
      protected String[] executeInEDT() {
        int count = model.getSize();
        String[] elements = new String[count];
        for (int i = 0; i < count; i++) {
          elements[i] = (String) model.get(i);
        }
        return elements;
      }
    });
  }

  @RunsInEDT
  public void requireElements(String... expected) {
    assertThat(elements()).isEqualTo(expected);
  }

  private static class ListTransferHandler extends StringTransferHandler<JList> {
    ListTransferHandler() {
      super(JList.class);
    }

    // Bundle up the selected items in the list as a single string, for export.
    @Override
    protected String exportString(JList list) {
      rows = list.getSelectedIndices();
      Object[] values = list.getSelectedValues();
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < values.length; i++) {
        Object val = values[i];
        b.append(val == null ? "" : val.toString());
        if (i != values.length - 1) {
          b.append("\n");
        }
      }
      return b.toString();
    }

    // Take the incoming string and wherever there is a newline, break it into a separate item in the list.
    @Override
    protected void importString(JList target, String s) {
      DefaultListModel listModel = (DefaultListModel) target.getModel();
      int index = target.getSelectedIndex();
      // Prevent the user from dropping data back on itself.
      if (rows != null && index >= rows[0] - 1 && index <= rows[rows.length - 1]) {
        rows = null;
        return;
      }
      int max = listModel.getSize();
      if (index < 0) {
        index = max;
      } else {
        index++;
        if (index > max) {
          index = max;
        }
      }
      addIndex = index;
      String[] values = s.split("\n");
      addCount = values.length;
      for (String value : values) {
        listModel.add(index++, value);
      }
    }

    // If the remove argument is true, the drop has been successful and it's time to remove the selected items from the
    // list. If the remove argument is false, it was a Copy operation and the original list is left intact.
    @Override
    protected void cleanup(JList source, boolean remove) {
      if (remove && rows != null) {
        DefaultListModel model = (DefaultListModel) source.getModel();
        // If we are moving items around in the same list, we need to adjust the indices accordingly, since those after
        // the insertion point have moved.
        if (addCount > 0) {
          for (int i = 0; i < rows.length; i++) {
            if (rows[i] > addIndex) {
              rows[i] += addCount;
            }
          }
        }
        for (int i = rows.length - 1; i >= 0; i--) {
          model.remove(rows[i]);
        }
      }
    }
  }
}