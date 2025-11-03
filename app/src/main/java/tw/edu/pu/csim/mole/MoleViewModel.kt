package tw.edu.pu.csim.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoleViewModel: ViewModel() {
    // 遊戲時間限制 (60秒)
    private val GAME_DURATION_SECONDS = 60L

    var counter by mutableLongStateOf(0)
        private set

    var stay by mutableLongStateOf(0)
        private set

    var maxX by mutableStateOf(0)
        private set

    var maxY by mutableStateOf(0)
        private set

    var offsetX by mutableStateOf(0)
        private set

    var offsetY by mutableStateOf(0)
        private set

    var isGameActive by mutableStateOf(true) // 新增：遊戲是否活躍的狀態

    fun incrementCounter() {
        if (isGameActive) { // 只有在遊戲進行中才加分
            counter++
        }
    }

    init {
        startCounting()
    }

    private fun startCounting() {
        viewModelScope.launch {
            while (isGameActive) { // 當 isGameActive 為 true 時才繼續迴圈
                delay(1000L)

                if (stay < GAME_DURATION_SECONDS) {
                    stay++ // 計數器加 1
                    moveMole()
                } else {
                    isGameActive = false // 達到時間限制，停止遊戲
                }
            }
        }
    }
    // 根據螢幕寬度,高度及地鼠圖片大小,計算螢幕範圍
    fun getArea(gameSize: IntSize, moleSize:Int) {
        maxX = gameSize.width - moleSize
        maxY = gameSize.height - moleSize
    }


    // 根據螢幕寬度,高度及地鼠圖片大小,隨機移動地鼠不超出螢幕範圍
    fun moveMole() {
        if (isGameActive) { // 只有在遊戲進行中才移動地鼠
            offsetX = (0..maxX).random()
            offsetY = (0..maxY).random()
        }
    }
}