package me.imf4ll.displayEmEvidencia.chat.models;

import java.util.UUID

data class PrivateMessages(
  val player: UUID,
  var pmLocked: Boolean = false,
  var lockReason: String,
  var blocked: MutableList<UUID> = mutableListOf(),
);
