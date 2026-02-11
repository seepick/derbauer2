#!/bin/bash

# ensure CWD is project root
CWD=$(pwd)
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

echoH1 "🔬  Validating Local State"

./bin/validate_documentation.sh || exit 1
./bin/build_doc_diagrams.sh || exit 1

echo "👷🏻‍♂️ Run Gradle and Tests"
./gradlew \
  detekt -PfailOnDetektIssue check \
  test uiTest jacocoTestCoverageVerification -PenableUiTests \
  dependencyUpdates -PfailOnDependencyUpdates \
  || exit 1

echoSuccess "Validating Local State"
