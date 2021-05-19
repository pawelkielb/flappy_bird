package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*

class ScrollingElement(
    renderer: Renderer,
    private val elementFactory: () -> Measurable,
    private val parallax: Double = 0.0,
    private val startXPos: Double = renderer.camera.bounds.left - Vector2.defaultSize.x * 5,
    zIndex: Int = 0,
    private val offset: Double = 0.0
) : Object(renderer, zIndex = zIndex) {

    private val elements = mutableListOf<Measurable>()
    private var lastCameraXPos = renderer.camera.position.x

    private fun spawnNext() {
        val element = elementFactory()
        val xPos = if (elements.isEmpty()) startXPos else elements.last().position.x + element.size.x + offset
        element.position = Vector2(xPos, element.position.y)
        addChild(element as Object)
        elements.add(element)
    }

    private fun removeElement(element: Measurable) {
        elements.remove(element)
        removeChild(element as Object)
    }

    override fun setup() {
        spawnNext()
    }

    override fun draw() {
        with(renderer) {
            while (elements.last().bounds.right + offset < camera.bounds.right + Vector2.defaultSize.x) {
                spawnNext()
            }
            elements.filter { it.bounds.right < camera.bounds.left }.forEach { removeElement(it) }
            elements.forEach { it.position -= Vector2((parallax + 1) * (camera.position.x - lastCameraXPos), 0) }
            lastCameraXPos = camera.position.x
        }
        super.draw()
    }
}
