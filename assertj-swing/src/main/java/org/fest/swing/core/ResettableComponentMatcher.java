/*
 * Created on Feb 5, 2010
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
package org.fest.swing.core;

/**
 * {@link ComponentMatcher} whose internal state can be reset.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public interface ResettableComponentMatcher extends ComponentMatcher {
  /**
   * Resets the internal state of this matcher.
   * 
   * @param matchFound indicates whether a match has been found before resetting.
   */
  void reset(boolean matchFound);
}
