package pl.gieted.flappy_bird.game

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.game.objects.*
import kotlin.math.abs
import kotlin.math.pow

class GameScene(renderer: Renderer, private val resources: Resources) : Scene(renderer) {

    companion object {
        const val firstPipeOffset = 1500
        const val pipeOffset = 300.0
        const val groundLevel = -290
        const val maxPipeHeight = 225.0
        const val minPipeHeight = -125.0
    }

    private val startScreen = StartScreen(renderer, resources.images.message)
    private var score = 0
    private val bird = Bird(renderer, getBirdTexture(), this::onDeath, resources.sounds.wing)

    private fun getBackgroundTexture() = if ((1..4).random() < 4) {
        resources.images.backgroundDay
    } else resources.images.backgroundNight

    private fun getBirdTexture() = when ((1..3).random()) {
        1 -> resources.images.birds[Bird.Color.Red]!!
        2 -> resources.images.birds[Bird.Color.Blue]!!
        else -> resources.images.birds[Bird.Color.Yellow]!!
    }

    private fun onDeath() {
        addObject(DeathFlash(renderer))
        resources.sounds.hit.play()
    }

    private var pipesCount = 0

    private fun getPipeTexture() = if (++pipesCount < 100) resources.images.greenPipe else resources.images.redPipe

    private var lastPipeHeight = 0.0

    private fun getNextPipeHeight(): Double {
        val nextHeight = limit(
            lastPipeHeight + (-50..50).random().toDouble().also { Math.cbrt(it) } * 5,
            minPipeHeight,
            maxPipeHeight
        )

        lastPipeHeight = nextHeight

        return nextHeight
    }

    private fun startGame() {
        startScreen.fadeOut()

        addObject(
            ScrollingElement(
                renderer,
                { Pipe(renderer, Vector2(0, getNextPipeHeight()), texture = getPipeTexture()) },
                offset = pipeOffset,
                startXPos = bird.position.x + firstPipeOffset
            )
        )
    }

    override fun setup() {
        super.setup()
        addObject(DipFromBlack(renderer))
        val backgroundTexture = getBackgroundTexture()
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, texture = backgroundTexture) },
                parallax = -1.8,
                offset = -11.0
            )
        )
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, Vector2(0, -Vector2.halfScreen.y), texture = resources.images.base) },
                zIndex = 5,
                offset = -1.0
            )
        )
        addObject(startScreen)
        addObject(bird)
    }

    override fun draw() {
        with(renderer) {
            if (bird.position.y < groundLevel) {
                bird.position = Vector2(bird.position.x, groundLevel)
                if (bird.isAlive) {
                    bird.kill()
                }
            }

            if (mousePressedThisFrame && bird.isAlive && bird.bounds.top < camera.bounds.top) {
                once("startGame") {
                    startGame()
                }
                bird.swing()
            }

            camera.position = Vector2(bird.position.x - Bird.xOffset, 0)
        }
        super.draw()
    }
}
