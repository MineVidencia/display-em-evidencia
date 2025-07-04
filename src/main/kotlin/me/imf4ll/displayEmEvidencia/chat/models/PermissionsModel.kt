package me.imf4ll.displayEmEvidencia.chat.models

sealed class Permissions(val permission: String) {
  class Mute: Permissions("displayemevidencia.mute");
  class StaffChat: Permissions("displayemevidencia.staffchat");
  class Staff: Permissions("displayemevidencia.staff");
}