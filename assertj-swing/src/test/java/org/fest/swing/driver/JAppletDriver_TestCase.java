/*
 * Created on Mar 30, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.fest.swing.core.TestRobots.singletonRobotMock;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.applet.AppletContext;
import java.net.URL;
import java.util.Map;

import javax.swing.JApplet;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;

/**
 * Base test case for {@link JAppletDriver}.
 *
 * @author Alex Ruiz
 */
public class JAppletDriver_TestCase extends EDTSafeTestCase {
  private JAppletStub applet;
  private JAppletDriver driver;

  @Before
  public final void setUp() {
    applet = JAppletStub.createNew();
    driver = new JAppletDriver(singletonRobotMock());
  }

  final JAppletStub applet() {
    return applet;
  }

  final JAppletDriver driver() {
    return driver;
  }

  static class JAppletStub extends JApplet {
    private final Map<String, Boolean> methodCallsInEDT = newHashMap();
    private final Map<String, String> parameters = newHashMap();

    private AppletContext context;
    private URL codeBase;
    private URL documentBase;

    private boolean active;

    @RunsInEDT
    static JAppletStub createNew() {
      return execute(new GuiQuery<JAppletStub>() {
        @Override
        protected JAppletStub executeInEDT() {
          return new JAppletStub();
        }
      });
    }

    @RunsInCurrentThread
    private JAppletStub() {
    }

    void updateAppletContext(AppletContext newContext) {
      context = newContext;
    }

    @Override
    public AppletContext getAppletContext() {
      registerMethodCall("getAppletContext");
      return context;
    }

    @Override
    public void resize(int width, int height) {
      registerMethodCall(concat("resize(", width, ", ", height, ")"));
    }

    void updateCodeBase(URL newCodeBase) {
      codeBase = newCodeBase;
    }

    @Override
    public URL getCodeBase() {
      registerMethodCall("getCodeBase");
      return codeBase;
    }

    void updateDocumentBase(URL newDocumentBase) {
      documentBase = newDocumentBase;
    }

    @Override
    public URL getDocumentBase() {
      registerMethodCall("getDocumentBase");
      return documentBase;
    }

    void addParameter(String name, String value) {
      parameters.put(name, value);
    }

    @Override
    public String getParameter(String name) {
      registerMethodCall(concat("getParameter(", quote(name), ")"));
      return parameters.get(name);
    }

    void setActive(boolean beActive) {
      active = beActive;
    }

    @Override
    public boolean isActive() {
      registerMethodCall("isActive");
      return active;
    }

    private void registerMethodCall(String methodName) {
      methodCallsInEDT.put(methodName, isEventDispatchThread());
    }

    boolean wasMethodCalledInEDT(String methodName) {
      Boolean calledInEDT = methodCallsInEDT.get(methodName);
      return calledInEDT == null ? false : calledInEDT;
    }
  }
}
