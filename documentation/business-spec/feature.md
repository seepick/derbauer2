# Feature

* synonyms: condition, option, possibility, modifier
* something that allows for a new user interaction (building, army, ...)
    * it's NOT for mutation of the system without user benefit; e.g. changes of user/city title not!
* about / essence:
    * internal "switches" to allow user new possibilities/interactions
    * similar to tech, but they are passive; or... is tech enabling a feature?!
        * Conditions are certain criteria in order to allow other things to happen/be available
* effects:
    * enables:
        * buildings, military (army/action), actions, happenings, trade (new) resources
        * also: achievements (if x feature is enabled, achievement y can be earned)
    * changes the modifiers (probability calculation)
* ad Turner enabling logic: could do it way more sophisticated
    * this would logically correct, BUT: way more complex (worth the effort?); also maybe too much UI/UX wise?!
    * TX-exec one, could later allow another to check==true
        * thus: do rounds of applying, until no feature returns check==true anymore
        * e.g.: TradingFeature enables TradeLandFeature; should BOTH appear in same turn report

# Trigger

* e.g. certain resources exceed threshold, or capacity running low
    * e.g. when land is getting low, receive feature to buy/trade land for money
* Depends on:
    * availability of entities: people, building, achievement
    * history! how often something happened so far

## Types

* Trading
    * check logic: look for turns played (and/or money owned?)
