package com.ssn.aso.project.project;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		rt.setCloseVM(true);
		Profile profile = new ProfileImpl(null, 1200, null);
		ContainerController cc = rt.createAgentContainer(profile);
		try {
			AgentController policeAgent = cc.createNewAgent("police", PoliceAgent.class.getName(), new Object[0]);
			policeAgent.start();
			AgentController trafficLightAgent = cc.createNewAgent("trafficLight", TrafficLightAgent.class.getName(),
					new Object[0]);
			trafficLightAgent.start();
			for (int i = 1; i <= 6; i++) {
				AgentController carAgent = cc.createNewAgent("car" + i, CarAgent.class.getName(), new Object[0]);
				carAgent.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}