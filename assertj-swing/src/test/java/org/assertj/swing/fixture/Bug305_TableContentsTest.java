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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.util.Arrays.format;

import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TableRenderDemo;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=135">Bug 135</a>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Bug305_TableContentsTest extends RobotBasedTestCase {
  private MyWindow window;
  private JTableFixture fixture;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    robot.showWindow(window);
    fixture = new JTableFixture(robot, window.table);
  }

  @Test
  public void should_Return_Table_Contents() {
    String[][] contents = fixture.contents();
    assertThat(contents.length).isEqualTo(5);
    assertThat(contents[0].length).isEqualTo(5);
    assertThat(contents[0][0]).isEqualTo("Mary");
    assertThat(contents[0][1]).isEqualTo("Campione");
    assertThat(contents[0][2]).isEqualTo("Snowboarding");
    assertThat(contents[0][3]).isEqualTo("5");
    assertThat(contents[0][4]).isEqualTo("false");
    assertThat(contents[1][0]).isEqualTo("Alison");
    assertThat(contents[1][1]).isEqualTo("Huml");
    assertThat(contents[1][2]).isEqualTo("Rowing");
    assertThat(contents[1][3]).isEqualTo("3");
    assertThat(contents[1][4]).isEqualTo("true");
    assertThat(contents[2][0]).isEqualTo("Kathy");
    assertThat(contents[2][1]).isEqualTo("Walrath");
    assertThat(contents[2][2]).isEqualTo("Knitting");
    assertThat(contents[2][3]).isEqualTo("2");
    assertThat(contents[2][4]).isEqualTo("false");
    assertThat(contents[3][0]).isEqualTo("Sharon");
    assertThat(contents[3][1]).isEqualTo("Zakhour");
    assertThat(contents[3][2]).isEqualTo("Speed reading");
    assertThat(contents[3][3]).isEqualTo("20");
    assertThat(contents[3][4]).isEqualTo("true");
    assertThat(contents[4][0]).isEqualTo("Philip");
    assertThat(contents[4][1]).isEqualTo("Milne");
    assertThat(contents[4][2]).isEqualTo("Pool");
    assertThat(contents[4][3]).isEqualTo("10");
    assertThat(contents[4][4]).isEqualTo("false");
  }

  @Test
  public void should_Pass_If_Content_Is_Equal_To_Expected() {
    String[][] contents = new String[][] { { "Mary", "Campione", "Snowboarding", "5", "false" },
        { "Alison", "Huml", "Rowing", "3", "true" }, { "Kathy", "Walrath", "Knitting", "2", "false" },
        { "Sharon", "Zakhour", "Speed reading", "20", "true" }, { "Philip", "Milne", "Pool", "10", "false" } };
    fixture.requireContents(contents);
  }

  @Test
  public void should_Fail_If_Content_Not_Equal_To_Expected() {
    thrown.expectAssertionError("property:'contents'");
    thrown.expectMessageToContain("expected:<[['hello']]>", concat("but was<", format(fixture.contents()), ">"));
    fixture.requireContents(new String[][] { { "hello" } });
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTable table;

    private MyWindow() {
      super(Bug305_TableContentsTest.class);
      TableRenderDemo newContentPane = new TableRenderDemo();
      table = newContentPane.table;
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
    }
  }
}
