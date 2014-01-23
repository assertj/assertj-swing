/*
 * Created on Jul 19, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.fest.util.Strings.concat;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.taskdefs.XSLTProcess.Param;
import org.apache.tools.ant.taskdefs.optional.junit.AggregateTransformer;
import org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.resources.URLResource;
import org.apache.tools.ant.util.FileUtils;

/**
 * Transforms a JUnit XML report. The default transformation generates an HTML report in either framed or non-framed
 * style.
 *
 * @author Alex Ruiz
 */
public class ReportTransformer extends AggregateTransformer {

  private static final String XSL_FILE_PATH = "org/fest/swing/junit/ant/";

  private Path classpath;

  /** The parameters that will be sent to the XSL transformation. */
  private final List<Param> params;

  /** Instance of a utility class to use for file operations. */
  private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();

  /** Used to ensure the uniqueness of a property. */
  private static int counter;

  /**
   * Creates a new <code>{@link ReportTransformer}</code>.
   * @param task task delegating to this class.
   */
  public ReportTransformer(Task task) {
    super(task);
    params = new CopyOnWriteArrayList<Param>();
  }

  /**
   * Create an instance of an XSL parameter for configuration by Ant.
   * @return an instance of the <code>Param</code> class to be configured.
   */
  @Override public Param createParam() {
    Param p = new Param();
    params.add(p);
    return p;
  }

  /**
   * Performs the XSLT transformation to generate the HTML report.
   * @throws BuildException thrown if something goes wrong with the transformation.
   */
  @Override public void transform() throws BuildException {
    checkOptions();
    TempFile tempFileTask = tempFileTask();
    XSLTProcess xsltTask = xsltTask();
    File outputFile = outputFile(tempFileTask);
    xsltTask.setOut(outputFile);
    createNewParams(xsltTask);
    createOutputDirParam(xsltTask);
    long startingTime = System.currentTimeMillis();
    try {
      xsltTask.execute();
    } catch (Exception e) {
      throw new BuildException(concat("Errors while applying transformations: ", e.getMessage()), e);
    }
    long transformTime = System.currentTimeMillis() - startingTime;
    task.log(concat("Transform time: ", String.valueOf(transformTime), " ms"));
    delete(outputFile);
  }

  private XSLTProcess xsltTask() {
    XSLTProcess xsltTask = new XSLTProcess();
    xsltTask.bindToOwner(task);
    xsltTask.setClasspath(classpath);
    xsltTask.setXslResource(getStylesheet());
    xsltTask.setIn(((XMLResultAggregator) task).getDestinationFile());
    return xsltTask;
  }

  /**
   * Access the stylesheet to be used as a resource.
   * @return stylesheet as a resource
   */
  @Override protected Resource getStylesheet() {
    String xslname = "junit-frames.xsl";
    if (NOFRAMES.equals(format)) xslname = "junit-noframes.xsl";
    if (styleDir == null) {
      URLResource stylesheet = new URLResource();
      URL stylesheetURL = getClass().getClassLoader().getResource(concat(XSL_FILE_PATH, xslname));
      stylesheet.setURL(stylesheetURL);
      return stylesheet;
    }
    FileResource stylesheet = new FileResource();
    File stylesheetFile = new File(styleDir, xslname);
    stylesheet.setFile(stylesheetFile);
    return stylesheet;
  }

  private TempFile tempFileTask() {
    TempFile tempFileTask = new TempFile();
    tempFileTask.bindToOwner(task);
    return tempFileTask;
  }

  private File outputFile(TempFile tempFileTask) {
    Project project = task.getProject();
    if (format.equals(FRAMES)) {
      String tempFileProperty = concat(getClass().getName(), String.valueOf(counter++));
      setUpTempFileTask(tempFileTask, tempFileProperty);
      return new File(project.getProperty(tempFileProperty));
    }
    return new File(toDir, "junit-noframes.html");
  }

  private void setUpTempFileTask(TempFile tempFileTask, String tempFileProperty) {
    Project project = task.getProject();
    File tmp = FILE_UTILS.resolveFile(project.getBaseDir(), project.getProperty("java.io.tmpdir"));
    tempFileTask.setDestDir(tmp);
    tempFileTask.setProperty(tempFileProperty);
    tempFileTask.execute();
  }

  private void createNewParams(XSLTProcess xsltTask) {
    for (Param param : params) {
      Param p = xsltTask.createParam();
      p.setProject(task.getProject());
      p.setName(param.getName());
      p.setExpression(param.getExpression());
    }
  }

  private void createOutputDirParam(XSLTProcess xsltTask) {
    Param p = xsltTask.createParam();
    p.setProject(task.getProject());
    p.setName("output.dir");
    p.setExpression(toDir.getAbsolutePath());
  }

  private void delete(File outputFile) {
    if (!format.equals(FRAMES)) return;
    Delete deleteTask = new Delete();
    deleteTask.bindToOwner(task);
    deleteTask.setFile(outputFile);
    deleteTask.execute();
  }

  /**
   * Sets an additional classpath.
   * @param classpath the additional classpath to append to the current one.
   */
  public void setClasspath(Path classpath) {
    createClasspath().append(classpath);
  }

  /**
   * Sets a reference to a classpath.
   * @param r the reference to set.
   */
  public void setClasspathRef(Reference r) {
    createClasspath().setRefid(r);
  }

  /**
   * Creates the current classpath.
   * @return the created classpath.
   */
  public Path createClasspath() {
    if (classpath == null) classpath = new Path(task.getProject());
    return classpath.createPath();
  }
}
