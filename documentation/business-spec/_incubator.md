# Incubator of Ideas

* Supply Chain and Logistics (factorio)
    * buildings consume to produce other (higher) resources; respect ratio
* Financial System
    * allows to adjust tax rate (gold/happiness tradeoff)
* Banking
    * allows finanicial actions (borrow/invest money, ...)
* Religion
    * atheism, monotheism, polytheism; mixed allowed
    * effects: happiness, production, military, ...
* Policy: change laws
    - taxes (gambling, banking, land, military)
    - +gold prod; -happyness/people prod
    - feature enabled when ... upgrade "politics" + building "tax office"
* Diplomacy
    * mit verschiedene voelker, relationship metrik
    * actions: buy/sell troops, handeln/schenken/verlangen, buendnis, ...
    * other nations: info about resources, army, ....
        - diplomacy, spy, war
        - trade agreement, send gift, hire troops, alliance
        - happening: get free stuff from other; get requested to donate/help/escort (borrow miliatry units)
* Management (automation)
    * hire manager delegate, which will for example: auto trade sell food if capacity at 80%
* text-to-speech :)

# Fixed Ideas

# History

* store statistical data of misc **Action**s/events (any kind of user-interaction/transaction)
    * like in an event-driven system, dispatch messages in a permament store/queue
    * needs to be that dynamic, as of the decopuled nature of the application's architecture
* calculations can analyze afterwards to gain insight over-time about user behavior
* used to check for conditions for achievements/features
* E.g.:
    * trade: per item, buy/sell, amount, total gold spent/earned (on something specific)
    * military: attacks won/lost
    * build: amount buildings, ratio of type of buildings (more military/economical/social)
    * happening: amount, by type
    * personal perferences: certain building/unit type often used
    * maybe even UI interaction (e.g. entered menu x times)

## Skill

* based on information of what things user did in past, can calculate "experience points"
* effects:
    * improves that certain skill even more
        * e.g. trade a lot -> better prices
    * unlock features, gain achievements
* e.g. global military expertize: each time attack, it increases and leads to improved global att/def

## Interaction

* about: keep track how user interacts with the game
    * like a message queue/event store
* e.g. traded x-times, build y-times, attacked z-times, ... did mini games, ... past decisions.

## Achievement

* about / essence:
    * a progression based happening
    * some kind of "XP reward"
    * similar to feature/tech maybe
    * relies on historical data
* get free stuff with it (otherwise just a meaningless cosmetic)
    * gold/food/land/buildings (if sufficient storage/land available)

Triggered by:

* resources: total produced/owned
* buildings: total build/owned
* trades: buy/sell amount, money spent/earned
    * e.g. when traded x-times, then get reward as in achievement being the ~"capitalism guru"
* military armies: total hired/owned
* attacks: attack VS raid, won/lost/total
* happenings: good vs bad
* days (each week, month, year), give special reward
* ui-interation ;) e.g. having entered the build menu x-times
* ... differentiate between: by type VS global

## Start Game

* Start game sequence
    * show game title screen: nice ASCII art, welcome text, credits, version, date
    * choose: gender, name, race
    * select difficulty
    * intro sequence: prolog with explanatory text
    * tutorial, basic UI and controls explained
* komplexe charaktere wahl (rasse, nation/fraktion, geschlecht, ...)
    - mittelalter theme beibehalten: galier, roemer, griechen, egypter
    - effects: military, forschung, trade, production, prices, ...
