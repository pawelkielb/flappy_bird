package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import pl.gieted.flappy_bird.engine.limit
import pl.gieted.flappy_bird.game.Resources
import processing.sound.SoundFile

class Bird(
    renderer: Renderer,
    private val textures: Resources.Images.Bird,
    private val onDeath: () -> Unit,
    private val swingSound: SoundFile
) : Sprite(renderer, Vector2(0, autopilotHeight), texture = textures.downFlap) {

    companion object {
        const val wingFlapInterval = 100
        const val xOffset = -150
        const val autopilotHeight = -35
        const val autopilotRange = 5
        const val autopilotSpeed = 0.03
        const val gravityPower = 0.4
        const val swingPower = 8.5
        const val flySpeed = 2.5
        const val velocityCap = 13.0
    }

    enum class Color {
        Blue, Red, Yellow
    }

    private var flapCounter = 0

    private var yVelocity: Double = 0.0
        set(value) {
            field = limit(value, -velocityCap)
        }

    private var flapDirection = false
    private var mouseButtonLock = false
    private var autopilotDirection = 1

    var isAlive = true
        private set

    private var autopilot = true

    fun kill() {
        isAlive = false
        onDeath()
    }

    fun swing() {
        if (autopilot) {
            autopilot = false
        }
        mouseButtonLock = true
        swingSound.play()
        yVelocity = swingPower
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            if (isAlive) {
                flapCounter += deltaTime
                if (flapCounter > wingFlapInterval) {
                    flapCounter = 0
                    texture = when (texture) {
                        textures.midFlap -> if (flapDirection.also {
                                flapDirection = !flapDirection
                            }) textures.upFlap else textures.downFlap
                        else -> textures.midFlap
                    }
                }

                position += Vector2(flySpeed, 0)
            } else {
                if (texture != textures.midFlap) {
                    texture = textures.midFlap
                }
            }

            if (autopilot) {
                if (position.y > autopilotHeight + autopilotRange && autopilotDirection == 1) autopilotDirection = -1
                if (position.y < autopilotHeight - autopilotRange && autopilotDirection == -1) autopilotDirection = 1
                position += Vector2(0, autopilotSpeed * deltaTime * autopilotDirection)
            } else {
                yVelocity -= gravityPower
            }

            position += Vector2(0, yVelocity)
        }
    }
}
