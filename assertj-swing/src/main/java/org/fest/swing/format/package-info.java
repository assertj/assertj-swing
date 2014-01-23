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
 * Formatters that create better and shorter {@code String} representations of AWT and Swing {@code Component}s.
 * </p>
 *
 * <p>
 * FEST-Swing provides default component formatters for all the Swing components in the JDK. Unlike the
 * '{@code toString}' method in Swing components, the provided component formatters display only the information
 * that can help developers solve problems in functional tests, excluding any information related to the appearance of
 * GUI components (e.g. colors, layouts, sizes, etc.)
 * </p>
 *
 * <p>
 * The following are some examples of the output of the some of the provided component formatters.
 * <ul>
 * <li>{@code org.fest.swing.test.TestFrame[name='frame', title='FormattingTest', enabled=true, showing=true]}</li>
 * <li>{@code javax.swing.JButton[name='button', text='A button', enabled=false]}</li>
 * <li>{@code javax.swing.JList[name='list', selectedValues=['One', 2], contents=['One', 2, 'Three', 4], selectionMode=MULTIPLE_INTERVAL_SELECTION, enabled=true]}</li>
 * </ul>
 * </p>
 *
 * @author Alex Ruiz
 */
package org.fest.swing.format;