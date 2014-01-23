/*
 * Created on Aug 16, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestList;

/**
 * Base test case for drag and drop in {@link JListDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JListDriver_dragAndDrop_TestCase extends JListDriver_TestCase {
  TestList dropList;

  @Override
  final void extraSetUp() {
    dropList = execute(new GuiQuery<TestList>() {
      @Override
      protected TestList executeInEDT() {
        TestList l = new TestList("four", "five", "six");
        l.setName("dropList");
        window.addList(l);
        return l;
      }
    });
  }
}
