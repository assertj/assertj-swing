/*
 * Created on Nov 6, 2008
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

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

import javax.annotation.Nullable;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Indicates whether it is possible for the user to move the given AWT or Swing {{@code Component}. This query is
 * <b>not</b> guaranteed to be executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class ComponentMovableQuery {
  @RunsInCurrentThread
  static boolean isUserMovable(@Nullable Component c) {
    return c instanceof Dialog || c instanceof Frame || c instanceof JInternalFrame;
  }

  private ComponentMovableQuery() {}
}
