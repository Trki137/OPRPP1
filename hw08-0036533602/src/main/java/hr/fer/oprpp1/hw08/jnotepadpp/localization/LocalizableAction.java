package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.io.Serial;
import java.util.Objects;

/**
 * Class <code>LocalizableAction</code> extends {@link AbstractAction}.
 * Adding lozalizable functionality that {@link AbstractAction} doesn't have
 */

public abstract class LocalizableAction extends AbstractAction {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Key for which we get text from <code>iLocalizationProvider</code>
   */
  private final String key;

  /**
   * Instance of {@link ILocalizationProvider}
   */
  private final ILocalizationProvider iLocalizationProvider;

  /**
   * Instance of {@link ILocalizationListener}
   */
  private final ILocalizationListener listener;

  /**
   * Constructor foor <code>LocalizableAction</code>
   * @param iLocalizationProvider instance of {@link ILocalizationProvider}
   * @param key key from which action gets its name
   */

  public LocalizableAction(ILocalizationProvider iLocalizationProvider, String key ){
    Objects.requireNonNull(iLocalizationProvider);

    this.key = key;
    this.iLocalizationProvider = iLocalizationProvider;
    this.listener = () -> putValue(Action.NAME, iLocalizationProvider.getString(this.key));

    this.iLocalizationProvider.addLocalizationListener(listener);

    this.putValue(Action.NAME, iLocalizationProvider.getString(key));
  }
}
