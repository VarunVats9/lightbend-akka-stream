package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

import scala.collection.immutable.Seq

class EngineShop(shipmentSize: Int) {
  val shipments: Source[Shipment, NotUsed] = Source.fromIterator(() => Iterator.continually(Shipment(Seq.fill(shipmentSize)(Engine(new SerialNumber)))))
  val engines: Source[Engine, NotUsed] = shipments via Flow[Shipment].flatMapConcat(shipment => Source(shipment.engines))
  val installEngine: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = Flow[UnfinishedCar].zip(engines).map(x => x._1.installEngine(x._2))
}
