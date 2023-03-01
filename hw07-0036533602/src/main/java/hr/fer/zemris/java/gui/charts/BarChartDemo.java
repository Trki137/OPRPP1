package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <code>BarChartDemo</code> class models a swing application for displaying graphs
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 */
public class BarChartDemo extends JFrame {

  /**
   * Component for painting graphs
   */
  private final BarChart barChart;

  /**
   * File path from which we read information for chart
   */
  private final String filePath;

  /**
   * Sets up swing application
   *
   * @param barChart component for painting graphs
   * @param filePath file path from which we read information for chart
   */
  public BarChartDemo(BarChart barChart, String filePath){
    this.barChart = barChart;
    this.filePath = filePath;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    initGui();
    setLocationRelativeTo(null);
    pack();
  }

  /**
   * Sets up graphical interface
   */

  private void initGui(){
    Container container = getContentPane();
    container.setLayout(new BorderLayout());
    container.setPreferredSize(new Dimension(700,300));
    container.add(new JLabel(filePath), BorderLayout.PAGE_START);
    container.add(new BarChartComponent(barChart), BorderLayout.CENTER);

  }

  /**
   * Entry point that starts our swing application
   *
   * @param args an array of command-line arguments.
   * @throws IllegalArgumentException if <code>args</code> contains more or less than 1 command-line argument
   * @throws IOException if <code>args</code> contains invalid path to file
   *
   */
  public static void main(String[] args) {
    Objects.requireNonNull(args);

    if(args.length != 1)
      throw new IllegalArgumentException("Expected one argument, but got "+ args.length);

    Path path = Path.of(args[0]);
    List<String> fileLines = null;
    try {
      fileLines = Files.readAllLines(path, StandardCharsets.UTF_8);

    }catch (IOException e){
      System.out.println("Couldn't open and read file "+ args[0]);
    }

    if(fileLines == null) return;

    String[] freq = fileLines.get(2).split(" ");

    List<XYValue> xyValueList = new ArrayList<>();

    for (String s : freq) {
      String[] xyValue = s.split(",");
      xyValueList.add(new XYValue(
          Integer.parseInt(xyValue[0]),
          Integer.parseInt(xyValue[1])
      ));
    }

    BarChart barChart = new BarChart(
        xyValueList,
        fileLines.get(0),
        fileLines.get(1),
        Integer.parseInt(fileLines.get(3)),
        Integer.parseInt(fileLines.get(4)),
        Integer.parseInt(fileLines.get(5))
    );

    SwingUtilities.invokeLater(() -> new BarChartDemo(barChart,path.toAbsolutePath().toString()).setVisible(true));

    }
}
