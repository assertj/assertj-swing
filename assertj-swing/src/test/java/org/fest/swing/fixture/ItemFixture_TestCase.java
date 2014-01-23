/*
 * Created on Dec 26, 2009
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
package org.fest.swing.fixture;

import org.junit.Test;

/**
 * Base test case for implementations of {@link ItemFixture}.
 * 
 * @author Alex Ruiz
 */
public interface ItemFixture_TestCase {
  @Test
  public void should_select_item();

  @Test
  public void should_click_item();

  @Test
  public void should_click_item_with_MouseButton();

  @Test
  public void should_click_item_using_MouseClickInfo();

  @Test
  public void should_double_click_item();

  @Test
  public void should_right_click_item();

  @Test
  public void should_show_popup_menu_at_item();

  @Test
  public void should_return_item_contents();

  @Test
  public void should_drag_item();

  @Test
  public void should_drop_item();
}
