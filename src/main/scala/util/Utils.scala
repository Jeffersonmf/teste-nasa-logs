package util

object Utils {

  def convertToLong(b: String): Option[Long] = {
    try {
      Some(b.toLong)
    } catch {
      case e: NumberFormatException => None
    }
  }

  def extractURLFromRequest(url: String): Option[String] = {
    val i = url.indexOf("/")
    if (i > -1) {
      val subs = url.slice(i, url.length)
      return Some(subs.split(" ").head)
    }
    return None
  }

}
