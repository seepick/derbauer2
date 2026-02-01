#!/bin/bash

# Usage: bin/version_bump.sh {major|minor|patch}
# ------------------------------------------------------------------
# *) take argument: { major | minor | patch }
# *) read version from file, increment it by 1
# *) save new version back and return it to stdout for further processing

ARG="${1:-}"

if [ -z "$ARG" ]; then
  echo "ERROR: missing argument. Use one of: \`major\`, \`minor\`, \`patch\`" >&2
  exit 1
fi

case "$ARG" in
  major|minor|patch) ;;
  *)
    echo "ERROR: invalid argument '$ARG'. Use one of: \`major\`, \`minor\`, \`patch\`" >&2
    exit 1
    ;;
esac

VERSION_FILE=src/main/distribution/version.txt

if [ ! -f "$VERSION_FILE" ]; then
  echo "ERROR: $VERSION_FILE does not exist!" >&2
  exit 1
fi

if [ ! -r "$VERSION_FILE" ] || [ ! -w "$VERSION_FILE" ]; then
  echo "ERROR: $VERSION_FILE is not readable/writable" >&2
  exit 1
fi

content=$(<"$VERSION_FILE")
version=$(printf "%s" "$content" | tr -d ' \t\r\n')

if ! [[ "$version" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
  echo "ERROR: version in $VERSION_FILE is not a semantic version (MAJOR.MINOR.PATCH): '$content'" >&2
  exit 1
fi

IFS='.' read -r major minor patch <<< "$version"

case "$ARG" in
  major)
    major=$((major + 1))
    minor=0
    patch=0
    ;;
  minor)
    minor=$((minor + 1))
    patch=0
    ;;
  patch)
    patch=$((patch + 1))
    ;;
esac

new_version="${major}.${minor}.${patch}"

printf "%s\n" "$new_version" > "$VERSION_FILE" || { echo "ERROR: failed to write $VERSION_FILE" >&2; exit 1; }

printf "%s\n" "$new_version"
exit 0
