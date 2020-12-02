package api.server.sync

import api.server.sync.SyncResult.SyncResult

object Synchronizer {
  def sync(): SyncResult ={
    SyncResult.SYNCED
  }
}
