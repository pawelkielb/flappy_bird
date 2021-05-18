package pl.gieted.flappy_bird.engine

// does not support camera rotation or zooming
fun isOnScreen(
    renderer: Renderer,
    position: Vector2,
    size: Vector2
): Boolean {
    with(renderer) {
        val leftSide = (position - size / 2).x
        val topSide = (position - size / 2).y
        val rightSide = (position + size / 2).x
        val bottomSide = (position + size / 2).y

        val cameraLeftSide = (camera.position - Vector2.halfScreen).x
        val cameraTopSide = (camera.position + Vector2.halfScreen).y
        val cameraRightSide = (camera.position + Vector2.halfScreen).x
        val cameraBottomSide = (camera.position - Vector2.halfScreen).y

        val cameraVertical = cameraBottomSide..cameraTopSide
        val cameraHorizontal = cameraLeftSide..cameraRightSide

        return rightSide in cameraHorizontal && (topSide in cameraVertical || bottomSide in cameraVertical)
                || (leftSide in cameraHorizontal && (topSide in cameraVertical || bottomSide in cameraVertical))
    }
}
