# Implement a basic project website

* Read these instructions, then write a complete HTML file according to the specifications below.
* Write all necessary files into the `/docs` folder.
* Write a HTML file named `index.html`.
* It should serve as a promotion for the game for new users, attract, and invite to download it.
* The title of the page (within the head HTML tag) is "DerBauer2"

The instruction you initially got is as follows:

```text
Use the instructions (consider them also specifications or requirements) provided in the #file:website.md (located at `/documentation/prompts/website.md`). Write the contents into the file #file:index.html  (located at `/docs/index.html`) as mentioned in the given instructions.
```

## Content

The website should include:

* project title
    * with big, bold fonts on top of the page add the title: "DerBauer2"
* tagline
    * beneath the project title add some kind of "subtitle", with smaller font
    * write a text which is catchy, inviting, emotional, inspiring, ... to motivate to play the game.
* a brief description
    * about the project from a user point of view; mention the actions the user is going to do (building, trading, ...)
* application screenshot
    * use the image file located at `/docs/images/screenshot.png`
    * make it positioned in the center of the page
    * adjust the size dynamically so all the content fits on the page without scrolling
        * all the other elements have a static height, only the screenshot can change size, and will to fill up the
          remaining space, until it hits maximum width of the page (then it should scale down accordingly to keep aspect
          ratio)
* application logo
    * embed the image located at  `/docs/images/logo.png`
    * make it overlay over the screenshot at the bottom right corner, with some offset
    * do NOT add any additional border or background to the logo, just the image itself
* download buttons
    * for all three operating systems (windows, macos, linux) provide download buttons
    * the links should point to / image to use for the clickable download buttons:
        * for windows: `https://www.github.com/download/DerBauer2.exe` and `/docs/images/download-windows.png`
        * for macos: `https://www.github.com/download/DerBauer2.dmg` and `/docs/images/download-macos.png`
        * for linux: `https://www.github.com/download/DerBauer2.jar` and `/docs/images/download-linux.png`
    * make all three images the same height of 200px, and arrange them horizontally with some spacing in between.

## Style

* make it visually appealing but keep it simple.
* make it look fun, cartoon like, saturated colors.
* the background like a scenery:
    * blue sky
    * green grass, trees, and hills
    * some moving (animated) clouds
    * the sun in the upper righth corner
* thus at the bottom green, and at the top blue
* use a playful font for headings, and a clean sans-serif font for body text.

## UX

* use some interactive elements, transitions, alpha, hover effects to make it engaging.
* transitions are short and feel quick.
    * play with alpha values and the illusion of depth by using drop shadows.
* make sure the download buttons have a hover effect (like scaling up a bit, or shadow).
* make an element of the page react to the position of the mouse cursor (like the eyes of the sun following the cursor,
  or clouds moving slightly).
* make the background (hills, clouds, etc.) fill the whole page, in width and height, so if scrolling is needed, it
  still looks good.

## Technical

* use CSS for styling, embedded in the HTML file.
* when embedding images, always keep the aspect ratio!
* all tags referring to a file in the `/docs` folder should use relative paths.
    * this means that a file located at `/docs/foo.png` is referenced as`foo.png` in the HTML (`src` tag for example).

## What you should NOT do!

* Don't mention anything about the implementation, programming language, or technical details.
* Don't change the background color when hovering over any element.
* Don't add a text below the download links.
* Don't let any element be invisible due to an alpha glitch or similar.