package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class <code>FormLocalizationProvider</code> extends {@link LocalizationProviderBridge}
 *
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

  /**
   * Constructor for <code>FormLocalizationProvider</code> and adds window listener so it connects to <code>parent</code> when window is opened and
   * disconnected from parent when window is closed
   *
   * @param parent instance of {@link ILocalizationProvider}
   * @param frame frame of application
   */
  public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
    super(parent);

    frame.addWindowListener(new WindowAdapter() {

      @Override
      public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        connect();
      }

      @Override
      public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        disconnect();
      }
    });
  }
}
