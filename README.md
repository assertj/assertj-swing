Test NG: [![][Maven Central TestNG img]][Maven Central TestNG]

JUnit: [![][Maven Central JUnit img]][Maven Central JUnit]



This project provides a simple and intuitive API for functional testing of Swing user interfaces, resulting in tests 

that are compact, easy to write, and read like a specification. Tests written using AssertJ Swing are also robust. 

AssertJ Swing simulates actual user gestures at the operating system level, ensuring that the application will behave correctly in 

front of the user. It also provides a reliable mechanism for GUI component lookup that ensures that changes in the GUI's 

layout or look-and-feel will not break your tests.



This project is a fork of https://github.com/alexruiz/fest-swing-1.x.



[Maven Central TestNG]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-testng

[Maven Central TestNG img]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-testng/badge.svg



[Maven Central JUnit]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-junit

[Maven Central JUnit img]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-junit/badge.svg



# How to release



1. Preconditions: You need ssh setup for being able to push to [assertj/assertj-swing](https://github.com/assertj/assertj-swing), gpg setup for signing the release and ossrh setup in Maven for being able to publish the release, including the right to publish assertj-swing releases.

2. Make sure you have the latest sources: `git pull`

3. Make sure you have a clean release base: `mvn release:clean`

4. Prepare the release: `mvn release:prepare`

5. Actually publish the release: `mvn release:perform`

6. Sign in to github, click the tag, edit it and click *publish* (optionally insert change log here)

7. Download updated [JIDE](https://jidesoft.com/evaluation/) (*Password required. Click here to see the password. Please note, it's not your forum user name and password*)

8. Create temporary work space: `mkdir /tmp/jide` and cd `/tmp/jide`

9. Unzip JIDE: `unzip ~/Downloads/jide.X.Y.Z.eval.zip`

10. `cd maven/`

11. Follow the readme:

12. Download [ant-contrib](https://downloads.sourceforge.net/project/ant-contrib/ant-contrib/1.0b3) and copy jar file to current directory

13. Download [Apache common bean script](https://commons.apache.org/proper/commons-bsf/download_bsf.cgi) and copy jar file to current directory

14. Download [Mozilla Rhino](https://github.com/downloads/mozilla/rhino/rhino1_7R4.zip) and copy jar file to current directory

15. Download [Commons logging](https://commons.apache.org/proper/commons-logging/download_logging.cgi) and copy jar file to current directory

16. Mavenify: `ant -f mavenify.xml -lib .`

17. Integrate resulting maven repo into own maven repos: `rsync -a repo/ ~/.m2/repository/`

18. Go to `assertj-swing/assertj-swing-jide/pom.xml` and enter downloaded version: `<jide.version>X.Y.Z</jide.version>`

19. __Update parent POM (AssertJ Swing) version__ to currently (see 5.) released version

20. Fix potentially broken code - use `mvn clean verify -f assertj-swing-jide/pom.xml` to verify

21. Update POM version, commit and push changes

22. Release assertj-swing-jide:

23. `cd assertj-swing-jide`

24. Clean state: `mvn release:clean`

25. Preapre: `mvn release:prepare`

26. Perform: `mvn release:perform`

27. Update documentation - pull [assertj/doc]()

28. Update at least `src/docs/asciidoc/user-guide/assertj-swing-release-notes.adoc`

29. File PR for documentation update. __Finished!__

