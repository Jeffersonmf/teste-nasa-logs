
import config.Environment
import org.scalatest._

class Tests extends FlatSpec with Matchers {

  /**
   * Some tests in Scala using native BDD and infix concepts.
   *
   * @author Jefferson Marchetti Ferreira
   *
   */
  it should "return non-null String in the input folder of key SourceLocalFolder" in {

    Environment.getSourceLocalFolder() shouldNot be(null)
  }
  it should "return non-null String in the SourceRemoteFiles of key value" in {

    Environment.getSourceRemoteFiles() shouldNot be(null)
  }
  it should "return a Boolean of the Local Mode Key" in {

    Environment.isRunningLocalMode() should be(true || false)
  }
}