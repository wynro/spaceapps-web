package com.team103.database.postGisDB

import com.team103.config.Repository
import com.team103.model.Response
import org.squeryl.PrimitiveTypeMode._

/**
  * @author Team 103
  * @version 0.0.1
  *
  *  Response Data Access object
  */
object ResponseDAO {

  def repo = Repository.responses
  /**
    * @param r response to insert
    */
  def insert(r: Response): Response = {
    transaction {
      repo.insert(r)
    }
  }

  /**
    * @param id repsonse identificator to use
    */
  def delete(id: Long) = {
    transaction {
      repo.deleteWhere(r => r.id === id)
    }
  }

  /**
    * @return all the responses in the database
    */
  def findAll(): List[Response] = {
    transaction {
      from(repo)(s => select(s)).toList
    }
  }
}
