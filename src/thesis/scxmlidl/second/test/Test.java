package thesis.scxmlidl.second.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import thesis.scxmlidl.second.impl.Event;
import thesis.scxmlidl.second.interfac.Monitor;
import thesis.scxmlidl.second.profile.CommonsInterpreter;
import thesis.scxmlidl.second.profile.CommonsMonitorListener;
import thesis.scxmlidl.second.profile.SimpleCommonsMonitor;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("test");

		CommonsInterpreter interpreter = new CommonsInterpreter();
		File f = new File(
				"/home/sajjad/Workspace-Thesis/SCXMLAPIFINAL/src/thesis/api/scxmlfinal/example/stopwatch.xml");

		URL url = null;
		try {
			url = f.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(interpreter.getInterpreterState());
		CommonsMonitorListener monitor = new SimpleCommonsMonitor();
		interpreter.setStateMachine(url);
		System.out.println(interpreter.getInterpreterState());
		interpreter.addMonitor();
		System.out.println(interpreter.getInterpreterState());

		interpreter.addEventToQueue(new Event("watch.start"));
		interpreter.addEventToQueue(new Event("watch.stop"));
		System.out.println(interpreter.getInterpreterState());

		interpreter.triggerEvent(new Event("watch.start"));
		System.out.println(interpreter.getInterpreterState());

		interpreter.triggerEvent(new Event("watch.stop"));
		interpreter.triggerEvent(new Event("watch.reset"));

		interpreter.triggerEvents();
		System.out.println(interpreter.getInterpreterState());

		System.out.println("States in machine");

		int i = interpreter.allStateIds().size();
		for (int count = 0; count < i; count++)
			System.out.println(interpreter.allStateIds().get(count));

		int j = interpreter.activeStateIds().size();
		// for (int count = 0; count < j; count++)
		// System.out.println("Active States:" +
		// interpreter.activeStateIds().get(count));

		// System.out.println(interpreter.isActiveState("stopped"));
	}

}
