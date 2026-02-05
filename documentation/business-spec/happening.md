# Happening

* aka Events: probability each end-turn
* structure:
    * one or multiple pages (sequence/flow)
    * a happening (e.g. visitor arrives) can have sub-types (e.g. good/bad visitor)
* interaction nature:
    * simple notification (read and continue)
    * multiple choice
* properties:
    * title: string
    * to provide diversity during probability calcuation:
        * impact: { negative, positive, neutral, complex }
        * category: { economical, social, planetary, military, other }
    * happenings are dependent on ... existence of feature (or more?)
* Affected by:
    * intensity adjusted (mechanics) by attraction for/resistence to
        * buildings: e.g. defense structure for bandits
        * tech (feature): e.g. better granary for rats
            * stats: karma/luck, wealth, happyness
            * previous behavior (use Business Intelligence / History data)
* Effects:
    * +/- resource/building/army
    * +/- resource prod
        * can provide a temporary effect/modifier
            * e.g. on different stats like health (plague)
            * productivity of buildings (workers joined for some turns)
* requirements: only able to be happening under certain conditions...
    * rats only when food concept exists
    * when military: getting attacked by foreign armies
    * when certain buildings exist ...

## Examples

Economical:

* gold bag/treasure
    * +gold
* rats eaten
    * -food
        * eaten amount depends on amount of stored food/capacity?
    * IDEA: change likelyhood if ...
        * more in winter season
        * lots of food/capacity
        * no buildings/features/tech which protect from it
* heritage
    * +gold (+building/land)
    * a one-time event!
* bandits/theft

Social:

* audience:
    * in the throne room to your citizens
    * multiple small issues raised
* help wanted
    * being asked for help, multiple choice
    * accept: +karma, +people; -gold/food
    * refuse: -karma, -people
* visitors arrive
    * traveller/doctor/philosopher/magician
    * can be good/bad:
        * takes away people: -people
        * e.g. tells a secret: get free upgrade
* migration
    * group of people arrives/flees: +/-people
    * immigrants: if happyness (and karma) is high, free people move in
* marriage / birth
    * if dies (because of age) option to continue by day 1 but with same state

Military:

* armies join: +army
* bandits attack
    * -gold/food/people/building
    * less impactful if army present

Planetary:

* weather
    * drouth (duerre): -food prod (not food itself!)
    * storm: -food, -people, -building
* disease/sickness (plague)
    * -people!!!

## Ideas

* ? happenings mit selber viel choices (wie action game)
    - happenings auch waehrend dem turn (evtl durch action triggered)
    - oder einfach random wenn im menue rumklicken
    - OR: are happenings always PASSIVE only?!?
* long lasting happenings
    - e.g.: blessing from farm good increases farm productivity for X turns
    - affects: income, prod, efficiency, military
* when land is lost, building(s) will be destroyed if not enough free land
* reisender gibt schriftrolle fuer free tech
* new happening: random people/army join; make happening#condition() .. check whether player good capacity/necessary
  features

- people die (murder/crime/krankheit)
- free food
- some people/armies join
