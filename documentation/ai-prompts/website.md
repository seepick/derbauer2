# Implement a basic project website

* Read these instructions, then write a complete HTML file according to the specifications below.
* Write all necessary files into the `/docs` folder.
* Write a HTML file named `/docs/index.html`.
* It should serve as a promotion for the game for new users, attract, and invite to download it.
* The title of the page (within the head HTML tag) is "DerBauer2"

The instruction you initially got is as follows:

```text
Use the instructions (consider them also specifications or requirements) provided in the #file:website.md (located at `/documentation/prompts/website.md`). Write the contents into the file #file:index.html  (located at `/docs/index.html`) as mentioned in the given instructions.
```

## Content

The website should include:

* project title
    * on top of the page add the title: "DerBauer2"
    * use a big, bold font with some dropshadow
* tagline
    * beneath the project title add some kind of "subtitle", with smaller font
    * write a text which is catchy, inviting, emotional, inspiring, ... to motivate to play the game.
* a brief description
    * about the project from a user point of view; mention the actions the user is going to do (building, trading, ...)
* application screenshot
    * use the image file located at `/docs/images/screenshot.png`
    * make it positioned in the center of the page
    * adjust the size dynamically so all the content fits on the page without scrolling
        * only the screenshot can change size, all the other elements have a static height
        * it will to fill up the remaining space, until it hits maximum width of the page
        * when hit maximum width of the page, then it should scale down accordingly to keep aspect ratio
    * do NOT apply any hover-effects to it; also NO border or dropshadow around it
* application logo
    * embed the image located at: `/docs/images/logo.png`
    * position it on top of the screenshot, so that it covers up some of it, at the bottom right corner
        * be VERY careful about doing this!
        * if the page resizes, the logo always stays relatively at the same spot with the screenshot
        * use z-index or similar techniques to achieve this
        * do NOT add any additional border or background to the logo, just the image itself
* download buttons
    * for all three operating systems (windows, macos, linux) provide download buttons
    * have a title text indicating to download the game
        * the links should point to / image to use for the clickable download buttons:
            * for windows: `https://github.com/seepick/derbauer2/releases/latest/download/DerBauer2.exe` and
              `/docs/images/download-windows.png`
            * for macos: `https://github.com/seepick/derbauer2/releases/latest/download/DerBauer2.dmg` and
              `/docs/images/download-macos.png`
            * for linux: `https://github.com/seepick/derbauer2/releases/latest/download/DerBauer2.deb` and
              `/docs/images/download-linux.png`
    * make all three images the same height of 200px, and arrange them horizontally with at least 60px in between.
* add a link to the sourcecode in the footer (bottom right corner) of the page:
    * use the `/docs/images/github-logo.png` image as an icon; make it the clickable link, no text rendered
    * link: `https://github.com/seepick/derbauer2`
    * tooltip: "View the source code on GitHub"
* add a very subtle disclaimer that the page was created by AI.
    * use a small font, with color which is semi-transparent.

## Style

* make it visually appealing but keep it simple.
* make it look fun, cartoon like, saturated colors.
* the background like a scenery:
    * blue sky; green grass, trees, and hills; the sun in the upper right corner
    * thus at the bottom green, and at the top blue
    * in the middle draw a castle, grey walls, red rooftop.
* use a playful font for headings, and a clean sans-serif font for body text.

## UX

* make the background (hills, clouds, etc.) fill the whole page, in width and height.
* use some subtle interactive elements, transitions, alpha, hover effects to make it engaging.
    * transitions are short and feel quick.
* make sure the download buttons have a hover effect, slightly scale up size (but NO dropshadow or glossy shiny thing).
* no scrollbars are visible at any time, the whole content fits on the screen.

## Technical

* use CSS for styling, embedded in the HTML file.
* when embedding images, always keep the aspect ratio!
* all tags referring to a file in the `/docs` folder should use relative paths.
    * this means that a file located at `/docs/foo.png` is referenced as`foo.png` in the HTML
    * e.g. for images change the `src` attribute accordingly

## What you should NOT do!

* Don't mention anything about the implementation, programming language, or technical details.
* Don't change the background color when hovering over any element.
* Don't add a text below the download links.
* Don't let any element be invisible due to an alpha glitch or similar.
* Don't add a drop shadow to the combined box around the screenshot and the application logo.
* Don't add boxes around elements; e.g. around download buttons, images, texts, etc.
    * keep the page elements clean and simple, placed directly on the background.
