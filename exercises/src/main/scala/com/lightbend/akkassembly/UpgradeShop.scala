package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ClosedShape, FlowShape}
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge}

class UpgradeShop {
  val installUpgrades: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = Flow.fromGraph(
    GraphDSL.create() {
      implicit builder: GraphDSL.Builder[NotUsed] =>

        val balance = builder.add(Balance[UnfinishedCar](3))
        val f1 = Flow[UnfinishedCar].map(x => x.installUpgrade(Upgrade.DX))
        val f2 = Flow[UnfinishedCar].map(x => x.installUpgrade(Upgrade.Sport))
        val f3 = Flow[UnfinishedCar]
        val merge = builder.add(Merge[UnfinishedCar](3))

        import GraphDSL.Implicits._

        balance ~> f1 ~> merge
        balance ~> f2 ~> merge
        balance ~> f3 ~> merge

        FlowShape(balance.in, merge.out)
    }
  )
}
