/*
 * Created on Jul 23, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static java.util.Arrays.asList;
import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.ProjectHelper.configureProject;

import java.io.*;
import java.util.Vector;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.listener.TimestampedLogger;

/**
 * Understands programmatic execution of a Ant task.
 *
 * @author Alex Ruiz
 */
public final class AntLauncher {

  private final Project project = new Project();
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  public AntLauncher(File buildFile) {
    project.addBuildListener(createLogger(out));
    project.init();
    configureProject(project, buildFile);
    project.setUserProperty("ant.file", buildFile.getAbsolutePath());
  }

  public String executeDefaultTarget() {
    return execute(project.getDefaultTarget());
  }

  public String execute(String... targetNames) {
    project.executeTargets(new Vector<String>(asList(targetNames)));
    return out.toString();
  }

  private static BuildLogger createLogger(ByteArrayOutputStream out) {
    BuildLogger logger = new TimestampedLogger();
    logger.setEmacsMode(false);
    logger.setErrorPrintStream(printStreamFrom(out));
    logger.setOutputPrintStream(printStreamFrom(out));
    logger.setMessageOutputLevel(MSG_INFO);
    return logger;
  }

  private static PrintStream printStreamFrom(ByteArrayOutputStream out) {
    return new PrintStream(out);
  }
}
