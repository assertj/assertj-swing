/*
 * Created on Apr 2, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static java.lang.System.currentTimeMillis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.Test;

import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands a collection of executed tests in a suite.
 *
 * @author Alex Ruiz
 */
class TestCollection {

  final Map<Test, XmlNode> testXml = new ConcurrentHashMap<Test, XmlNode>();
  final List<Test> failed = new CopyOnWriteArrayList<Test>();
  final Map<Test, Long> started = new ConcurrentHashMap<Test, Long>();

  void started(Test test) {
    started.put(test, currentTimeMillis());
  }

  boolean wasStarted(Test test) {
    return started.containsKey(test);
  }

  long startTimeOf(Test test) {
    return started.get(test);
  }

  void addXmlNode(Test test, XmlNode n) {
    testXml.put(test, n);
  }

  XmlNode xmlNodeFor(Test test) {
    return testXml.get(test);
  }

  boolean wasFailed(Test test) {
    return failed.contains(test);
  }

  void failed(Test test) {
    failed.add(test);
  }
}
