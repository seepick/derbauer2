#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

echoH1 "🚦  Running all tests"
./gradlew detekt test uiTest -PenableUiTests || exit 1
echoSuccess "Running all tests"
