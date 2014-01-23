/*
 * Created on Apr 6, 2009
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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;
import static org.fest.swing.junit.ant.CommonConstants.UNKNOWN;
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.swing.junit.xml.XmlAttributes.attributes;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands how to write information about a test suite to a XML node.
 *
 * @author Alex Ruiz
 */
class SuiteXmlNodeWriter {

  SuiteXmlNodeWriter writeSuiteName(XmlNode target, JUnitTest suite) {
    String suiteName = suite.getName();
    target.addAttribute(name(ATTR_NAME).value(suiteName == null ? UNKNOWN : suiteName));
    return this;
  }

  SuiteXmlNodeWriter writeSuiteProperties(XmlNode target, JUnitTest suite) {
    XmlNode propertiesNode = target.addNewNode(PROPERTIES);
    Properties properties = suite.getProperties();
    if (properties == null) return this;
    Enumeration<?> propertyNames = properties.propertyNames();
    while (propertyNames.hasMoreElements())
      writeProperty(propertiesNode, properties, (String)propertyNames.nextElement());
    return this;
  }

  private void writeProperty(XmlNode target, Properties properties, String propertyName) {
    String propertyValue = properties.getProperty(propertyName);
    target.addNewNode(PROPERTY, attributes(name(ATTR_NAME).value(propertyName), name(ATTR_VALUE).value(propertyValue)));
  }

  SuiteXmlNodeWriter writeSuiteStatistics(XmlNode target, JUnitTest suite) {
    target.addAttribute(name(ATTR_TESTS).value(suite.runCount()));
    target.addAttribute(name(ATTR_FAILURES).value(suite.failureCount()));
    target.addAttribute(name(ATTR_ERRORS).value(suite.errorCount()));
    target.addAttribute(name(ATTR_TIME).value(suite.getRunTime() / 1000.0));
    return this;
  }
}
