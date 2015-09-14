# actuator-cucumber-demo

:shipit:

The main purpose of this app is to demonstrate

1. Customisation of Spring Boot health endpoints
2. Cucumber testing against those endpoints
3. Spring wired cucumber tests
4. Spring property driven target urls for the test suite to use
5. Binding of cucumber tests to Maven with the failsafe plug-in rather than the surefire plug-in

###### Customisation of Spring Boot health endpoints

* See the code in com.emc.demo with class names ThirdParty*.java

###### Cucumber testing against those endpoints

* See the features folder in src/test/resources
* See src/test/cukes for Step Definition implementations

###### Spring wired cucumber tests

* This is fairly standard, starting with the cucumber.xml

###### Spring property driven target urls for the test suite to use

* See features.step_definitions.SpringBootstrap in src/test/cukes
* See features.step_definitions.Stepdefs in src/test/cukes where @Value is used
* Note that the default is localhost:8080 and that -DtargetRootUrl=X is used to invoke a hosted version of the app

###### Binding of cucumber tests to Maven with the failsafe plug-in rather than the surefire plug-in

* See the pom.xml config in the profile called acceptance-tests