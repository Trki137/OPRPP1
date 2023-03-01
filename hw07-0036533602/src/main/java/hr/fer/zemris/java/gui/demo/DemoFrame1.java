package hr.fer.zemris.java.gui.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;

public class DemoFrame1 extends JFrame {

  public DemoFrame1(){
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    initGui();
    pack();
  }

  private void initGui(){
    Container cp = getContentPane();
    cp.setLayout(new CalcLayout(3));
    cp.add(l("Tekst 1"), new RCPosition(1,1));
    cp.add(l("Tekst 1,6"), new RCPosition(1,6));
    cp.add(l("Tekst 1,7"), new RCPosition(1,7));
    cp.add(l("Tekst 2"), new RCPosition(2,3));
    cp.add(l("Tekst stvarno najdulji"), new RCPosition(2,7));
    cp.add(l("Tekst kraci"), new RCPosition(4,2));
    cp.add(l("Tekst srednji"), new RCPosition(4,5));
    cp.add(l("Tekst"), new RCPosition(4,7));
  }

  private JLabel l(String text){
    JLabel l = new JLabel(text);
    l.setBackground(Color.YELLOW);
    l.setOpaque(true);
    return l;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new DemoFrame1().setVisible(true));
  }

}
