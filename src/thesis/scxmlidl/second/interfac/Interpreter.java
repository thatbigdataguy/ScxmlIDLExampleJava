package thesis.scxmlidl.second.interfac;

import java.net.URL;
import java.util.List;

import thesis.scxmlidl.second.impl.Event;
import thesis.scxmlidl.second.profile.CommonsMonitorListener;

public interface Interpreter {
	public void setStateMachine(URL url);

	public void triggerEvents();

	public void addEventToQueue(Event e);

	public void addMonitor();

	public void triggerEvent(Event e);

	public List<String> allStateIds();

	public List<String> activeStateIds();

	public boolean isActiveState(String stateId);

}
