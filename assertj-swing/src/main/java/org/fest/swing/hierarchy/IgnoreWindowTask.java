/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import java.awt.Window;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Task that given a {@link WindowFilter}, marks a given {@code Window} as "ignored" if it is already implicitly ignored
 * by such filter. This task should be executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
class IgnoreWindowTask implements Runnable {
  private final Window w;
  private final WindowFilter filter;

  IgnoreWindowTask(@Nonnull Window w, @Nonnull WindowFilter filter) {
    this.w = w;
    this.filter = filter;
  }

  @RunsInCurrentThread
  @Override
  public void run() {
    // If the window was shown again since we queued this action, it will have removed the window from the
    // implicit filtered set, and we shouldn't filter.
    if (filter.isImplicitlyIgnored(w)) {
      filter.ignore(w);
    }
  }
}