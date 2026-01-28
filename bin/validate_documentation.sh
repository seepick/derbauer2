#!/bin/sh

# check `documentation/*.md` if any more than max lines

MAX_LINES=100
exceeding=0
checked=0
tmp=$(mktemp) || exit 1

find documentation -type f -name '*.md' > "$tmp"
while IFS= read -r file || [ -n "$file" ]; do
  lines=$(wc -l < "$file" | tr -d ' ')
  checked=$((checked + 1))
  if [ "$lines" -gt "$MAX_LINES" ]; then
    printf '❌  Too long file: %s --> %s lines exceeds max of %s\n' "$file" "$lines" "$MAX_LINES"
    exceeding=$((exceeding + 1))
  fi
done < "$tmp"
rm -f "$tmp"

echo ""
printf 'Script done: %d out of %d files are invalid\n' "$exceeding" "$checked"
if [ "$exceeding" -eq 0 ]; then
  printf '✅ All documentation files are valid.\n'
fi

exit $exceeding
