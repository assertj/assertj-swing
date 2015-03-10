/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.fixture;

import org.junit.Test;

/**
 * Base test case for implementations of {@link ItemFixture}.
 * 
 * @author Alex Ruiz
 */
public interface ItemFixture_TestCase {
  @Test
  public void should_Select_Item();

  @Test
  public void should_Click_Item();

  @Test
  public void should_Click_Item_With_MouseButton();

  @Test
  public void should_Click_Item_Using_MouseClickInfo();

  @Test
  public void should_Double_Click_Item();

  @Test
  public void should_Right_Click_Item();

  @Test
  public void should_Show_Popup_Menu_At_Item();

  @Test
  public void should_Return_Item_Contents();

  @Test
  public void should_Drag_Item();

  @Test
  public void should_Drop_Item();
}
