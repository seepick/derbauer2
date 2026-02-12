#!/bin/bash

# ensure CWD is project root
CWD=$(pwd)
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

# check `documentation/*.md` if any more than max lines
SOURCE_DIR="./documentation"
MAX_LINES=100
SUPPRESSION="@SUPPRESS_MAX_LINES"

exceeding=0
checked=0
tmp=$(mktemp) || exit 1

echoH1 "📜  Validate documentation files"
echoParam "📁  Source directory" $SOURCE_DIR
echoParam "📈  Max lines" $MAX_LINES

find $SOURCE_DIR -type f -name '*.md' > "$tmp"
while IFS= read -r file || [ -n "$file" ]; do
  checked=$((checked + 1))
  if head -n 1 "$file" | grep -Fq "$SUPPRESSION"; then continue; fi
  lines=$(wc -l < "$file" | tr -d ' ')
  if [ "$lines" -gt "$MAX_LINES" ]; then
    printf '❌  Too long file: %s --> %s lines exceeds max of %s\n' "$file" "$lines" "$MAX_LINES"
    exceeding=$((exceeding + 1))
  fi
done < "$tmp"
rm -f "$tmp"

echo ""
echo "Script done: $exceeding out of $checked files are invalid"
if [ "$exceeding" -eq 0 ]; then
  echoSuccess "Validating documentation files"
else
  echo ""
  echo "ℹ️ Hint: Start the MD file with \"<!-- $SUPPRESSION -->\" to skip the max lines check"
fi

exit $exceeding
