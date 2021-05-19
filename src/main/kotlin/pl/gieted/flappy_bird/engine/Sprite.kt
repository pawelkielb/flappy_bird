package pl.gieted.flappy_bird.engine

import processing.core.PImage

open class Sprite(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    zIndex: Int = 0,
    var rotation: Double = 0.0,
    var texture: PImage,
    opacity: Double = 1.0
) : Object(renderer, position, zIndex), Measurable {
    var opacity: Double = limit(opacity, 0.0, 1.0)
        set(value) {
            field = limit(value, 0.0, 1.0)
        }

    override val size: Vector2
        get() = Vector2(texture.width, texture.height)

    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            tint(255, (255 * opacity).toFloat())
            rotateAround(position, rotation)
            image(texture, (position.x - size.x / 2).toFloat(), (-position.y - size.y / 2).toFloat())
        }
    }
}
