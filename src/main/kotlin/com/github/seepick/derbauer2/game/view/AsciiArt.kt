package com.github.seepick.derbauer2.game.view

/**
 * See: https://www.asciiart.eu/
 */
@JvmInline
value class AsciiArt(val value: String) {
    companion object {
        val book = AsciiArt(
            """
                  __...--~~~~~-._   _.-~~~~~--...__
                //               `V'               \\ 
               //                 |                 \\ 
              //__...--~~~~~~-._  |  _.-~~~~~~--...__\\ 
             //__.....----~~~~._\ | /_.~~~~----.....__\\
            ====================\\|//====================
                                `---`
            """.trimIndent()
        )
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
