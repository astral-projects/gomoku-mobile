package gomoku.ui.screens

import androidx.compose.foundation.Image
import pdm.gomoku.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
import gomoku.ui.lib.TextWithFont
import gomoku.ui.screens.backgrounds.BackGround
import gomoku.ui.screens.backgrounds.cliqueableRow
import gomoku.ui.theme.BlueBackGround
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.YellowBackGround
import gomoku.ui.theme.loginComposableInterior
import gomoku.ui.theme.orangeBubbleBorder

@Composable
fun Pop_UP() {
    GomokuTheme {
        Surface(
            color = BlueBackGround,
        ) {
            val background = BackGround(LocalConfiguration.current)
            //pop_up_window_searchingOpponnet(background = background)
            //pop_up_windowTimeExceed(background = background)
            //pop_up_windowResultGame(background)
            pop_up_windowToLeaveTheGame(background)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Pop_UpPreview() {
    Pop_UP()
}


@Composable
fun pop_up_window_searchingOpponnet(background: BackGround) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.07f)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.man),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)  // Takes up 1 part of available space
                    .size(400.dp)
            )
            TextWithFont(
                text = "Searching for an opponent",
                modifier = Modifier
                    .weight(2f)  // Takes up 2 parts of available space
                    .padding(horizontal = 8.dp)
            )
            Image(
                painterResource(id = R.drawable.ellipsis),
                modifier = Modifier
                    .weight(1f)  // Takes up 1 part of available space
                    .size(400.dp),
                contentDescription = null
            )
        }
    }
}


@Composable
fun pop_up_windowTimeExceed(background: BackGround) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.25f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithFont(text = "Turn Time Exceeded")
                Image(
                    painterResource(id = R.drawable.time_is_up),
                    modifier = Modifier.padding(start = 8.dp),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithFont(
                    text = "Your strategic moves were on point" +
                            ",but this time, the clock got better of you. "
                            + "Unfortunately, no points can be awarded as a result."
                )
            }
            Row {
                DismissButton(
                    backgroundColor = orangeBubbleBorder,
                    letterColor = Color.White,
                    onButtonText = "Acknowledge",
                    enable = true,
                    onDismiss = {}
                )
            }

        }
    }
}

@Composable
fun DismissButton(
    backgroundColor: Color,
    letterColor: Color,
    onButtonText: String,
    enable: Boolean,
    onDismiss: () -> Unit
) {
    Button(
        onClick = onDismiss,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = letterColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) { TextWithFont(onButtonText) }
}

@Composable
fun pop_up_windowResultGame(background: BackGround) {

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
fun pop_up_windowToLeaveTheGame(background: BackGround) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.2f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()

        ){
            Row {
                TextWithFont(
                    text = "Are you sure you want to quit this the game?" +
                            " You won't receive any points as a result", 3, 35
                )
            }
            Row(
            ) {
                DismissButton(backgroundColor = Color.Green, letterColor =Color.White , onButtonText = "Nevermind" , enable =true ) {
                }
                Spacer(modifier = Modifier.width(80.dp))
                DismissButton(backgroundColor = Color.Red, letterColor =Color.White , onButtonText = "Yes" , enable =true ) {
                }
            }

        }
    }
}