package model;

import java.util.ArrayList;
import java.util.List;


public abstract class Observable{
	private List<Observer> observers;
	private boolean changed;
	
	public Observable(){
		observers = new ArrayList<Observer>();
		changed = false;
	}
	
	public boolean isChanged(){return changed;}
	
	public void markChange(){changed = true;}
	
	public void unMarkChange(){changed = false;}
	
	public void addObserver(Observer obs){
		observers.add(obs);
	}

	public void removeObserver(Observer obs){
		observers.remove(obs);
	}
	
	public void notifyObservers(){
		if(isChanged()){
			unMarkChange();
			for(Observer obs: observers){
				obs.update(this);
			}
		}
	}

}
