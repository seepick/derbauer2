package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.textengine.Textmap

/**
 * Gold treasure found - positive happening.
 * Based on documentation/cleanup/happenings.md
 */
class FoundTreasure : Happening {
    override val nature = HappeningNature.Positive
    override val asciiArt = AsciiArt.Treasure

    override fun render(textmap: Textmap) {
        textmap.line(">> Found Treasure! <<")
        textmap.emptyLine()
        textmap.line("Your citizens discovered a hidden treasure chest!")
        textmap.line("You gained 💰 50 Gold.")
    }

    override fun execute(user: User) {
        user.get<Gold>().add(50.z)
    }
}

/**
 * Heritage received - positive happening.
 * Based on documentation/cleanup/happenings.md
 */
class ReceivedHeritage : Happening {
    override val nature = HappeningNature.Positive
    override val asciiArt = AsciiArt.Gold

    override fun render(textmap: Textmap) {
        textmap.line(">> Heritage Received! <<")
        textmap.emptyLine()
        textmap.line("A distant relative has left you an inheritance!")
        textmap.line("You gained 💰 100 Gold.")
    }

    override fun execute(user: User) {
        user.get<Gold>().add(100.z)
    }
}

/**
 * Drought - negative happening affecting food production.
 * Based on documentation/cleanup/happenings.md
 */
class Drought : Happening {
    override val nature = HappeningNature.Negative
    override val asciiArt = AsciiArt.Happening

    override fun render(textmap: Textmap) {
        textmap.line(">> Drought! <<")
        textmap.emptyLine()
        textmap.line("A severe drought has struck your lands.")
        textmap.line("Food production is reduced this turn.")
        textmap.line("You lost 🍖 30 Food.")
    }

    override fun execute(user: User) {
        user.get<Food>().remove(30.z)
    }
}

/**
 * Storm - negative happening affecting multiple resources.
 * Based on documentation/cleanup/happenings.md
 */
class Storm : Happening {
    override val nature = HappeningNature.Negative
    override val asciiArt = AsciiArt.Happening

    override fun render(textmap: Textmap) {
        textmap.line(">> Storm! <<")
        textmap.emptyLine()
        textmap.line("A terrible storm has ravaged your settlement.")
        textmap.line("You lost 🍖 20 Food and 🧑 5 Citizens.")
    }

    override fun execute(user: User) {
        user.get<Food>().remove(20.z)
        user.get<Citizen>().remove(5.z)
    }
}

/**
 * Plague - negative happening affecting citizens.
 * Based on documentation/cleanup/happenings.md
 */
class Plague : Happening {
    override val nature = HappeningNature.Negative
    override val asciiArt = AsciiArt.Happening

    override fun render(textmap: Textmap) {
        textmap.line(">> Plague! <<")
        textmap.emptyLine()
        textmap.line("A plague has spread through your settlement!")
        textmap.line("You lost 🧑 10 Citizens.")
    }

    override fun execute(user: User) {
        user.get<Citizen>().remove(10.z)
    }
}

/**
 * Immigrants arrive - positive happening adding citizens.
 * Based on documentation/cleanup/happenings.md
 */
class ImmigrantsArrive : Happening {
    override val nature = HappeningNature.Positive
    override val asciiArt = AsciiArt.Citizen

    override fun render(textmap: Textmap) {
        textmap.line(">> Immigrants Arrive! <<")
        textmap.emptyLine()
        textmap.line("A group of settlers has heard of your prosperous realm!")
        textmap.line("They wish to join your settlement.")
        textmap.line("You gained 🧑 8 Citizens.")
    }

    override fun execute(user: User) {
        user.get<Citizen>().add(8.z)
    }
}

/**
 * Bandits attack - negative happening.
 * Based on documentation/cleanup/happenings.md
 */
class BanditsAttack : Happening {
    override val nature = HappeningNature.Negative
    override val asciiArt = AsciiArt.Happening

    override fun render(textmap: Textmap) {
        textmap.line(">> Bandits Attack! <<")
        textmap.emptyLine()
        textmap.line("Bandits have raided your settlement!")
        textmap.line("You lost 💰 40 Gold and 🍖 15 Food.")
    }

    override fun execute(user: User) {
        user.get<Gold>().remove(40.z)
        user.get<Food>().remove(15.z)
    }
}

/**
 * Free food found - positive happening.
 * Based on documentation/cleanup/happenings.md
 */
class FreeFood : Happening {
    override val nature = HappeningNature.Positive
    override val asciiArt = AsciiArt.Food

    override fun render(textmap: Textmap) {
        textmap.line(">> Bountiful Harvest! <<")
        textmap.emptyLine()
        textmap.line("Your farmers have had an exceptionally good harvest!")
        textmap.line("You gained 🍖 40 Food.")
    }

    override fun execute(user: User) {
        user.get<Food>().add(40.z)
    }
}

/**
 * Traveling wizard - neutral/positive happening.
 * Based on documentation/cleanup/happenings.md
 */
class TravelingWizard : Happening {
    override val nature = HappeningNature.Positive
    override val asciiArt = AsciiArt.Happening

    override fun render(textmap: Textmap) {
        textmap.line(">> Traveling Wizard! <<")
        textmap.emptyLine()
        textmap.line("A mysterious wizard has visited your realm.")
        textmap.line("He shares ancient knowledge with you!")
        textmap.line("(Future: grants free upgrade)")
    }

    override fun execute(user: User) {
        // Future: grant a free upgrade
    }
}
