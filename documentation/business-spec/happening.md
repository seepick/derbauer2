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
    * happenings base values are dynamically calculated (all time max; then low-med-high "packages")
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
    * amount with probability range { lil=5%, med=10%, big=20% } + diffuser; limitted
        * base value = 10% of all time max resource; round to 10s/100s/...
        * apply to existing happenings
    * TODO: make HappeningNature.happinessImpact more sophisticated
* requirements: only able to be happening under certain conditions...
    * rats only when food concept exists
    * when military: getting attacked by foreign armies
    * when certain buildings exist ...
* TODO: PoorPhilosopherHappening needs refactoring
* TODO: happening with random outcome (success; damage; intensity); gambling...
* enable happenings based on "things"; e.g. military happenings (being attacked, etc.) only if have feature

## Ideas

* happenings ask for major decisions, impacting the story line, having influence on game mechanics
* make poor philospher interactive; being asked Y/N
    * -gold +happiness, basically special trade into stats with consequences
    * !!! requires refactoring of happening rendering and multi-view-page thing...
* happening festival: Y/N (+happy, -food); or just regular always possible action?
    * lasts for X turns; decreases lifetime -1; not stat %-modifier but constant-addition
* new season dependent happening; e.g. in autumn bigger harvest; people freeze in winter; ...
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
