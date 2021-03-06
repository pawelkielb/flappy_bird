package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Image
import pl.gieted.flappy_bird.engine.Object
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Vector2

class ScoreDisplay(renderer: Renderer, private val digitTextures: List<Image>) :
    Object(renderer, Vector2(0, 325), zIndex = 500) {

    companion object {
        const val digitOffset = 0
        const val digitScale = 1.25
    }

    var score = 0

    override fun setup() {
    }

    private val digitHeight = digitTextures.first().height * digitScale
    private val digitWidth = digitTextures.first().width * digitScale

    override fun draw() {
        super.draw()
        with(renderer) {
            position = Vector2(camera.position.x, position.y)

            val digitsCount = score.toString().length
            var nextDigitXPos = position.x - (digitsCount * digitWidth + (digitsCount - 1) * digitOffset) / 2
            score.toString().map {
                image(
                    digitTextures[it.digitToInt()],
                    nextDigitXPos.toFloat(),
                    (-position.y - digitHeight / 2).toFloat(),
                    digitWidth.toFloat(),
                    digitHeight.toFloat()
                )
                nextDigitXPos += digitWidth + digitOffset
            }
        }
    }
}
