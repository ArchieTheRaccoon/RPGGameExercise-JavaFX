package engine;

import ui.GameObserver;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ObservableGamePart {
//    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
//
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        changeSupport.addPropertyChangeListener(listener);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        changeSupport.removePropertyChangeListener(listener);
//    }
//
//    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
//        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
//    }

    private ArrayList<GameObserver> observerList = new ArrayList<>();

    public void addObserver(GameObserver o) {
        if (!observerList.contains(o)) {
            observerList.add(o);
        }
    }

    public void deleteObserver(GameObserver o) {
        observerList.remove(o);
    }

    public void notifyObservers(GameObserver.Event eventType, Object content) {
        for (GameObserver obs : observerList) {
            obs.updateGUI(eventType, content);
        }
    }
}
