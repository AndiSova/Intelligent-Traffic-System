package com.ssn.aso.project.project;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class PoliceAgent extends Agent {
	private static final long serialVersionUID = 1L;
	private int carsPassed = 0;
	private int totalCarsPassed = 0;
	private boolean lightIsGreen = false;
	private Random rand = new Random();

	protected void setup() {
		addBehaviour(new TickerBehaviour(this, 15000) {
			private static final long serialVersionUID = 1L;

			protected void onTick() {
				lightIsGreen = !lightIsGreen;

				int numCars = rand.nextInt(6) + 1;
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("green light");
				for (int i = 1; i <= numCars; i++) {
					carsPassed++;
					msg.addReceiver(new AID("car" + i, AID.ISLOCALNAME));
				}
				send(msg);
			}
		});

		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					String content = msg.getContent();
					if (content.equals("car passed")) {
						totalCarsPassed++;
						if (carsPassed > 0) {
							System.out.println(getLocalName() + ": " + carsPassed + " cars have passed.");
						}
						carsPassed = 0;
						if (totalCarsPassed == 100) {
							System.out.println(getLocalName() + ": " + totalCarsPassed
									+ " cars have passed in total. Stopping the traffic.");
							for (int i = 1; i <= 6; i++) {
								AgentController carAgent;
								try {
									carAgent = getContainerController().getAgent("car" + i);
									carAgent.kill();
								} catch (ControllerException e) {
									e.printStackTrace();
								}

							}
							doDelete();
						}
					}
				} else {
					block();
				}
			}
		});
	}
}