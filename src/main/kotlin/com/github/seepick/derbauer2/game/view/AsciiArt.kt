package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.multiLine

fun Textmap.asciiart(asciiArt: AsciiArt) {
    multiLine(asciiArt.value)
}

/**
 * See: https://www.asciiart.eu/
 */
@JvmInline
value class AsciiArt(val value: String) {
    companion object {
        val coin = AsciiArt(
            """
                             ______________
                __,.,---'''''              '''''---..._
             ,-'             .....:::''::.:            '`-.
            '           ...:::.....       '
                        ''':::'''''       .               ,
            |'-.._           ''''':::..::':          __,,-
             '-.._''`---.....______________.....---''__,,-
                  ''`---.....______________.....--
            """.trimIndent()
        )
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
        val smiley = AsciiArt(
            """
                  .-""${'"'}${'"'}${'"'}${'"'}${'"'}${'"'}${'"'}${'"'}-.
                 /              `\
                / .--.---.-.-.--.-;.
               ; { ' . '  .  '  .  '}
               | {__'_,__.__'__.__'_}
               |  /    _      _    \
               | ;    / \    / \    ;
               | |    |0|    |0|    |
               \ |    \_/    \_/    |
             .-'\;  \            /  ;
            |.  ' \  '.        .'  /
            \  ` / '.  '-.__.-'  .'
             '--'    '-._    _.-'
                         ''''
            """.trimIndent()
        )
        val sun = AsciiArt(
            """
                        |
                        |
              `.        |        .'
                `.    .---.    .'
                   .~       ~.
                  /   O   O   \
        -- -- -- (             ) -- -- --
                  \    `-'    /
                   ~.       .~
                .'    ~---~    `.
              .'        |        `.
                        |
                        |
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
