package ericj.chelan.raft.messages

import akka.actor.ActorRef

case class Init(cluster: Array[ActorRef])

object HeartBeat

object ElectionTimeout

sealed trait RaftMessage {
  val term: Int
}

sealed trait RaftRequest extends RaftMessage

sealed trait RaftResponse extends RaftMessage {
  val success: Boolean
}

case class ClientRequest(payload: Any)

object ClientResponse

case class AppendEntriesRequest(term: Int = 0,
  prevLogIndex: Int = 0,
  prevLogTerm: Int = 0,
  entries: List[NewEntry] = List.empty,
  leaderCommit: Int = 0) extends RaftRequest

case class NewEntry(term: Int, value: Any)

case class AppendEntriesResponse(term: Int, lastAgreeIndex: Option[Int] = None) extends RaftResponse {
  override val success: Boolean = lastAgreeIndex != None
}

case class RequestVoteRequest(term: Int) extends RaftRequest

case class RequestVoteResponse(term: Int, success: Boolean) extends RaftResponse

