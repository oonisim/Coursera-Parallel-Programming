
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {

    val x_min = clamp(x - radius, min =0, max = src.width -1)
    val x_max = clamp(x + radius, min =0, max = src.width -1)
    val y_min = clamp(y - radius, min =0, max = src.height -1)
    val y_max = clamp(y + radius, min =0, max = src.height -1)
    val pixels = math.abs(x_max - x_min + 1) * math.abs(y_max - y_min + 1)

    var col = x_min
    var row = y_min
    var r, g, b, a= 0
    
    while(row <= y_max){
      while(col <= x_max){
        var pixel = src(col, row)
        r += red(pixel)
        g += green(pixel)
        b += blue(pixel)
        a += alpha(pixel)
        col += 1
      }
      col = x_min
      row += 1
    }
    r = (r)   / pixels
    g = (g) / pixels
    b = (b)  / pixels
    a = (a) / pixels
    rgba(r, g, b, a)
  }

}
