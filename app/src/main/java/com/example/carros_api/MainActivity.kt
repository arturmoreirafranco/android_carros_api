package com.example.carros_api // <-- troque pelo package do SEU projeto (1a linha do seu MainActivity)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Cores ----------
val Azul = Color(0xFF38B6FF)
val CinzaCampo = Color(0xFFD9D9D9)
val Vermelho = Color(0xFFFF3131)
val TextoEscuro = Color(0xFF222222)

// ---------- Dados ----------
data class Carro(
    val id: Int, val apelido: String, val ano: Int, val marca: String, val modelo: String,
    val cor: Color, val motor: String, val potencia: String, val consumo: String, val aceleracao: String
)

val listaCarros = listOf(
    Carro(1, "Carro1", 2026, "Mercedez", "Mercedes AMG A45", Color(0xFFB0B7C0), "2.0 Flex", "177 cv", "14,5 km/L", "9,2 s"),
    Carro(2, "Carro2", 2026, "Audi", "Audi A5", Color(0xFF1E3FBF), "2.0 Turbo", "252 cv", "11,0 km/L", "5,8 s"),
    Carro(3, "Carro3", 2026, "Honda", "Honda NX", Color(0xFF7A8FA8), "2.5 Hibrido", "200 cv", "16,0 km/L", "7,5 s"),
    Carro(4, "Carro4", 2026, "Bugatti", "Bugatti Chiron", Color(0xFF222222), "8.0 W16", "1500 cv", "3,0 km/L", "2,4 s"),
    Carro(5, "Carro5", 2026, "jaguar", "jaguar", Color(0xFF222222), "8.0 W16", "1500 cv", "3,0 km/L", "2,4 s"),
    Carro(6, "Carro6", 2026, "ferrari", "Bugatti Chiron", Color(0xFF222222), "8.0 W16", "1500 cv", "3,0 km/L", "2,4 s"),
    Carro(7, "Carro5", 2025, "Ferrari", "Ferrari SF90 Stradale", Color(0xFFFF0000), "4.0 V8 Biturbo", "1000 cv", "6,5 km/L", "2,5 s"),

    Carro(8, "Carro6", 2024, "Lamborghini", "Lamborghini Revuelto", Color(0xFFFFFF00), "6.5 V12 Híbrido", "1015 cv", "5,8 km/L", "2,5 s"),

    Carro(9, "Carro7", 2025, "Porsche", "Porsche 911 GT3 RS", Color(0xFF00AA00), "4.0 Boxer 6", "525 cv", "7,2 km/L", "3,2 s"),

    Carro(10, "Carro8", 2024, "McLaren", "McLaren 750S", Color(0xFFFF8800), "4.0 V8 Biturbo", "750 cv", "6,8 km/L", "2,8 s"),

    Carro(11, "Carro9", 2025, "Koenigsegg", "Koenigsegg Jesko", Color(0xFF0000FF), "5.0 V8 Biturbo", "1600 cv", "4,5 km/L", "2,5 s"),

    Carro(12, "Carro10", 2024, "Mercedes-Benz", "Mercedes-AMG GT 63", Color(0xFF888888), "4.0 V8 Biturbo", "585 cv", "7,0 km/L", "3,2 s"),

    Carro(13, "Carro11", 2025, "BMW", "BMW M4 Competition", Color(0xFF00008B), "3.0 6 cilindros Biturbo", "510 cv", "8,5 km/L", "3,9 s"),

    Carro(14, "Carro12", 2024, "Audi", "Audi R8 V10 Performance", Color(0xFF800080), "5.2 V10", "620 cv", "6,0 km/L", "3,1 s"),

    Carro(15, "Carro13", 2025, "Nissan", "Nissan GT-R Nismo", Color(0xFF444444), "3.8 V6 Biturbo", "600 cv", "6,5 km/L", "2,8 s"),

    Carro(16, "Carro14", 2024, "Chevrolet", "Chevrolet Corvette Z06", Color(0xFFAA0000), "5.5 V8", "670 cv", "5,5 km/L", "2,6 s")


)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = Azul)) {
                Surface(Modifier.fillMaxSize(), color = Color.White) { CarrosApp() }
            }
        }
    }
}

// ---------- Navegacao simples (sem biblioteca extra) ----------
@Composable
fun CarrosApp() {
    var tela by remember { mutableStateOf("login") }          // login, cadastro, home, detalhe
    var carroAberto by remember { mutableStateOf(1) }
    val favoritos = remember { mutableStateListOf<Int>() }
    val alternar: (Int) -> Unit = { if (favoritos.contains(it)) favoritos.remove(it) else favoritos.add(it) }

    when (tela) {
        "login" -> LoginScreen({ tela = "home" }, { tela = "cadastro" })
        "cadastro" -> CadastroScreen({ tela = "home" }, { tela = "login" })
        "home" -> HomeScreen(favoritos, alternar, { carroAberto = it; tela = "detalhe" }, { tela = "login" })
        "detalhe" -> {
            val carro = listaCarros.first { it.id == carroAberto }
            DetalheScreen(carro, favoritos.contains(carro.id), { alternar(carro.id) }, { tela = "home" })
        }
    }
}

// ---------- Componentes ----------
@Composable
fun CampoCinza(
    valor: String, onValor: (String) -> Unit, dica: String, icone: ImageVector,
    senha: Boolean = false, teclado: KeyboardType = KeyboardType.Text
) {
    var visivel by remember { mutableStateOf(false) }
    TextField(
        value = valor, onValueChange = onValor,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        singleLine = true, shape = RoundedCornerShape(0.dp),
        placeholder = { Text(dica, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black) },
        leadingIcon = { Icon(icone, null, tint = Color.Black) },
        trailingIcon = if (senha) {
            { Text(if (visivel) "🙈" else "👁", fontSize = 20.sp, modifier = Modifier.clickable { visivel = !visivel }.padding(8.dp)) }
        } else null,
        visualTransformation = if (senha && !visivel) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (senha) KeyboardType.Password else teclado),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CinzaCampo, unfocusedContainerColor = CinzaCampo,
            focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun BotaoAzul(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Azul, contentColor = Color.White)
    ) { Text(texto, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
}

// ---------- Tela 1: Login ----------
@Composable
fun LoginScreen(onEntrar: () -> Unit, onCadastrar: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))
        Text("🚗", fontSize = 90.sp)
        Text("CarrosApi", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = TextoEscuro)
        Spacer(Modifier.height(70.dp))
        CampoCinza(email, { email = it }, "E-mail", Icons.Filled.Email, teclado = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        CampoCinza(senha, { senha = it }, "Senha", Icons.Filled.Lock, senha = true)
        Spacer(Modifier.height(24.dp))
        BotaoAzul("Entrar", onEntrar)
        Spacer(Modifier.height(24.dp))
        Text("Ainda não tem conta?", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text("Cadastrar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Azul,
            modifier = Modifier.clickable { onCadastrar() })
    }
}

// ---------- Tela 2: Criar conta ----------
@Composable
fun CadastroScreen(onCadastrar: () -> Unit, onEntrar: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))
        Text("Criar conta", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = TextoEscuro, textAlign = TextAlign.Center)
        Spacer(Modifier.height(60.dp))
        CampoCinza(nome, { nome = it }, "Nome completo", Icons.Filled.Person)
        Spacer(Modifier.height(12.dp))
        CampoCinza(email, { email = it }, "E-mail", Icons.Filled.Email, teclado = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        CampoCinza(senha, { senha = it }, "Senha", Icons.Filled.Lock, senha = true)
        Spacer(Modifier.height(12.dp))
        CampoCinza(confirmar, { confirmar = it }, "Confirmar Senha", Icons.Filled.Lock, senha = true)
        Spacer(Modifier.height(80.dp))
        BotaoAzul("Cadastrar", onCadastrar, Modifier.padding(horizontal = 8.dp))
        Spacer(Modifier.height(32.dp))
        Row {
            Text("Já tem uma conta?", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Entrar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Azul,
                modifier = Modifier.clickable { onEntrar() })
        }
    }
}

// ---------- Telas 3, 5 e Conta (com barra inferior) ----------
@Composable
fun HomeScreen(favoritos: List<Int>, onToggle: (Int) -> Unit, onAbrir: (Int) -> Unit, onSair: () -> Unit) {
    var aba by remember { mutableStateOf(0) } // 0 Carros, 1 Favoritos, 2 Conta
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val itens = listOf("Carros", "Favoritos", "Conta")
                itens.forEachIndexed { i, nome ->
                    NavigationBarItem(
                        selected = aba == i, onClick = { aba = i },
                        icon = {
                            when (i) {
                                0 -> Text("🚗", fontSize = 26.sp)
                                1 -> Icon(if (aba == 1) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, null, Modifier.size(30.dp))
                                else -> Icon(Icons.Filled.Person, null, Modifier.size(30.dp))
                            }
                        },
                        label = { Text(nome, fontSize = 13.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Azul, selectedTextColor = Azul,
                            unselectedIconColor = Color.Black, unselectedTextColor = Color.Black,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (aba) {
                0 -> ListaCarros(favoritos, onToggle, onAbrir)
                1 -> FavoritosTela(favoritos, onToggle, onAbrir)
                else -> Column(Modifier.fillMaxSize().statusBarsPadding().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(48.dp))
                    Text("Minha Conta", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextoEscuro)
                    Spacer(Modifier.weight(1f))
                    BotaoAzul("Sair", onSair)
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun ListaCarros(favoritos: List<Int>, onToggle: (Int) -> Unit, onAbrir: (Int) -> Unit) {
    var busca by remember { mutableStateOf("") }
    val filtrados = listaCarros.filter {
        it.marca.contains(busca, true) || it.modelo.contains(busca, true) || it.apelido.contains(busca, true)
    }
    Column(Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(48.dp))
        Text("CarrosApi", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = TextoEscuro,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(12.dp))
        TextField(
            value = busca, onValueChange = { busca = it },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            singleLine = true, shape = RoundedCornerShape(0.dp),
            placeholder = { Text("Pesquisar", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Black) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CinzaCampo, unfocusedContainerColor = CinzaCampo,
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(filtrados, key = { it.id }) { carro ->
                ItemCarro(carro, favoritos.contains(carro.id), onToggle, onAbrir)
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFBDBDBD)))
            }
        }
    }
}

@Composable
fun FavoritosTela(favoritos: List<Int>, onToggle: (Int) -> Unit, onAbrir: (Int) -> Unit) {
    val salvos = listaCarros.filter { favoritos.contains(it.id) }
    Column(Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(48.dp))
        Text("Meus Favoritos", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextoEscuro)
        Text("Seus carros salvos aparecem aqui", fontSize = 14.sp)
        Spacer(Modifier.height(40.dp))
        LazyColumn {
            items(salvos, key = { it.id }) { carro ->
                ItemCarro(carro, true, onToggle, onAbrir)
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFBDBDBD)))
            }
        }
    }
}

@Composable
fun ItemCarro(carro: Carro, favorito: Boolean, onToggle: (Int) -> Unit, onAbrir: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().clickable { onAbrir(carro.id) }.padding(vertical = 12.dp)) {
        // Troque este Box por Image(painterResource(R.drawable.suaFoto), ...) quando tiver as fotos
        Box(Modifier.size(width = 130.dp, height = 86.dp).background(carro.cor), contentAlignment = Alignment.Center) {
            Text("🚗", fontSize = 36.sp)
        }
        Column(Modifier.weight(1f).padding(start = 8.dp)) {
            Text(carro.apelido, fontSize = 12.sp)
            Text(carro.ano.toString(), fontSize = 12.sp, modifier = Modifier.padding(start = 4.dp))
            Spacer(Modifier.height(4.dp))
            Text(carro.marca, fontSize = 16.sp)
        }

        IconButton(onClick = { onToggle(carro.id) }) {
            Icon(
                if (favorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, "Favoritar",
                tint = if (favorito) Vermelho else Color.Black, modifier = Modifier.size(34.dp)
            )
        }
    }
}

// ---------- Tela 4: Detalhe ----------
@Composable
fun DetalheScreen(carro: Carro, favorito: Boolean, onToggleFavorito: () -> Unit, onVoltar: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxWidth().height(260.dp).background(carro.cor), contentAlignment = Alignment.Center) {
            Text("🚗", fontSize = 90.sp)
            Text("←", fontSize = 28.sp, color = Color.White,
                modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().clickable { onVoltar() }.padding(16.dp))
            Icon(
                if (favorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, null,
                tint = if (favorito) Vermelho else Color.White,
                modifier = Modifier.align(Alignment.TopEnd).statusBarsPadding().padding(16.dp).size(24.dp)
            )
        }
        Column(Modifier.padding(horizontal = 8.dp)) {
            Text(carro.modelo, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(carro.ano.toString(), fontSize = 20.sp, modifier = Modifier.padding(start = 2.dp))
            Spacer(Modifier.height(28.dp))
            Spec("⚙️", "Motor", carro.motor)
            Spacer(Modifier.height(20.dp))
            Spec("⚡", "Potencia", carro.potencia)
            Spacer(Modifier.height(20.dp))
            Spec("⛽", "Consumo", carro.consumo)
            Spacer(Modifier.height(20.dp))
            Spec("⏱️", "0-100 km/h", carro.aceleracao)
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onToggleFavorito,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(46.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Azul, contentColor = Color.White)
        ) {
            Icon(if (favorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, null, Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Text(if (favorito) "Remover dos favoritos" else "Salvar nos favoritos", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.height(32.dp).navigationBarsPadding())
    }
}

@Composable
fun Spec(emoji: String, nome: String, valor: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(emoji, fontSize = 30.sp, modifier = Modifier.width(44.dp), textAlign = TextAlign.Center)
        Text(nome, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp).weight(1f))
        Text(valor, fontSize = 15.sp, modifier = Modifier.padding(end = 12.dp))
    }
}
