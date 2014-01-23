/*
 * Created on Jul 12, 2007
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

import org.apache.tools.ant.taskdefs.optional.junit.AggregateTransformer;
import org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/**
 * Aggregates all &lt;junit&gt; XML formatter test suite data under a specific directory and transforms the results via
 * XSLT.
 *
 * @author Alex Ruiz
 */
public class JUnitReportTask extends XMLResultAggregator {

  private Path classpath;

  /**
   * Generate a report based on the document created by the merge.
   * @return the generated report.
   */
  @SuppressWarnings("unchecked")
  @Override public AggregateTransformer createReport() {
    ReportTransformer transformer = new ReportTransformer(this);
    transformer.setClasspath(classpath);
    transformers.addElement(transformer);
    return transformer;
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
    if (classpath == null) classpath = new Path(getProject());
    return classpath.createPath();
  }
}
