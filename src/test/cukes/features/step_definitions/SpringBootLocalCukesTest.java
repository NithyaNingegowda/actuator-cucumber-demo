package features.step_definitions;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emc.demo.ActuatorCucumberDemoApplication;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ActuatorCucumberDemoApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
@Deprecated // For reference purposes only, cuke tests currently only run against a built and deployed endpoint
public class SpringBootLocalCukesTest
{

}
