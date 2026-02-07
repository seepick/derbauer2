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
            * e.g. police station for lower crime (stat: safety)
            * e.g. hospital to lower diseases/breakout (stat: health)
        * tech (feature): e.g. better granary for rats
            * stats: karma/luck, wealth, happiness
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
* random people/army join; make happening#condition() .. check whether player good capacity/necessary
  features
* when in autumn, possibility of "saison ernte" happening
