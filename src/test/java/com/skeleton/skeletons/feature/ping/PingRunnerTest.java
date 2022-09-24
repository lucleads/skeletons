package com.skeleton.skeletons.feature.ping;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.annotation.DirtiesContext;

@Suite
@SelectClasspathResource("features/ping")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.skeleton.skeletons.feature.shared")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public final class PingRunnerTest {
}
