#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"


echoH1 "🏁  Verifying local state"

./bin/validate_documentation.sh || exit 1
./bin/build_doc_diagrams.sh || exit 1

echoH1 "👷🏻‍♂️  Run Gradle and Tests"
./gradlew detekt test uiTest check jacocoTestReport -PrunUiTests || exit 1
# TODO jacoco test verify
echoSuccess "Verifying local state"
