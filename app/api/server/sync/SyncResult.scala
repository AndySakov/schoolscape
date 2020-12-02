package api.server.sync

object SyncResult extends Enumeration {
  type SyncResult = Value
  val SYNCED, SERVER_ERROR, LOCAL_ERROR, MERGE_ERROR, UNEXPECTED_ERROR = Value
}