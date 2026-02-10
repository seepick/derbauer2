#!/bin/bash

# ensure CWD is project root
CWD=$(pwd)
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

# ensure drawio desktop is installed (mac: brew cask install drawio)
DRAWIO_BIN="/Applications/draw.io.app/Contents/MacOS/draw.io"
SOURCE_DIR="documentation"

generate() {
    SOURCE_FILE=$1
    FILENAME=`basename $SOURCE_FILE`
    BASEDIR="$(dirname "$SOURCE_FILE")"
    OUT_FILENAME="${FILENAME%.drawio}.png"
    OUT_FILE="$BASEDIR/$OUT_FILENAME"
    CMD="$DRAWIO_BIN --export --format png --output $OUT_FILE $SOURCE_FILE"
    echo "   ... Generating image for diagram: $FILENAME"
    `$CMD`
}

echoH1 "📊  Generating images from draw.io diagrams"
echoParam "📁  Source directory" $SOURCE_DIR
echoParam "💾  Draw.io binary" $DRAWIO_BIN
echo -e "${COL_ITALIC}(You can safely ignore the printed 'Permission denied' messages 🤭)${COL_RESET}"
verifyExists $DRAWIO_BIN
echo ""

while IFS= read -r -d '' diagram; do
  generate "$diagram"
done < <(find "$SOURCE_DIR" -type f -name "*.drawio" -print0)

echoSuccess "Generating diagram images"

# draw.io command line options:
#  -V, --version                      output the version number
#  -c, --create                       creates a new empty file if no file is passed
#  -x, --export                       export the input file/folder based on the given options
#  -r, --recursive                    for a folder input, recursively convert all files in sub-folders also
#  -o, --output <output file/folder>  specify the output file/folder. If omitted, the input file name is used for output with the specified format as extension
#  -f, --format <format>              if output file name extension is specified, this option is ignored (file type is determined from output extension) (default: "pdf")
#  -q, --quality <quality>            output image quality for JPEG (default: 90)
#  -t, --transparent                  set transparent background for PNG
#  -e, --embed-diagram                includes a copy of the diagram (for PNG format only)
#  -b, --border <border>              sets the border width around the diagram (default: 0)
#  -s, --scale <scale>                scales the diagram size
#  --width <width>                    fits the generated image/pdf into the specified width, preserves aspect ratio.
#  --height <height>                  fits the generated image/pdf into the specified height, preserves aspect ratio.
#  --crop                             crops PDF to diagram size
#  -a, --all-pages                    export all pages (for PDF format only)
