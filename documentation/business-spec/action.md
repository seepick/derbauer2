# Action

* about: keep track how user interacts with the game
    * like a message queue/event store
* e.g. traded x-times, build y-times, attacked z-times, ... did mini games, ... past decisions.
* certain "special activities" which can can be executed by user
* available only in certain conditions
    * feature, building, resources, ...
* each turn can only be done X times -> motivate player to do it each turn :)
* refreshes end of turn
* amount depends on "certain things", e.g. buildings, happiness (to visit the emperor)
* some action got maximum count (or max 1 anyway)
* special one-time actions
    * long lasting effects (see happening as well)
    * (how is it different from enable feature/tech?)

## History

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
