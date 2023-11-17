package gomoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.shared.background.BackgroundConfig
import gomoku.shared.components.shimmerEffect

@Composable
fun GameSkeletonLoader() {
    val modSgm = Modifier.shimmerEffect()
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            SkeletonLoaderItem(label = "             ")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SkeletonLoaderItem(
                label = ("                               ")
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modSgm.size(380.dp, 380.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SkeletonLoaderItem(
                label = ("                               ")
            )
            SkeletonLoaderItem(label = "            ")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SkeletonLoaderItem("                                  ")
        }
    }
}

@Composable
fun SkeletonLoaderItem(label: String) {
    val chipHeight = BackgroundConfig(
        LocalConfiguration.current
    ).screenHeight * 0.05f
    Row(
        Modifier
            .height(chipHeight)
            .shimmerEffect()
    ) {
        Text(label)
    }
}

/*
    SkeletonLoader(contentView:Composable,defaultPrototypeView:Composable,loadinG:Boolean )
 */

/*
@Composable
fun SkeletonLoader(content: @Composable () -> Unit) {
    val modSgm = Modifier.shimmerEffect()
    val shimmerContent = remember {
        Skeletonify(content)
    }
    Box(modifier = modSgm.fillMaxSize()) {
        ShimmerEffect(child = shimmerContent)
    }
}

private fun Skeletonify(content: @Composable () -> Unit): @Composable () -> Unit {
    val shimmerViews = mutableListOf<Composable>()
    content()
    return {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
        ) {
            shimmerViews.forEach { it() }
        }
    }
}



private fun Composable.asSkeletonItem(): SkeletonLoaderItem {
    val chipHeight = BackgroundConfig(
        LocalConfiguration.current
    ).screenHeight * 0.05f
    return SkeletonLoaderItem(
        label = Modifier.height(chipHeight).fillMaxWidth().shimmerEffect().toString()
    )
}

private fun Row.asSkeletonItem(): List<SkeletonLoaderItem> {
    return this.map { it.asSkeletonItem() }
}

private fun GameInfoChip.asSkeletonItem(): SkeletonLoaderItem {
    return SkeletonLoaderItem(
        label = listOf(
            Modifier.size(40.dp).shimmerEffect().toString(),
            Modifier.padding(start = 10.dp).weight(1f).shimmerEffect().toString()
        ).joinToString(separator = " ")
    )
}

private fun PlayerInfoChip.asSkeletonItem(): SkeletonLoaderItem {
    return SkeletonLoaderItem(
        label = listOf(
            if (select) {
                Modifier.size(40.dp).shimmerEffect().toString()
            } else {
                Modifier.fillMaxWidth().height(30.dp).shimmerEffect().toString()
            },
            Modifier.weight(1f).shimmerEffect().toString()
        ).joinToString(separator = " ")
    )
}

private fun BoardContainer.asSkeletonItem(): SkeletonLoaderItem {
    return SkeletonLoaderItem(
        label = this.children.map { it.asSkeletonItem() }.joinToString(separator = "\n")
    )
}*/
@Preview
@Composable
fun GameSkeletonLoaderPreview() {
    GameSkeletonLoader()
}

