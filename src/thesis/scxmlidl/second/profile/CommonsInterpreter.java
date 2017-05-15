package thesis.scxmlidl.second.profile;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.scxml2.Context;
import org.apache.commons.scxml2.Evaluator;
import org.apache.commons.scxml2.SCXMLExecutor;
import org.apache.commons.scxml2.TriggerEvent;
import org.apache.commons.scxml2.env.SimpleErrorReporter;
import org.apache.commons.scxml2.env.jexl.JexlEvaluator;
import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;

import thesis.scxmlidl.second.example.StopWatch;
import thesis.scxmlidl.second.example.StopWatchFrame;
import thesis.scxmlidl.second.impl.Event;
import thesis.scxmlidl.second.interfac.AbstractInterpreter;
import thesis.scxmlidl.second.interfac.Monitor;

public class CommonsInterpreter extends AbstractInterpreter {
	// Commons SCXML
	Evaluator evaluator;
	SCXMLExecutor executor;
	// parse SCXML URL into SCXML model
	SCXML scxml = null;
	
	List<String> allStateIds = new ArrayList<String> ();
	List<String> activeStateIds = new ArrayList<String> ();

	public CommonsInterpreter() {
		// evaluator instance which is used by SCXML engine to evaluate
		// expressions in SCXML
		evaluator = new JexlEvaluator();
		// engine to execute the scxml instance
		executor = new SCXMLExecutor(evaluator, null, new SimpleErrorReporter());

		super.setInterpreterState(InterpreterState.INTERPRETER_INSTANTIATED);
	}

	@Override
	public void setStateMachine(URL url) {
		// TODO Auto-generated method stub
		final URL SCXML = url;

		try {
			scxml = SCXMLReader.read(SCXML);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// set state machine (scxml instance) to execute
		try {
			executor.setStateMachine(scxml);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Profile Method
		setExecutorContext();
		initiateExecutor();

		
		super.setInterpreterState(InterpreterState.INTERPRETER_INITIALIZED);
	}

	@Override
	public void setExecutorContext() {
		// TODO Auto-generated method stub

		// create root context storing variables and being used by evaluator
		Context rootContext = evaluator.newContext(null);

		// create stopWatch object and add it to rootContext
		// to be able to script with that in SCXML.
		StopWatch stopWatch = new StopWatch();
		rootContext.set("stopWatch", stopWatch);

		// set the root context for the engine
		executor.setRootContext(rootContext);
	}

	@Override
	public void triggerEvents() {
		// TODO Auto-generated method stub
		while (!super.getEventQueue().isEmpty()) {
			Event event = super.getEventQueue().remove();
			System.out.println(event.name);
			try {
				executor.triggerEvent(new TriggerEvent(event.name, TriggerEvent.SIGNAL_EVENT));
			} catch (ModelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			super.setInterpreterState(InterpreterState.INTERPRETER_IDLE);
	}

}

	@Override
	public void addEventToQueue(Event e) {
		// TODO Auto-generated method stub
		super.getEventQueue().add(e);
		super.setInterpreterState(InterpreterState.INTERPRETER_INITIALIZED);
		
	}

	@Override
	public void addMonitor() {
		// TODO Auto-generated method stub
		executor.addListener(scxml, new SimpleCommonsMonitor());
		super.setInterpreterState(InterpreterState.INTERPRETER_INITIALIZED);
		
	}

	@Override
	public void initiateExecutor() {
		// TODO Auto-generated method stub
		// initiate the execution of the state machine
		try {
			executor.go();
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new StopWatchFrame(executor);
	}

	@Override
	public void triggerEvent(Event e) {
		// TODO Auto-generated method stub
		Event event = e;
		System.out.println("Event Raised: "+event.name);
		try {
			executor.triggerEvent(new TriggerEvent(event.name, TriggerEvent.SIGNAL_EVENT));
		} catch (ModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		super.setInterpreterState(InterpreterState.INTERPRETER_IDLE);
		
		return;
	}

	@Override
	public SCXMLExecutor getCommonsScxmlExecutor() {
		// TODO Auto-generated method stub
		return executor;
	}

	@Override
	public List<String> allStateIds() {
		// TODO Auto-generated method stub
		int i = executor.getStateMachine().getChildren().size();
		for(int count=0; count<i;count++)
			allStateIds.add(executor.getStateMachine().getChildren().get(count).getId());
		
		return allStateIds;
	}

	@Override
	public List<String> activeStateIds() {
		// TODO Auto-generated method stub
		Iterator entries = executor.getStatus().getActiveStates().iterator();
		while (entries.hasNext()) {
		  activeStateIds.add(entries.next().toString());
		  
		}
		return activeStateIds;
	}

	@Override
	public boolean isActiveState(String stateId) {
		// TODO Auto-generated method stub
		return executor.getStatus().isInState(stateId);
	}
}
