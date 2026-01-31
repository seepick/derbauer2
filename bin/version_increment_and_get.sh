#!/bin/bash

VERSION_FILE=src/main/distribution/version.txt

if [ ! -f "$VERSION_FILE" ]; then
  echo "ERROR: $VERSION_FILE does not exist!" >&2
  exit 1
fi

content=$(<"$VERSION_FILE")
version=$(printf "%s" "$content" | tr -d ' \t\r\n')

if ! [[ "$version" =~ ^[0-9]+$ ]]; then
  echo "error: version is not a non-negative integer: '$content'" >&2
  exit 1
fi

if [ ! -w "$VERSION_FILE" ]; then
  echo "ERROR: $VERSION_FILE is not writable" >&2
  exit 1
fi

new_version=$((version + 1))

printf "%s\n" "$new_version" > "$VERSION_FILE" || { echo "ERROR: failed to write $VERSION_FILE" >&2; exit 1; }

printf "%s\n" "$new_version"
exit 0
