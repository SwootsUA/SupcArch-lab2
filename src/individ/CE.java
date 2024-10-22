package individ;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;

public class CE extends Sim_entity {
	private Sim_port in1, in2, in3, in4, in5, out1, out2, out3, out4, out5;
	private double delay;
	public static Boolean link = false;
	
	CE(String name, double delay) {
		super(name);
		this.delay = delay;
		in1 = new Sim_port("In1");
		in2 = new Sim_port("In2");
		in3 = new Sim_port("In3");
		in4 = new Sim_port("In4");
		in5 = new Sim_port("In5");
		out1 = new Sim_port("Out1");
		out2 = new Sim_port("Out2");
		out3 = new Sim_port("Out3");
		out4 = new Sim_port("Out4");
		out5 = new Sim_port("Out5");
		add_port(in1);
		add_port(in2);
		add_port(in3);
		add_port(in4);
		add_port(in5);
		add_port(out1);
		add_port(out2);
		add_port(out3);
		add_port(out4);
		add_port(out5);
	}
	
	@Override
	public void body() {
		int i = 0;
		boolean flag1 = false, flag2 = false, flag3 = false, flag4 = false;
		
		while(Sim_system.running()) {
			Sim_event event = new Sim_event();
			sim_wait(event);
			
			if(event.from_port(in1)) {
				sim_process(delay);
				sim_completed(event);
				
				sim_schedule(out1, 1.0, i);
				sim_process(delay);
				sim_schedule(out2, 1.0, i+1);
				sim_process(delay);
				sim_schedule(out3, 1.0, i+2);
				sim_process(delay);
				sim_schedule(out4, 1.0, i+3);
				sim_trace(Sim_system.get_trc_level(), "Workload No." + i + " from CE has been sent...");
				++i;
			}
			
			if(event.from_port(in2)) {
				sim_trace(Sim_system.get_trc_level(), "Workload part No." + 1 + " from WN has been received");
				flag1 = true;
			}
			if(event.from_port(in3)) {
				sim_trace(Sim_system.get_trc_level(), "Workload part No." + 2 + " from WN has been received");
				flag2 = true;
			}
			if(event.from_port(in4)) {
				sim_trace(Sim_system.get_trc_level(), "Workload part No." + 3 + " from WN has been received");
				flag3 = true;
			}
			if(event.from_port(in5)) {
				sim_trace(Sim_system.get_trc_level(), "Workload part No." + 4 + " from WN has been received");
				flag4 = true;
			}
			
			if((flag1 && flag2 && flag3 && flag4) && WN.flag) {
				WN.flag = false;
				sim_process(delay / 2);
				sim_completed(event);
				link = true;
				sim_trace(Sim_system.get_trc_level(), "The output directory generated!");
				sim_schedule(out5, 3.0, ++i);
			} else continue;
		}
	}
}