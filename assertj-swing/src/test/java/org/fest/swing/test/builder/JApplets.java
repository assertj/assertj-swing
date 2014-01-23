/*
 * Created on Dec 8th, 2009 Mel Llaguno http://www.aclaro.com ------------------------------------
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
 * Copyright @2007-2009 the original author or authors.
 */

package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JApplet;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

public final class JApplets {
  private JApplets() {}

  public static JAppletFactory applet() {
    return new JAppletFactory();
  }

  public static class JAppletFactory {
    String name;

    public JAppletFactory withName(String newName) {
      name = newName;
      return this;
    }

    @RunsInEDT
    public JApplet createNew() {
      return execute(new GuiQuery<JApplet>() {
        @Override
        protected JApplet executeInEDT() {
          JApplet applet = new JApplet();
          applet.setName(name);
          return applet;
        }
      });
    }
  }
}
