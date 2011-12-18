package kcacup

import repo._
import models._

class Timeline(eventRepo: EventRepo) {

  def ofUser(user: User): List[Entry] = for {
    event <- eventRepo.findAll
    replay <- event.replays
    if (replay.username == user.username)
  } yield Entry(replay, event)
}
