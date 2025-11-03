package tw.edu.pu.csim.mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.mole.ui.theme.MoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoleTheme {
                MoleScreen()
            }
        }
    }
}

@Composable
fun MoleScreen(moleViewModel: MoleViewModel = viewModel()) {
    val counter = moleViewModel.counter
    val stay = moleViewModel.stay
    val isGameActive = moleViewModel.isGameActive // 新增：判斷遊戲是否還在進行

    // DP-to-pixel轉換
    val density = LocalDensity.current

    // 地鼠Dp轉Px
    val moleSizeDp = 150.dp
    val moleSizePx = with(density) { moleSizeDp.roundToPx() }


    Box (
        modifier = Modifier.fillMaxSize()
            .onSizeChanged { intSize ->  // 用來獲取全螢幕尺寸px
                moleViewModel.getArea(intSize, moleSizePx) }
    ) {
        // 頂部居中的文字區域
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter) // 靠上置中
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "打地鼠遊戲(張佑先)",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "分數: $counter \n時間: $stay",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            if (!isGameActive) {
                Text(
                    text = "遊戲結束！時間到！",
                    fontSize = 32.sp,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(top = 200.dp)
                )
            }
        }


        Image(
            painter = painterResource(id = R.drawable.mole),
            contentDescription = "地鼠",
            modifier = Modifier
                .offset { IntOffset(moleViewModel.offsetX, moleViewModel.offsetY) }
                .size(moleSizeDp)
                .clickable(enabled = isGameActive) { // 只有遊戲進行中才能點擊
                    moleViewModel.incrementCounter()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoleTheme {
        MoleScreen()
    }
}