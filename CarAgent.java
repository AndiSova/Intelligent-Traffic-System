package com.ssn.aso.project.project;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CarAgent extends Agent {
	private static final long serialVersionUID = 1L;

	protected void setup() {
		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					String content = msg.getContent();
					if (content.equals("green light")) {
						System.out.println(getLocalName() + ": The traffic light is green.");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(getLocalName() + ": I can go now.");
						ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
						reply.setContent("car passed");
						reply.addReceiver(new AID("police", AID.ISLOCALNAME));
						send(reply);
					} else if (content.equals("red light")) {
						System.out.println(getLocalName() + ": The traffic light is red. I must stop.");
					}
				} else {
					block();
				}
			}
		});
	}
}