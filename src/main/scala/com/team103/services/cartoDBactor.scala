package com.team103.services

import _root_.akka.actor.{Actor, ActorRef, ActorSystem}
import _root_.akka.pattern.ask
import com.team103.config.ScheduledConfig
import com.team103.database.postGisDB.ResponseDAO
import com.team103.model.Response
import grizzled.slf4j.Logger
import java.util.Calendar
import org.scalatra._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * @author Team 103
  * @version 0.0.1
  *
  *  Service to update CartoDB values
  */
class cartoDBactor extends Actor {

  def logger = Logger[cartoDBactor]

  def repo = ResponseDAO

  /** Abstract method inherited from Actor. When message comes the actor invokes
    * the db check method
    */
  def receive = {
    case ScheduledConfig.CHECK_DATA_MESSAGE => {
      logger.info("[PB]: Deleting data from CartoDB")
      checkData
    }
  }

  /** Checks data for remove old values */
  protected def checkData = {
    val responses = repo.findAll
    responses.map(r => checkDate(r))
    logger.info("[PB]: Tuple's deleted from cartoDB")
  }

  /** Check a data an delete if it has been inserted 24 hours ago 
    * @param r representing the response to check
    */
  private def checkDate(r:Response) = {
    val now = Calendar.getInstance.getTimeInMillis
    if ((now - r.timeStamp.getTime) >= ScheduledConfig.TIME_MILLIS_EXPIRE){
      repo.delete(r.id)
    }
  }
}
