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
 * Utilities for finding {@code Component}s.
 * </p>
 *
 * <p>
 * An example is the main window of an application being shown after the user's credentials have been successfully
 * verified. The following are the steps to complete such scenario:
 * </p>
 *
 * <p>
 * <ul>
 * <li>User launches the application</li>
 * <li>A login window appears</li>
 * <li>User enters her username and password and clicks the &quot;Login&quot; button</li>
 * <li>User is authenticated and authorized successfully</li>
 * <li>The main window of the application is displayed</li>
 * </ul>
 * </p>
 *
 * <p>
 * The &quot;tricky&quot; part here is step 4. Authentication/authorization can take some time (depending on network
 * traffic, etc.) and we need to wait for the main window to appear in order to continue our test. It is possible to
 * test this scenario with FEST:
 * <pre>
 * loginDialog.textBox(&quot;username&quot;).enterText(&quot;yvonne&quot;);
 * loginDialog.textBox(&quot;password&quot;).enterText(&quot;welcome&quot;);
 * loginDialog.button(&quot;login&quot;).click();
 *
 * // now the interesting part, we need to wait till the main window is shown.
 * FrameFixture mainFrame = findFrame(&quot;main&quot;).using(loginDialog.robot);
 *
 * // we can continue testing the main window.
 * </pre>
 * </p>
 *
 * <p>
 * The &quot;<{@link org.fest.swing.finder.WindowFinder#findFrame(String) findFrame}&quot; method (imported statically
 * from {@link org.fest.swing.finder.WindowFinder}) can lookup a {@code Frame} (having &quot;main&quot; as its name)
 * with a default timeout of 5 seconds. That means that if in 5 seconds the frame we are looking for is not found, the
 * test will fail.
 * </p>
 *
 * <p>
 * We can also specify a custom value for the timeout. For example, we can set the timeout to 10 seconds in two ways:
 * <pre>
 * FrameFixture mainFrame = findFrame(&quot;main&quot;).withTimeout(10000).using(loginDialog.robot);
 * // or
 * FrameFixture mainFrame = findFrame(&quot;main&quot;).withTimeout(10, SECONDS).using(loginDialog.robot);
 * </pre>
 * </p>
 *
 * <p>
 * We can also look up Frames by type:
 * <pre>
 * FrameFixture mainFrame = findFrame(MainFrame.class).using(loginDialog.robot);
 * </pre>
 * </p>
 *
 * <p>
 * Something that you may find weird in the code examples is &quot;{@code using(loginDialog.robot)}.&quot; This is
 * necessary because, in a given test, only one instance of {@link org.fest.swing.core.Robot Robot} can be running, to
 * prevent GUI tests from blocking each other on the screen. In another words, in a test class you can only use one and
 * only one instance of {@code Robot}.
 * </p>
 *
 * @author Alex Ruiz
 */
package org.fest.swing.finder;