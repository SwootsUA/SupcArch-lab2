package task;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;

public class CE extends Sim_entity {
	private Sim_port in1, in2, in3, out1, out2, out3;
	private double delay;
	public static Boolean link = false;
	private boolean flag1 = false, flag2 = false;
	
	CE(String name, double delay) {
		super(name);
		this.delay = delay;
		in1 = new Sim_port("In1");
		in2 = new Sim_port("In2");
		in3 = new Sim_port("In3");
		out1 = new Sim_port("Out1");
		out2 = new Sim_port("Out2");
		out3 = new Sim_port("Out3");
		add_port(in1);
		add_port(in2);
		add_port(in3);
		add_port(out1);
		add_port(out2);
		add_port(out3);
	}
	
	@Override
	public void body() {
		int i = 0;
		
		while(Sim_system.running()) {
			Sim_event event = new Sim_event();
			sim_wait(event);
			
			if(event.from_port(in1)) {
				sim_process(delay);
				sim_completed(event);
				
				if(i % 2 == 0) {
					sim_schedule(out1, 1.0, i);
				} else {
					sim_schedule(out2, 1.0, i);
				}
				
				sim_trace(Sim_system.get_trc_level(), "Workload No." + i + " from CE has been sent...");
				++i;
			}
			
			if(event.from_port(in2)) {
				flag1 = true;
			}
			if(event.from_port(in3)) {
				flag2 = true;
			}
			
			if((flag1 && flag2) && WN.flag) {
				WN.flag = false;
				sim_process(delay / 2);
				sim_completed(event);
				link = true;
				sim_trace(Sim_system.get_trc_level(), "The output directory generated!");
				sim_schedule(out3, 3.0, ++i);
			} else continue;
		}
	}
}