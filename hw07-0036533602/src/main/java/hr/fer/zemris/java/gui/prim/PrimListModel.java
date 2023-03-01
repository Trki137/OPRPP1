package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <code>PrimListModel</code> class represent model for JList in {@link PrimDemo}.
 * It implements {@link ListModel}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer> {
  /**
   * List of all listeners for our model
   */
  private final List<ListDataListener> listeners;

  /**
   * List of all generated prime numbers
   */
  private final List<Integer> primList;

  /**
   * Constructor for <code>PrimListModel</code>
   */
  public PrimListModel(){
    this.primList = new ArrayList<>();
    this.listeners = new ArrayList<>();
    primList.add(1);
  }

  /**
   * Generates new prim number and saves is to <code>primList</code>
   */
  public void next(){
    int nextNumber = primList.get(primList.size() - 1) + 1;
    while(true){
      if(isPrime(nextNumber)) {
        primList.add(nextNumber);
        break;
      }
      nextNumber++;
    }
    ListDataEvent event = new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED,primList.size() - 1,primList.size() - 1);
    listeners.forEach(listener -> listener.intervalAdded(event));
  }

  /**
   * Checks if <code>num</code> is a prime number
   * @param num number to check if it is a prime
   * @return true if <code>num</code> is a prime number, else false
   */
  private boolean isPrime(int num){
    return IntStream.rangeClosed(2, (int)Math.sqrt(num)).allMatch(n -> num % n != 0);
  }

  public List<Integer> getPrimList() {
    return primList;
  }


  /**
   * Returns the length of the list.
   *
   * @return the length of the list
   */
  @Override
  public int getSize() {
    return primList.size();
  }

  /**
   * Returns the value at the specified index.
   *
   * @param index the requested index
   * @return the value at <code>index</code>
   */
  @Override
  public Integer getElementAt(int index) {
    return primList.get(index);
  }

  /**
   * Adds a listener to the list that's notified each time a change
   * to the data model occurs.
   *
   * @param l the <code>ListDataListener</code> to be added
   */
  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.add(l);
  }

  /**
   * Removes a listener from the list that's notified each time a
   * change to the data model occurs.
   *
   * @param l the <code>ListDataListener</code> to be removed
   */
  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.remove(l);
  }
}
