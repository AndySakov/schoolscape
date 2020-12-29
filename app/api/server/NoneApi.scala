package api.server

class NoneApi extends Api {
  override def _type: String = "none"
}

object NoneApi {
  def apply(): NoneApi = new NoneApi()
}
