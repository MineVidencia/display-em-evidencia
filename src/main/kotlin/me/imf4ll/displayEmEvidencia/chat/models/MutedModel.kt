package me.imf4ll.displayEmEvidencia.chat.models

data class Muted(
  var userID: String,
  var mutedBy: String,
  var reason: String,
  var time: Long,
);