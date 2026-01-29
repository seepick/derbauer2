package com.github.seepick.derbauer2.itest

import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

import io.cucumber.core.options.Constants

/**
 * JUnit Platform Suite for running Cucumber integration tests.
 * This class configures Cucumber to run all feature files in the features directory.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.github.seepick.derbauer2.itest")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:build/reports/cucumber.html, json:build/reports/cucumber.json")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "not @Ignore")
class CucumberTestSuite
