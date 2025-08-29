package me.imf4ll.displayEmEvidencia.chat.models;

data class Player(
  var userID: String,
  var pmLocked: Boolean = false,
  var lockReason: String = "",
);