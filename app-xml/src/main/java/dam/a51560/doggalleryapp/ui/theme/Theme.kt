package dam.a51560.doggalleryapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Esquema de cores para tema escuro.
 * Utiliza as cores definidas para o tema escuro.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Esquema de cores para tema claro.
 * Utiliza as cores definidas para o tema claro.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Tema principal da aplicação, baseado no Material Design 3.
 * Suporta cores dinâmicas em dispositivos Android 12+ (API 31+).
 *
 * @param darkTheme Indica se o tema escuro deve ser utilizado (padrão: baseado no sistema)
 * @param dynamicColor Indica se devem ser usadas cores dinâmicas (padrão: true)
 * @param content Conteúdo composable a ser envolvido pelo tema
 */
@Composable
fun DogGalleryAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        // Utiliza cores dinâmicas (extraídas do wallpaper) em Android 12+
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Caso contrário, utiliza as cores estáticas definidas
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}