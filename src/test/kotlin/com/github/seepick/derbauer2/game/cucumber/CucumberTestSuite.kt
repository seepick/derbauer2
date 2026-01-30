package com.github.seepick.derbauer2.game

import io.cucumber.core.options.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty,html:build/reports/cucumber/cucumber.html,json:build/reports/cucumber/cucumber.json"
)
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "not @Ignore")
// @SelectClasspathResource("features") // NO!
@SelectPackages("features")
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "com.github.seepick.derbauer2.game.cucumber.stepdefs"
)
class CucumberTestSuite
