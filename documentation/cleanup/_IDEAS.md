
# ideas

* the bigger the kingdom (more citizens), at certain thresholds, earn title: village, town, city, ...
* es gibt sowas wie "epochen"; zb: renessaince, ...
* change data model: buyable is generic; concrete is: tradable, buildable, hirable, upgradable, ...
* mittelalterliche BG music
* very detailed end-turn report (was weg/was dazu, warum/woher)
    - aus verschiedene bereiche/sectioned (economic, military, ...)
    - gibt stats aus ueber production/upcome/.../achievements/...
* feedback bei aktion (zb gebaeude gebaut)
* gebaeude abreissen: kostet gold
* rename "people" => "peasants"
* komplexe charaktere wahl (rasse, nation/fraktion, geschlecht, ...)
    - mittelalter theme beibehalten: galier, roemer, griechen, egypter
    - effects: military, forschung, trade, production, prices, ...
* idea ad action: can hire manager delegate, which will for example: auto trade sell food if capacity at 80%
* NICE: a la fugger: marry - birth - replay
    - sterbe rate von player steigt mit steigendem alter drastisch
    - auf wieviele jahre beschraenken? wie hundejahre * 7 zb... also nach 10 jahren ist todeswahrscheinlichkeit schon sehr hoch
* highscore

## I/O

* immediate print result of action; e.g.: bought farm => total food production increase from X to Y
* zu jeder option immer dazuschreiben ihren effekt
* print header ascii art for each section (build/trade/military/upgrade)
* show options which are not available yet "x)"
* when warning overlay (gruenes glasspane) ++ go other screen fast => immediate! close (cancel the timeout)
    - will man das denn ueberhaupt?! sonst geht evtl (unabsichtlich) der screen schnell weg bevor gelesen
* * affordable amount schon in uebersicht anzeigen, nicht erst in sub view
    - will man das denn ueberhaupt?! zuviele (irrelevante) daten aufeinmal
* IDEE: colored output

# New concepts

* experience points
  - military, trade (economical)
  - gets you achievements (permanent upgrades)
  - enables new stuff
* religion
* change laws
  - taxes (gambling, banking, land, military)
  - +gold prod; -happyness/people prod
  - feature enabled when ... upgrade "politics" + building "tax office"
* diplomacy
  * mit verschiedene voelker, relationship metrik
  * actions: buy/sell troops, handeln/schenken/verlangen, buendnis, ...
  * other nations: info about resources, army, ....
    - diplomacy, spy, war
    - trade agreement, send gift, hire troops, alliance
    - happening: get free stuff from other; get requested to donate/help/escort (borrow miliatry units)

# Fancy ideas

* multiplayer via network
* time-based, each 1sec = 1 tick

# Start game sequence

1. show game title screen: nice ASCII art, welcome text, credits, version, date
1. choose: gender, name, race
1. select difficulty
1. intro sequence: prolog with explanatory text
1. (might skip) tutorial, basic UI and controls explained