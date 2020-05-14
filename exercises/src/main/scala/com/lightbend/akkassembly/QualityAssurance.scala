package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow
import com.lightbend.akkassembly.QualityAssurance.CarFailedInspection

class QualityAssurance {
  val inspect: Flow[UnfinishedCar, Car, NotUsed] = Flow[UnfinishedCar].map {
    x => if (x.color.nonEmpty && x.engine.nonEmpty && x.wheels.size == 4)
              Car(SerialNumber.apply(), x.color.get, x.engine.get, x.wheels, x.upgrade)
          else throw new CarFailedInspection(x)
  }.withAttributes(ActorAttributes.supervisionStrategy(decider = {
    case _: CarFailedInspection => Supervision.resume
    case _ => Supervision.stop
  }))
}

object QualityAssurance {
  class CarFailedInspection(car: UnfinishedCar)
      extends IllegalStateException(s"The unfinished car does not meet necessary criteria $car")
}
