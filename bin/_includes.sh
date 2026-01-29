#!/bin/bash

# GENERAL VAL
#############################################################################

COL_BOLD="\033[1m"
COL_ITALIC="\033[3m"
COL_RESET="\033[0m"

#0 - Normal Style
#1 - Bold
#2 - Dim
#3 - Italic
#4 - Underlined
#5 - Blinking
#7 - Reverse
#8 - Invisible

COL_RED="\033[0;31m"
COL_GREEN="\033[0;32m"
COL_CYAN="\033[0;36m"
COL_GREY="\033[1;30m"
#COL_GREY_BG="\033[0;100m-e"

#Black	0;30
#Red	  0;31
#Green	0;32
#Brown	0;33
#Blue	  0;34
#Purple	0;35

# GENERAL FUN
#############################################################################

echoH1() {
  TITLE=$1
  echo ""
  echo -e "${COL_CYAN}===================================================${COL_RESET}"
  echo -e "${COL_CYAN}===${COL_RESET} ${COL_BOLD}${TITLE}${COL_RESET}"
  echo -e "${COL_CYAN}===================================================${COL_RESET}"
  echo ""
}

echoParam() {
  MESSAGE=$1
  PARAM=$2
  echo -e "$MESSAGE: ${COL_GREY}${COL_BOLD}$PARAM${COL_RESET}"
}

echoSuccess() {
  TITLE=$1
  echo ""
  echo -e "✅ $TITLE was ${COL_GREEN}SUCCESSFUL${COL_RESET} ✅"
  echo ""
}

verifyExists() {
  FILE=$1
  if [ ! -f "$FILE" ]; then
      echo "$FILE does not exist!"
      exit 1
  fi
}

# SPECIFIC FUN
#############################################################################

echoDocCommitWarning() {
  echo ""
  echo -e "⚠️ ${COL_RED}Commit changes${COL_RESET} to trigger GitHub pages action. ⚠️"
#  sleep 1
  echo ""
}

# make sure to surround with double quotes (otherwise space will break): safeExec "$COMMAND_WITH_SPACES"
safeExec() {
  CMD=$1
#  echo ">> $CMD"
  `$CMD` || exit 1
}