package task;

import eduni.simjava.Sim_system;

public class Grid {
	public static void main(String [] args) {
		Sim_system.initialise();
		Client client = new Client("Client", 10.0);
		CE ce = new CE("CE", 2.0);
		WN wn1 = new WN("WN1", 50);
		WN wn2 = new WN("WN2", 50);
		Sim_system.link_ports("Client","Out","CE","In1");
		Sim_system.link_ports("CE","Out1","WN1","In");
		Sim_system.link_ports("CE","Out2","WN2","In");
		Sim_system.link_ports("WN1","Out","CE","In2");
		Sim_system.link_ports("WN2","Out","CE","In3");
		Sim_system.link_ports("CE","Out3","Client","In");
		Sim_system.set_trace_detail(false, true, false);
		Sim_system.run();
	}
}