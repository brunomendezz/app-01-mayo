import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mayorista.oscar.mayoristaoscar.R
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(navController:NavController) {
    val context = LocalContext.current
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo grande en la parte superior
            Image(
                painter = painterResource(
                    id = R.mipmap.ic_mayooscar_foreground,
                ),
                modifier = Modifier.width(192.dp).height(192.dp),
                contentDescription = "Mayorista Oscar Logo"
            )

            // Campo de correo electrónico
            TextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                placeholder = { Text(text = "Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )

            // Campo de contraseña
            TextField(
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                placeholder = { Text(text = "Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )

            // Botón de inicio de sesión
            Button(
                onClick = {
                    if (isEmailValid(email) && isPasswordValid(password)) {
                        // Iniciar sesión con Firebase
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Autenticación exitosa, redirigir o mostrar otro mensaje
                                    // Por ejemplo, navegar a otra pantalla:
                                    navController.popBackStack()
                                    navController.navigate(route = "home_screen")
                                    Toast.makeText(
                                        context,
                                        "Inicio de sesion exitoso",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    // El inicio de sesión falló, mostrar un mensaje de error
                                    Toast.makeText(
                                        context,
                                        "Inicio de sesión fallido, email o contraseña incorrecto",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "Correo electrónico o contraseña no válidos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Iniciar Sesión")
            }
        }
    }
fun isEmailValid(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    // Establece tus propias reglas de validación aquí
    val minLength = 8
    return password.length >= minLength
}

