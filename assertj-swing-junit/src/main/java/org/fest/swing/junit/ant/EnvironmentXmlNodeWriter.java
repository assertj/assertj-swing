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
import static org.fest.swing.junit.xml.XmlAttribute.name;

import java.net.UnknownHostException;
import java.util.Date;

import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands how to write environment-related properties to a XML node.
 *
 * @author Alex Ruiz
 */
class EnvironmentXmlNodeWriter {

  private static final String LOCALHOST = "localhost";

  private final TimestampFormatter timestampFormatter;
  private final HostNameReader hostNameReader;

  EnvironmentXmlNodeWriter() {
    this(new TimestampFormatter(), new HostNameReader());
  }

  EnvironmentXmlNodeWriter(TimestampFormatter timestampFormatter, HostNameReader hostNameReader) {
    this.timestampFormatter = timestampFormatter;
    this.hostNameReader = hostNameReader;
  }

  EnvironmentXmlNodeWriter writeTimestamp(XmlNode target) {
    target.addAttribute(name(TIMESTAMP).value(timestampFormatter.format(new Date())));
    return this;
  }

  EnvironmentXmlNodeWriter writeHostName(XmlNode target) {
    String hostName = null;
    try {
      hostName = hostNameReader.localHostName();
    } catch (UnknownHostException e) {
      hostName = LOCALHOST;
    }
    target.addAttribute(name(HOSTNAME).value(hostName));
    return this;
  }
}
