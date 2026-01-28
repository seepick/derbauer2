# Generated Code from Documentation

This directory contains Kotlin source code automatically generated from the markdown documentation files in `/documentation`.

## Overview

The generated code follows the existing patterns and style found in `/src/main/kotlin` and implements the game requirements specified in the documentation.

## Generated Components

### Resources (`resource/GeneratedResources.kt`)
Based on `documentation/cleanup/resources.md`:
- **Wood** 🪵 - Used for advanced buildings and armies
- **Stone** 🪨 - Used for advanced buildings and catapult ammunition
- **Knowledge** 📚 - Research points for technology advancement

### Buildings

#### Civic Buildings (`building/GeneratedBuildings.kt`)
Based on `documentation/cleanup/building.md`:
- **Castle** 🏰 - Multi-purpose: stores citizens and food, enables special actions
- **Pub** 🍺 - Increases citizen happiness
- **Church** ⛪ - Increases happiness and improves happening luck
- **Alchemy** ⚗️ - Enables upgrades and reduces upgrade costs
- **Workshop** 🔨 - For crafting and production
- **Town Hall** 🏛️ - Central administrative building
- **Monument** 🗿 - Prestigious building

#### Military Buildings (`building/GeneratedMilitaryBuildings.kt`)
Based on `documentation/cleanup/military.md` and `building.md`:
- **Barracks** ⚔️ - Enables soldier recruitment
- **Stable** 🐴 - Enables knight recruitment, improves cavalry
- **Archery Range** 🏹 - Enables archer recruitment, improves defense
- **Smithy** ⚒️ - For forging weapons and armor
- **University** 🎓 - Research and knowledge building

#### Trading Buildings (`trading/GeneratedTradingBuildings.kt`)
Based on `documentation/cleanup/trade.md`:
- **Marketplace** 🏪 - Improves trade prices and regeneration
- **Trading Post** 🛒 - Enables advanced trading routes
- **Harbor** ⚓ - Enables water trade and army trading

### Military Units (`military/GeneratedArmies.kt`)
Based on `documentation/cleanup/military.md`:
- **Soldier** ⚔️ - Basic unit from barracks
- **Knight** 🐴 - Cavalry from stable, strong vs wildlings
- **Archer** 🏹 - Ranged unit, high defense
- **Catapult** 🎯 - Siege weapon, strong vs empire
- **Angry Farmer** 👨‍🌾 - Cheap militia, convertible back
- **Ram** 🐏 - Siege unit vs buildings/empire
- **Trap Builder** 🪤 - Defensive specialist, zero attack
- **Wizard** 🧙 - Magical unit
- **Scout** 🔭 - Reconnaissance unit

### Technologies/Upgrades (`technology/GeneratedTechnologies.kt`)
Based on `documentation/cleanup/upgrade.md` and `technology.md`:
- **Advanced Agriculture** 🌾 - +20% farm production
- **Improved Storage** 📦 - +25% storage capacity
- **Military Tactics** ⚔️ - +15% attack/defense
- **Trade Routes** 🛤️ - +10% better trade prices
- **Construction Techniques** 🏗️ - -15% building costs
- **Happiness Initiatives** 😊 - +10 citizen happiness
- **Divine Favor** ✨ - +20 karma/luck in happenings

### Features (`feature/GeneratedFeatures.kt`)
Based on `documentation/cleanup/feature.md`:
- **Military Feature** - Enables army recruitment and combat
- **Advanced Trading Feature** - Unlocks advanced trading options
- **Religious Feature** - Enables churches and divine benefits
- **Noble Feature** - Enables castles and nobility
- **Research Feature** - Enables university and technology research
- **Castle Actions Feature** - Enables throne room visitors and quests

### Happenings/Events (`happening/GeneratedHappenings.kt`)
Based on `documentation/cleanup/happenings.md`:

**Positive Events:**
- **Found Treasure** - +50 Gold
- **Received Heritage** - +100 Gold
- **Immigrants Arrive** - +8 Citizens
- **Free Food** - +40 Food
- **Traveling Wizard** - Future: grants free upgrade

**Negative Events:**
- **Rats Eat Food** (existing) - Food loss
- **Drought** - -30 Food, reduced production
- **Storm** - -20 Food, -5 Citizens
- **Plague** - -10 Citizens
- **Bandits Attack** - -40 Gold, -15 Food

## Code Style and Patterns

All generated code follows these patterns from the existing codebase:

1. **Resource Pattern**: Implements `Resource` or `StorableResource` interface
   - Has companion `Data` object implementing `HasLabel` and `HasEmoji`
   - Uses `Z` type for numeric values
   - Implements `deepCopy()` and `toString()`

2. **Building Pattern**: Implements `Building` interface
   - Has `costsGold` and `landUse` properties
   - May implement additional interfaces like `StoresResource` or `ProducesResourceOwnable`
   - Uses delegation for label and emoji data

3. **Military Pattern**: Implements `Army` interface
   - Has costs (gold and citizens), attack, and defense stats
   - Follows same data delegation pattern

4. **Technology Pattern**: Implements `Technology` interface
   - Has cost and bonus properties
   - Immutable data with proper copying

5. **Feature Pattern**: Extends `Feature` abstract class
   - Uses descriptor pattern with condition checks
   - Implements feature data delegation

6. **Happening Pattern**: Implements `Happening` interface
   - Has nature (Positive/Negative/Mixed/Neutral)
   - Implements render and execute methods

## Integration

To integrate generated code with the main application:

1. **Register in Module files**: Add generated classes to appropriate registry files
   - Features: Add descriptors to `FeatureDescriptor.all` list
   - Buildings: Register in building modules
   - Happenings: Add to happening registry

2. **Update Mechanics**: If needed, add constants to `Mechanics` object for balance

3. **Add Tests**: Create corresponding test files following existing test patterns

## Running the Generated Code

Run the demonstration main:
```bash
./gradlew run -PmainClass=com.github.seepick.derbauer2.generated.GeneratedMainKt
```

Or compile to verify:
```bash
./gradlew compileKotlin
```

## Documentation Sources

- `/documentation/cleanup/resources.md` - Resource types and mechanics
- `/documentation/cleanup/building.md` - Building types and effects
- `/documentation/cleanup/military.md` - Army units and combat
- `/documentation/cleanup/technology.md` - Technology system
- `/documentation/cleanup/upgrade.md` - Upgrade effects
- `/documentation/cleanup/feature.md` - Feature conditions and enablers
- `/documentation/cleanup/happenings.md` - Random events
- `/documentation/cleanup/trade.md` - Trading mechanics
- `/documentation/general.md` - Overall game design principles

## Notes

- Generated code uses the same package structure as existing code: `com.github.seepick.derbauer2.game.*`
- All emojis are properly encoded and follow the existing `.emoji` pattern
- Numeric values use the `.z` extension for the custom `Z` type
- Documentation comments reference the source markdown files
