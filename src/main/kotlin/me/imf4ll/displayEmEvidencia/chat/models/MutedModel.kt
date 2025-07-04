package me.imf4ll.displayEmEvidencia.chat.models

import java.util.UUID

data class Muted(
  val player: UUID,
  var time: Long,
  var reason: String,
);