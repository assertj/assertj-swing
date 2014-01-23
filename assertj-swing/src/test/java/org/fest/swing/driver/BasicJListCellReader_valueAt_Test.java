/*
 * Created on Apr 12, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JList;
import javax.swing.JToolBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestListModel;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link BasicJListCellReader#valueAt(JList, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJListCellReader_valueAt_Test extends RobotBasedTestCase {
  private MyList list;
  private BasicJListCellReader reader;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    list = window.list;
    reader = new BasicJListCellReader();
  }

  @Test
  public void should_return_text_from_JLabel_as_cellRenderer() {
    Object value = firstItemValue(reader, list);
    assertThat(value).isEqualTo("One");
  }

  @Test
  public void should_return_model_value_toString_if_cellRender_not_recognized() {
    list.setElements(new Jedi("Yoda"));
    setNotRecognizedRendererComponent(list);
    robot.waitForIdle();
    String value = firstItemValue(reader, list);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test
  public void should_return_null_if_cellRenderer_not_recognized_and_model_value_is_null() {
    list.setElements(new Object[] { null });
    setNotRecognizedRendererComponent(list);
    robot.waitForIdle();
    String value = firstItemValue(reader, list);
    assertThat(value).isNull();
  }

  @RunsInEDT
  private static void setNotRecognizedRendererComponent(final JList list) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        list.setCellRenderer(new CustomCellRenderer(new JToolBar()));
      }
    });
  }

  @RunsInEDT
  private static String firstItemValue(final BasicJListCellReader reader, final JList list) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        return reader.valueAt(list, 0);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final MyList list = new MyList("One", "Two");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(BasicJListCellReader_valueAt_Test.class);
      addComponents(list);
    }
  }

  private static class MyList extends JList {
    final TestListModel model;

    MyList(Object... elements) {
      model = new TestListModel(elements);
      setModel(model);
    }

    void setElements(final Object... elements) {
      execute(new GuiTask() {
        @Override
        protected void executeInEDT() {
          model.setElements(elements);
        }
      });
    }

    @Override
    public TestListModel getModel() {
      return model;
    }
  }
}
