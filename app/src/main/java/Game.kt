import android.content.Context
import android.icu.number.Scale
import android.media.MediaPlayer
import com.example.game2d.Boy
import com.example.game2d.R
import com.example.game2d.Virus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game(val scope: CoroutineScope, screenW:Int, screenH: Int,scale: Float,context:Context) {
    var counter = 0
    val state = MutableStateFlow(0)
    val background = Background(screenW)
    var isPlaying = true
    val boy = Boy(screenH,scale)
    val virus = Virus(screenW, screenH, scale)
    var mper1 = MediaPlayer.create(context, R.zzz.lastletter)
    var mper2 = MediaPlayer.create(context, R.zzz.gameover)

    fun Play(){
        scope.launch {
            isPlaying = true

            while (isPlaying) {
                mper1.start() //播放背景音樂
                delay(40)
                background.Roll()

                if (counter % 3 == 0){
                    boy.Walk()
                    virus.Fly()

                    if(boy.getRect().intersect(virus.getRect())) {
                        isPlaying = false
                        mper1.pause()
                        mper2.start()
                    }
                }
                counter++
                state.emit(counter)
            }
        }
    }

    fun Restart(){
        virus.Reset()
        counter = 0
        isPlaying = true
        Play()
    }

}