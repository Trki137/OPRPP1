package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * <code>PrimDemo</code> class is swing application that has 2 lists and button for generating new prim number
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class PrimDemo extends JFrame {
  /**
   * Model for our lists
   */
  private final PrimListModel primListModel;

  /**
   * Constructor for <code>PrimDemo</code> in which we do our setup
   */
  public PrimDemo(){
    this.primListModel = new PrimListModel();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("List of prime numbers");
    setSize(350,200);
    initGui();
    setLocationRelativeTo(null);
  }

  /**
   * Method that initializes graphical interface
   */
  private void initGui(){

    Container container = getContentPane();
    container.setLayout(new BorderLayout());

    JPanel pane = new JPanel(new GridLayout(1,0));

    JList<Integer> jListPrim1 = createJList();
    JList<Integer> jListPrim2 = createJList();
    JButton button = new JButton("Sljedeci");

    button.addActionListener(e -> primListModel.next());
    pane.setLayout(new GridLayout(1,0));

    pane.add(new JScrollPane(jListPrim1));
    pane.add(new JScrollPane(jListPrim2));
    container.add(pane,BorderLayout.CENTER);
    container.add(button,BorderLayout.PAGE_END);

  }

  /**
   * Creates new list of type Integer
   * @return new <code>JList</code> of type Integer
   */
  private JList<Integer> createJList(){
    JList<Integer> jListPrim1 = new JList<>(primListModel);
    jListPrim1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    return jListPrim1;
  }

  /**
   * Entry point that starts our swing application
   *
   * @param args an array of command-line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
  }
}
