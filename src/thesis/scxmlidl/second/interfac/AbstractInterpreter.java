package thesis.scxmlidl.second.interfac;

import java.util.LinkedList;
import java.util.Queue;

import thesis.scxmlidl.second.impl.Event;
import thesis.scxmlidl.second.profile.CommonsInterpreterProfile;

public abstract class AbstractInterpreter implements Interpreter,CommonsInterpreterProfile {

	InterpreterState currentState;
	Queue<Event> eventQueue = new LinkedList<Event>();
	
	public enum InterpreterState {
		INTERPRETER_INITIALIZED, // Initialized
		INTERPRETER_INSTANTIATED, // Instantiated
		INTERPRETER_IDLE // Processed all events in the Queue and in Idle State

	}
	
	public InterpreterState getInterpreterState(){
		return currentState;
		
	}
	
	public void setInterpreterState(InterpreterState state){
		currentState=state;
		
	}
	
	public Queue<Event> getEventQueue(){
		return eventQueue;
	}
}
