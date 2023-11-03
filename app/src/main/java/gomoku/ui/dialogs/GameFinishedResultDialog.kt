package gomoku.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun GameFinishedResultDialog(background: BackgroundConfig) {
    GomokuTheme {
        Surface(
            color = Color.Transparent,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White) // Pode ajustar para a cor de fundo desejada
                    .width(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp)) // Reduzindo o raio do canto para um tamanho mais modesto
                        .background(Color.White)
                        .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                        .width(250.dp) // Definindo largura específica
                        .height(150.dp), // Definindo altura específica
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) // Padding interno para o conteúdo dentro do popup
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp), // Ajuste a altura para adequar o tamanho do texto e da imagem
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(id = R.drawable.man),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp) // Tamanho da imagem
                            )
                            TextWithFont(text = "Results")
                            Image(
                                painterResource(id = R.drawable.man5),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp) // Tamanho da imagem
                            )
                        }
                        // Sua segunda Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp), // Ajuste conforme necessário
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextWithFont(text = "Tiago  Frazão", 2)
                            Image(
                                painterResource(id = R.drawable.checklist),
                                contentDescription = null
                            )
                            TextWithFont(text = "PLAYER 2", 2, 6)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth() // Faz com que esta Box preencha a largura da Column
                        .background(Color.White) // Cor de fundo do segmento de contraste
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) // Adicionando algum espaço interno
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp), // Ajuste conforme necessário
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(id = R.drawable.check_mark_2),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            TextWithFont(text = "Winner")
                            Image(
                                painterResource(id = R.drawable.close),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextWithFont(
                                    text = "250",
                                    modifier = Modifier.padding(end = 0.dp)
                                ) // ajustando o padding se necessário
                                Image(
                                    painterResource(id = R.drawable.coins),
                                    contentDescription = null
                                )
                            }
                            TextWithFont(text = "Points")
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextWithFont(text = "100", 2, 6)
                                Image(
                                    painterResource(id = R.drawable.coins),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun GameFinishedResultDialogPreview() {
    GameFinishedResultDialog(BackgroundConfig(LocalConfiguration.current))
}
