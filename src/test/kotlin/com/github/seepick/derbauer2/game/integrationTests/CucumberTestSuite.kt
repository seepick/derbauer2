package com.github.seepick.derbauer2.game.integrationTests

import io.cucumber.core.options.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "not @Ignore")
@SelectPackages("features") // NO! @SelectClasspathResource
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "com.github.seepick.derbauer2.game.integrationTests.stepdefs"
)
class CucumberTestSuite
