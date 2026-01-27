package com.github.seepick.derbauer2.game.view

@JvmInline
value class AsciiArt(val value: String) {
    companion object {
        val island = AsciiArt(
            """
                   _  _             _  _
          .       /\\/%\       .   /%\/%\     .
              __.<\\%#//\,_       <%%#/%%\,__  .
        .    <%#/|\\%%%#///\    /^%#%%\///%#\\
              ""/%/""\ \""//|   |/""'/ /\//"//'
         .     L/'`   \ \  `    "   / /  ```
                `      \ \     .   / /       .
         .       .      \ \       / /  .
                .        \ \     / /          .
           .      .    ..:\ \:::/ /:.     .     .
        ______________/ \__;\___/\;_/\___________
        YwYwYwYwYwYwYwYwYwYwYwYwYwYwYwYwYwY
    """.trimIndent()
        )

        val gameOver = AsciiArt(
            """
          ____
        ,'   Y`.
       /        \
       \ ()  () /
        `. /\ ,'
    8====| "" |====8
         `LLLU'
    """.trimIndent()
        )
    }
}
