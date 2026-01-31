# Interface

## Guidelines

* text-mode interface (like oldschool roguelikes)
* keyboard only (no mouse)
* single window (no multiple windows/tabs)
* simple navigation (minimize key combos, e.g. no ctrl/alt/shift combos)
* minimalistic design (no fancy graphics, just text, and simple colors; sometimes ASCII art)

## Prompt

* the prompt is the main interaction element of the game
* select options by number
    * max 9 items (1-9); needs runtime check as options are generated dynamically :-/
    * IDEA: possibility to "[0] next page" if more than 9 options? (either dynamically created, or manually
      constructed/grouped)
* allow for free numbers entered (including magnituted suffix to enable "123k")
* NO: use arrow up/down
* NO: mouse support (max. for tooltips; maybe for sound controls later)

# Ideas

* overlay info notifier/window/toast for InteractionResult.Failure (or keep it simple?)
* set icon in Dock (iconFile in build.gradle)
* set icon of window
* continuous bg music; medieval; each age different theme?! how to configure? overlay menu?!
* immediate print result of action; e.g.: bought farm => total food production increase from X to Y
* zu jeder option immer dazuschreiben ihren effekt
* print header ascii art for each section (build/trade/military/tech/feature/happening/achievement)
* when warning overlay (gruenes glasspane) ++ go other screen fast => immediate! close (cancel the timeout)
    - will man das denn ueberhaupt?! sonst geht evtl (unabsichtlich) der screen schnell weg bevor gelesen
* affordable amount schon in uebersicht anzeigen, nicht erst in sub view
    - will man das denn ueberhaupt?! zuviele (irrelevante) daten aufeinmal
* feedback bei aktion (zb gebaeude gebaut)
* colored text? (build annotated string necessary for compose text...)
    * like ctrl-codes in shell: <red> <green> <bold> tags
    * process in renderer and remember positions and style for compose annotations
* dynamic window size, adjust so it correlates with textmap size (font measure size needed)
