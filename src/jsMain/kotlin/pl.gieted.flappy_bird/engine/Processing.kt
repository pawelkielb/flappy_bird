package pl.gieted.flappy_bird.engine

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import pl.gieted.flappy_bird.p5
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("PropertyName")
actual open class Processing {
    private val instance = object : p5({}, document.querySelector("main")!! as HTMLElement) {
        override fun setup() {
            this@Processing.settings()
            createCanvas(sizeX!!, sizeY!!, renderer!!)
            this@Processing.setup()
        }

        override fun draw() {
            this@Processing.draw()
        }
    }

    actual class Surface {
        actual fun setTitle(title: String) {
        }

        actual fun setResizable(resizable: Boolean) {
        }
    }

    actual suspend fun loadImage(path: String): Image = suspendCoroutine<p5.Image> { continuation ->
        instance.loadImage(path, {
            continuation.resume(it)
        })
    }.let { Image(it) }

    actual val height: Int
        get() = instance.height.toInt()

    actual val width: Int
        get() = instance.width.toInt()

    actual fun background(image: Image) {
        instance.background(image.p5Image)
    }

    actual fun background(code: Int) {
        instance.background(code)
    }

    actual fun translate(x: Float, y: Float) {
        instance.translate(x, y)
    }

    actual fun rotate(value: Float) {
        instance.rotate(value)
    }

    actual fun millis(): Int = instance.millis().toInt()

    actual fun scale(value: Float) {
        instance.scale(value)
    }

    actual val mousePressed: Boolean
        get() = instance.mouseIsPressed

    actual val displayHeight: Int
        get() = instance.displayHeight.toInt()

    actual val displayWidth: Int
        get() = instance.displayWidth.toInt()

    private var sizeX: Int? = null
    private var sizeY: Int? = null
    private var renderer: String? = null

    actual fun size(x: Int, y: Int, renderer: String) {
        sizeX = x
        sizeY = y
        this.renderer = renderer
    }

    actual open fun setup() {
    }

    actual open fun draw() {
    }

    actual open fun settings() {
    }

    protected actual val surface: Surface
        get() = Surface()

    actual fun radians(degrees: Float): Float = instance.radians(degrees).toFloat()

    actual fun setIcon(vararg paths: String) {
        // TODO
    }

    actual val P2D: String = "p2d"

    actual fun pushMatrix() = instance.push()

    actual fun popMatrix() = instance.pop()

    actual fun noTint() = instance.noTint()

    actual fun noFill() {
        instance.noFill()
    }

    actual fun fill(v1: Float, v2: Float, v3: Float, alpha: Float) {
        instance.fill(v1, v2, v3, alpha)
    }

    actual fun strokeWeight(stroke: Float) {
        instance.strokeWeight(stroke)
    }

    actual fun stroke(a: Float, b: Float, c: Float) {
        instance.stroke(a, b, c)
    }

    actual fun stroke(a: Int, b: Float) {
        instance.stroke(a, b)
    }

    actual fun rect(a: Float, b: Float, c: Float, d: Float) {
        instance.rect(a, b, c, d)
    }

    actual fun tint(color: Int, alpha: Float) = instance.tint(color, alpha)

    actual fun image(image: Image, x: Float, y: Float) = instance.image(image.p5Image, x, y)
    
    actual val frameRate: Float
        get() = instance.frameRate().toFloat()
}
