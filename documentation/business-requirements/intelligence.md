# Intelligence

* also "business intelligence", based on (turn) reports and more
* holds (historical) data of anything relevant that happened:
  * turn reports (contains production/consumption, happenings)
  * user interactions (choices made)
* function:
  * basis for probability calculations
  * implements the mechanics of the game, modifies bases
  * uses stats and modifiers; user.all data of course
* e.g.
  * when is it time for a happening to happen (cooldown calculation + probability + modifiers)

## Idea: Actions

* introduce `ActionBus`; every user interaction recorded there as `Action` (==event)
* an action thus is like an event, like in an event-driven system
  * advantage: replayability, history, intelligence :)