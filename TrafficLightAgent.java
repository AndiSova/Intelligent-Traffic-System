package com.ssn.aso.project.project;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class TrafficLightAgent extends Agent {
	private static final long serialVersionUID = 1L;
	private boolean lightIsGreen = false;

	protected void setup() {
		addBehaviour(new TickerBehaviour(this, 5000) {
			private static final long serialVersionUID = 1L;

			protected void onTick() {
				lightIsGreen = !lightIsGreen;
				if (lightIsGreen) {
				} else {
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent("red light");
					for (int i = 1; i <= 6; i++) {
						msg.addReceiver(new AID("car" + i, AID.ISLOCALNAME));
					}
					send(msg);
				}
			}
		});
	}
}