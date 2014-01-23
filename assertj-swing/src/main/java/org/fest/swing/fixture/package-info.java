/*
 * Created on Mar 2, 2013
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
 * Copyright @2013 the original author or authors.
 */
/**
 * <p>
 * Public API, source of FEST's power and flexibility. Although you can use the {@link org.fest.swing.core.BasicRobot}
 * directly, it is too low-level and requires, in our opinion, too much code. FEST fixtures can simplify creation and
 * maintenance of functional GUI tests by:
 *
 * <ol>
 * <li>providing reliable lookup of GUI components (by component name or using custom criteria)</li>
 * <li>simulating user events on GUI components</li>
 * <li>providing assertion methods about the state of GUI components</li>
 * </ol>
 * </p>
 *
 * <p>
 * The following example shows how easy is to use FEST fixtures. The test verifies that an error message is displayed if
 * the user enters her username but forgets to enter her password.
 *
 * <pre>
 *   private {@link org.fest.swing.fixture.FrameFixture} window;
 *
 *   &#64;Before
 *   public void setUp() {
 *     window = new FrameFixture(new LoginWindow());
 *     window.show();
 *   }
 *
 *   &#64;After
 *   public void tearDown() {
 *     window.cleanUp();
 *   }
 *
 *   &#64;Test
 *   public void shouldCopyTextInLabelWhenClickingButton() {
 *     window.textBox(&quot;username&quot;).enterText(&quot;some.user&quot;);
 *     window.button(&quot;login&quot;).click();
 *     window.optionPane().requireErrorMessage().requireMessage(&quot;Please enter your password&quot;);
 *   }
 * </pre>
 *
 * </p>
 * <p>
 * The test uses a {@link org.fest.swing.fixture.FrameFixture} to launch the GUI to test ({@code LoginWindow}) and find
 * the GUI components in such window. This is the recommended way to use FEST. We could also use individual fixtures to
 * simulate user events, but it would result in more code to write and maintain:
 *
 * <pre>
 *   private {@link org.fest.swing.core.BasicRobot} robot;
 *
 *   &#64;Before
 *   public void setUp() {
 *     robot = BasicRobot.robotWithNewAwtHierarchy();
 *     robot.showWindow(new LoginWindow());
 *   }
 *
 *   &#64;After
 *   public void tearDown() {
 *     robot.cleanUp();
 *   }
 *
 *   &#64;Test
 *   public void shouldCopyTextInLabelWhenClickingButton() {
 *     new {@link org.fest.swing.fixture.JTextComponentFixture}(robot, &quot;username&quot;).enterText(&quot;some.user&quot;);
 *     new {@link org.fest.swing.fixture.JButtonFixture}(robot, &quot;login&quot;).click();
 *     new {@link org.fest.swing.fixture.JOptionPaneFixture}(robot).requireErrorMessage().requireMessage(&quot;Please enter your password&quot;);
 *   }
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Note:</strong> It is very important to clean up resources used by FEST (keyboard, mouse and opened windows)
 * after each test; otherwise, the FEST robot will keep control of them and can make your computer pretty much unusable.
 * To clean up resources call the method 'cleanUp' from {@link org.fest.swing.core.BasicRobot},
 * {@link org.fest.swing.fixture.FrameFixture} or {@link org.fest.swing.fixture.DialogFixture}.
 * </p>
 * <p>
 * Each fixture has the name of the GUI component it can control plus the word &quot;Fixture&quot; at the end. For
 * example, {@link org.fest.swing.fixture.JButtonFixture} can simulate user events on {@code JButton}s.
 * </p>
 * *
 *
 * @author Alex Ruiz
 */
package org.fest.swing.fixture;