package kcacup
package models

case class Entry(replay: Replay, event: Event) extends Chronological {

  def createdAtDate = replay.createdAtDate
}
