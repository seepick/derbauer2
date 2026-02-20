# Inspiration

Other similar games...

General notes:

* some have (local) multiplayer feature
* in beginning, ask if want introduction/tutorial
    * if implement this, then store in "memory.yetSeen" (and provide info-button in toolbar)
* choose difficulty level

# Examples

## Commodore 64

* Armut & Reichtum
    * PLAY: https://www.commodoregames.net/Commodore64/Armut-&-Reichtum-413.html
    * story line: you are rule of a poor house in 13th cent; goal is to become the king
    * high taxes reduce economical growth (production rates)
    * turn report: citizen birth/death; army upcos
    * buy/sell food/land; rats eat % food; food consumption number given, choose how many feed
    * different taxes: zoll, einkommen, verkauf; changing rate immediate prognosis of change
    * gerichtswesen: gnadig, fair, ungerecht, bestechlich (crime?)
    * buildings: markt (+gold), muehle (++gold), palast (partly), kirche (partly), armee
    * land in m2 (produces food)
    * citizen types: hoeflinge, leibeigene (regular), kirche klerus, haendler, soldaten
    * 🎨 every rerender action, will build screen by-row from top2bottom in a smooth animation
        * cute, but slows down interaction drastically
    * current weather conditions
    * interaction sequence (🎨 background color changes per page)
        * 1: manage food/land (buy/sell); prices change dynamically
        * 2: feed people food (must keep min. 25% of food); dictates birth/death rate
        * 3: turn report: birth/death, income/upcome
        * 4: tax adjust (total income); gericht
        * ... some weird map; army; castle ...
        * 5: build buildings
* Kingdom ☹️
    * PLAY: https://www.commodoregames.net/Commodore64/Kingdom-15721.html
    * super minimalistic; blue bg color; german
    * saecke korn (zum essen/anbauen), bauern, hektar anbauflaeche
    * interaktions sequenz: wieviel anbauen? wieviel verfuettern? ... step-by-step
    * schlecht: unklar mechanic (proportionen), entscheidungsfindung unmoeglich (frustrierend)
        * in info wird erklaert die "unangenehme" komplexitaet der mechanik (muss intuitiv sein!)
    * end turn: report production, starvation; happening (random events)
* Kaiser II:
    * PLAY?!: https://www.commodoregames.net/Commodore64/Kaiser-II-4022.html ... doesn't run?!
    * Ernte (% verfaulen), Taler
    * trade korn/land
    * kredit aufnehmen/abzahlen; zinsen bekommen von investment

## Classics

* [Die Fugger II](fugger2.md) ‼️‼️‼️
* [Civilisation](civilization.md) ‼️‼️‼️
* Anno
* Age of Empires
* Settlers

## Modern Browser Games

* [Viktorian Idle](victorian-idle.md) ‼️‼️‼️
* Warsim
    * very big, nice game (5 USD, win only)
    * Website: https://huw2k8.itch.io/warsim
    * Gameplay: https://www.youtube.com/watch?v=xcnHcXXRzlI
* the dark ages
    * very tiny game, python
    * https://github.com/haydenhughes/the-dark-ages
* open dominion
    * average size, PHP
    * https://github.com/WaveHack/OpenDominion
* hamurabi
    * lil game, JS
    * https://github.com/skypanther/Hamurabi/blob/master/Resources/common/hamurabi.js#L62
* villager kings
    * https://xamer.itch.io/villager-kings
* civ clicker
    * https://civclicker.sourceforge.net/civclicker/civclicker.html

* text based upgrade game (simple UI):
    * https://adarkroom.doublespeakgames.com/
    * https://www.decisionproblem.com/paperclips/index2.html
    * https://kittensgame.com/web/#

## Collections

* https://github.com/search?q=text+strategy+game
* https://www.ifarchive.org/indexes/if-archiveXgames.html
