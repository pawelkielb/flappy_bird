package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.game.LoadingScene
import processing.core.PApplet

class Renderer : PApplet() {

    companion object {
        const val windowWidth = 1440
        const val windowHeight = 810
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

    fun rotateAround(center: Vector2, degrees: Double) {
        translate(center.x.toFloat(), center.y.toFloat())
        rotate(radians(degrees.toFloat()))
        translate(-center.x.toFloat(), -center.y.toFloat())
    }

    override fun setup() {
        lastDrawTime = millis()
        scene.setup()
    }

    override fun draw() {
        background(0)
        deltaTime = millis() - lastDrawTime
        lastDrawTime = millis()
        mousePressedThisFrame = mousePressed && !mousePressedLastFrame
        mousePressedLastFrame = mousePressed
        scene.draw()
    }

    override fun settings() {
        size(windowWidth, windowHeight, P2D)
    }
}
