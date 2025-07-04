package me.imf4ll.displayEmEvidencia.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun toTime(time: String): Pair<Long, Boolean> {
  var remaining: Long = 0;

  if (!time.contains("h") && !time.contains("m") && !time.contains("s")) {
    return Pair(0, false);

  } else {
    time
      .replace("h", "h:")
      .replace("m", "m:")
      .replace("s", "")
      .split(":")
      .iterator()
      .forEach { i ->
        if (i.contains("h")) remaining += i.split("h")[0].toInt() * 60 * 60;
        if (i.contains("m")) remaining += i.split("m")[0].toInt() * 60;
        if (i.toIntOrNull() != null) remaining += i.toInt();
      }
  }

  return Pair(System.currentTimeMillis() + (remaining * 1000), true);
}

fun toLocalDateTime(millis: Long): LocalDateTime {
  return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
}