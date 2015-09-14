# actuator-cucumber-demo

:shipit:

The main purpose of this app is to demonstrate

1. Customisation of Spring Boot health endpoints
2. Cucumber testing against those endpoints
3. Spring wired cucumber tests
4. Spring Profile driven target urls for the test suite to use
5. Binding of cucumber tests to Maven with the failsafe plug-in rather than the surefire plug-in

###### Customisation of Spring Boot health endpoints

* See the code in com.emc.demo with class names ThirdParty*.java

###### Cucumber testing against those endpoints

* See the features folder in src/test/resources
* See src/test/cukes for Step Definition implementations

###### Spring wired cucumber tests

* This is fairly standard, starting with the cucumber.xml

###### Spring Profile driven target urls for the test suite to use

* See the system-under-test-*.properties in src/test/resources
* See features.step_definitions.SpringBootstrap in src/test/cukes
* Note that the default is local and that -DenvTarget=dev is used to invoke a PCF hosted version of the app

###### Binding of cucumber tests to Maven with the failsafe plug-in rather than the surefire plug-in

* See the pom.xml config in the profile called acceptance-tests