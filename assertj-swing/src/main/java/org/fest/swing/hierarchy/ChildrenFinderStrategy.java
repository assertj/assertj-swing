/*
 * Created on Oct 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Finds children {@code Component}s in an AWT or Swing {@code Container}.
 * 
 * @author Yvonne Wang
 */
interface ChildrenFinderStrategy {
  /**
   * <p>
   * Returns the non-explicit children of an AWT or Swing {@code Container}. Non-explicit children are AWT or Swing
   * {@code Component}s considered to be children of the given {@code Container} but cannot be obtained by calling
   * {@code Container.getComponents()}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the container whose children we are looking for.
   * @return a collection containing the non-explicit children found.
   */
  @RunsInCurrentThread
  @Nonnull Collection<Component> nonExplicitChildrenOf(@Nonnull Container c);
}
