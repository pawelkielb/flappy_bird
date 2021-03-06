package pl.gieted.flappy_bird.engine

open class Rectangle(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    zIndex: Int = 0,
    override var size: Vector2 = Vector2.defaultSize,
    var rotation: Double = 0.0,
    var color: Color = Color.default,
    opacity: Double = 1.0,
    var strokeColor: Color? = null,
    var strokeWidth: Double = 1.0
) : Object(renderer, position, zIndex), Measurable {
    
    var opacity: Double = limit(opacity, 0.0, 1.0)
        set(value) {
            field = limit(value, 0.0, 1.0)
        }
    
    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            fill(color.r.toFloat(), color.g.toFloat(), color.b.toFloat(), opacity.toFloat() * 255)
            val outlineColor = strokeColor
            if (outlineColor != null) {
                strokeWeight(strokeWidth.toFloat())
                stroke(outlineColor.r.toFloat(), outlineColor.g.toFloat(), outlineColor.b.toFloat())
            } else {
                stroke(0, 0f)
            }
            rotateAround(position, rotation)
            rect((position.x - size.x / 2).toFloat(), (-position.y - size.y / 2).toFloat(), size.x.toFloat(), size.y.toFloat())
        }
    }
}
