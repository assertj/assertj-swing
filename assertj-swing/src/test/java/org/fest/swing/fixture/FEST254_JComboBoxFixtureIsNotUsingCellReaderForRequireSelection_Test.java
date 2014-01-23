package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-254" target="_blank">FEST-254</a>
 * 
 * @author Glen Schrader
 * @author Alex Ruiz
 */
public class FEST254_JComboBoxFixtureIsNotUsingCellReaderForRequireSelection_Test extends RobotBasedTestCase {
  private JComboBox comboBox;
  private JComboBoxFixture fixture;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    fixture = new JComboBoxFixture(robot, comboBox);
    robot.showWindow(window);
  }

  @Test
  public void should_return_text_of_selected_item_in_editable_JComboBox() {
    selectIndex(comboBox, 0);
    robot.waitForIdle();
    assertThat(fixture.requireSelection("first"));
  }

  @RunsInEDT
  private static void selectIndex(final JComboBox comboBox, final int index) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setSelectedIndex(index);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private final JComboBox comboBox;

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
      super(FEST254_JComboBoxFixtureIsNotUsingCellReaderForRequireSelection_Test.class);
      comboBox = new JComboBox(array(TestValue.values()));
      comboBox.setEditable(true);
      comboBox.setEditor(new MyComboBoxEditor());
      comboBox.setRenderer(new MyComboBoxRenderer());
      comboBox.setSelectedIndex(-1);
      addComponents(comboBox);
    }
  }

  private static class MyComboBoxEditor extends BasicComboBoxEditor {
    @Override
    public void setItem(Object anObject) {
      super.setItem(renderIfValue(anObject));
    }

    @Override
    public Object getItem() {
      String text = editor.getText();
      try {
        return TestValue.valueOf(text.toUpperCase());
      } catch (IllegalArgumentException e) {
        return null;
      }
    }
  }

  private static class MyComboBoxRenderer extends BasicComboBoxRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
      return super.getListCellRendererComponent(list, renderIfValue(value), index, isSelected, cellHasFocus);
    }
  }

  private static Object renderIfValue(Object o) {
    if (o instanceof TestValue) {
      return renderValue((TestValue) o);
    }
    return o;
  }

  private static String renderValue(TestValue v) {
    return v == null ? null : v.toString().toLowerCase();
  }

  private static enum TestValue {
    FIRST, SECOND, THIRD
  }
}