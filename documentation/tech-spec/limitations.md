# Architectural Limitations

Due to the current design decisions made, there are certain limitations as a consequence.

Some of them might be challenged, some of them are wise to have to **limit complexity**.
Although "the sky is the limit", deliberate limitation keep things simple, doable, and avoid ever increasing complexity.

* User stored entities need to be concrete classes
    * No anonymous implementations (for tests)
    * No "dynamic entities", which differ only in field value(s) but being of the same type (instanceof checks)
* The single owned items of an entity are indistinguishable
    * E.g. user has warriors stored by owned amount, no individual entities with distinct values possible (HP)
