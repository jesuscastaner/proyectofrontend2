package net.unir.proyectofrontend2.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormattedLifeSpan(
    birthYear: Short?,
    deathYear: Short?,
    modifier: Modifier = Modifier
) {
    val yearSpan: String? = run {
        when {
            birthYear != null && deathYear != null -> {
                when {
                    birthYear < 0 && deathYear < 0 -> {
                        "${-birthYear}-${-deathYear} BC"
                    }

                    birthYear < 0 && deathYear >= 0 -> {
                        "${-birthYear} BC-${deathYear} AC"
                    }

                    birthYear >= 0 && deathYear >= 0 -> {
                        "$birthYear-$deathYear"
                    }

                    else -> {
                        "Malformed life span"
                    }
                }
            }

            birthYear != null -> {
                if (birthYear < 0) "b. ${-birthYear} BC"
                else "b. $birthYear"
            }

            deathYear != null -> {
                if (deathYear < 0) "d. ${-deathYear} BC"
                else "d. $deathYear"
            }

            else -> null
        }
    }

    yearSpan?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )
    }
}
