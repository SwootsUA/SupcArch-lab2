package individ;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;

public class WN extends Sim_entity {
	private Sim_port in, out;
	private double delay;
	public int jobs_count = 0;
	public static Boolean flag = false;
	
	WN(String name, double delay) {
		super(name);
		this.delay = delay;
		in = new Sim_port("In");
		out = new Sim_port("Out");
		add_port(in);
		add_port(out);
	}
	
	@Override
	public void body() {
		while(Sim_system.running()) {
			Sim_event event = new Sim_event();
			sim_get_next(event);
			
			if(event.from_port(in)) {
				sim_process(delay);
				sim_completed(event);
				sim_trace(Sim_system.get_trc_level(), "Workload No." + jobs_count + " has been processed!");
				
				if(Client.jobs_count >= ++jobs_count) {
					//if all jobs have been processed
					flag = true;
				}
			}
			
			if(flag) {
				sim_schedule(out, 1.0, ++jobs_count);
				// sim_trace(Sim_system.get_trc_level(), "All the results have been sent to CE!");
			} else continue;
		}
	}
}