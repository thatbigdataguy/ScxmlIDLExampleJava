package thesis.scxmlidl.second.profile;

import java.io.Serializable;

import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;

public class SimpleCommonsMonitor implements CommonsMonitorListener,Serializable {

	@Override
	public void onEntry(EnterableState arg0) {
		// TODO Auto-generated method stub
		System.out.println("onEntry");
	}

	@Override
	public void onExit(EnterableState arg0) {
		// TODO Auto-generated method stub
		System.out.println("onExit");

	}

	@Override
	public void onTransition(TransitionTarget arg0, TransitionTarget arg1, Transition arg2, String arg3) {
		// TODO Auto-generated method stub
		System.out.println("onTransition");

	}

}
