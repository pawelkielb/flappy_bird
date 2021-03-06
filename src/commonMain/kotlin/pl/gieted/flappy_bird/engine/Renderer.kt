package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.game.HighScoreRepository
import pl.gieted.flappy_bird.game.LoadingScene

class Renderer(
    private val startFullscreen: Boolean = false,
    val interactor: Interactor? = null,
    highScoreRepository: HighScoreRepository,
    private val setExtraSettings: Renderer.() -> Unit = {},
) : Processing() {

    companion object {
        const val defaultWidth = 1440
        const val defaultHeight = 810
        const val soundVolume = 0.1
    }

    private var lastDrawTime: Int = 0
    var deltaTime: Int = 0
        private set

    var mousePressedThisFrame = false
        private set

    private var mousePressedLastFrame = false

    var scene: Scene = LoadingScene(this, highScoreRepository)
        set(value) {
            scene.destroy()
            field = value
            value.setup()
        }

    val camera: Camera
        get() = scene.camera

    fun rotateAround(anchorPoint: Vector2, degrees: Double) {
        translate(anchorPoint.x.toFloat(), -anchorPoint.y.toFloat())
        rotate(radians(degrees.toFloat()))
        translate(-anchorPoint.x.toFloat(), anchorPoint.y.toFloat())
    }

    override val width: Int
        get() = (super.width / gameScale).toInt()

    override val height: Int
        get() = defaultHeight

    val aspectRatio: Double
        get() = width.toDouble() / height

    private val gameScale
        get() = super.height.toFloat() / defaultHeight

    override fun setup() {
        setExtraSettings()
        lastDrawTime = millis()
        scene.setup()
    }

    override fun draw() {
        background(0)
        translate(super.width.toFloat() / 2 - defaultWidth * gameScale / 2, 0f)
        scale(gameScale)
        val currentTime = millis()
        deltaTime = currentTime - lastDrawTime
        lastDrawTime = currentTime
        mousePressedThisFrame = mousePressed && !mousePressedLastFrame
        mousePressedLastFrame = mousePressed
        scene.draw()

        interactor?.reportFrameRate(frameRate.toInt())
    }

    override fun settings() {
        if (startFullscreen) {
            size(displayWidth, displayHeight, P2D)
        } else {
            val windowScale = if (displayWidth > displayHeight) {
                displayHeight / 1080.0
            } else {
                displayWidth / 1920.0
            }
            println("Window scale: $windowScale")
            size((defaultWidth * windowScale).toInt(), (defaultHeight * windowScale).toInt(), P2D)
        }
        noSmooth()
    }
}
