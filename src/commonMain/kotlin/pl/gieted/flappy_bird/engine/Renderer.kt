package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.game.LoadingScene
import kotlin.math.roundToInt

class Renderer(private val setExtraSettings: Renderer.() -> Unit = {}): Processing() {

    companion object {
        const val windowWidth = 1440
        const val windowHeight = 810
        const val soundVolume = 0.1
    }

    private var lastDrawTime: Int = 0
    var deltaTime: Int = 0
        private set

    var mousePressedThisFrame = false
        private set

    private var mousePressedLastFrame = false

    var scene: Scene = LoadingScene(this)
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

    private var windowScale = 0.0

    override fun setup() {
        setExtraSettings()
        lastDrawTime = millis()
        scene.setup()
    }

    override fun draw() {
        background(0)
        scale(windowScale.toFloat())
        val currentTime = millis()
        deltaTime = currentTime - lastDrawTime
        lastDrawTime = currentTime
        mousePressedThisFrame = mousePressed && !mousePressedLastFrame
        mousePressedLastFrame = mousePressed
//        scene.draw()
        rect(0f, 0f, 20f, 20f)
    }

    override fun settings() {
        windowScale = if (displayWidth > displayHeight) {
            displayHeight / 1080.0
        } else {
            displayWidth / 1920.0
        }
        println("Window scale: $windowScale")
        size((windowWidth * windowScale).roundToInt(), (windowHeight * windowScale).roundToInt(), P2D)
    }
}
