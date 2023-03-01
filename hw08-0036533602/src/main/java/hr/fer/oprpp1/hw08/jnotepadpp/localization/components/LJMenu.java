package hr.fer.oprpp1.hw08.jnotepadpp.localization.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;
import java.io.Serial;

public class LJMenu extends JMenu {
  @Serial
  private static final long serialVersionUID = 1L;

  private final String key;

  private final ILocalizationProvider provider;

  private final ILocalizationListener listener;

  public LJMenu(String key, ILocalizationProvider provider){
    this.key = key;
    this.provider = provider;
    this.listener = () -> setText(provider.getString(key));

    provider.addLocalizationListener(listener);
    updateMenu();
  }

  private void updateMenu(){
    setText(provider.getString(key));
  }
}
