/*
 * Created on Jul 31, 2009
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
package org.assertj.swing.timing;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link Condition} that is never satisfied.
 * 
 * @author Alex Ruiz
 */
class NeverSatisfiedCondition extends Condition {
  private AtomicInteger count;
  public NeverSatisfiedCondition() {
    super("Never satisfied");
    count = new AtomicInteger(0);
  }

  @Override
  public boolean test() {
    count.incrementAndGet();
    return false;
  }

  public int getCount() {
    return count.get();
  }
}