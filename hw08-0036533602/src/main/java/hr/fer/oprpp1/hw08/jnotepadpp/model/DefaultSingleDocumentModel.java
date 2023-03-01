package hr.fer.oprpp1.hw08.jnotepadpp.model;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class <code>DefaultSingleDocumentModel</code> that represents an implementation of interface {@link SingleDocumentModel}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel{

  /**
   * Path to file
   */
  private Path filePath;

  /**
   * Text component
   */

  private final JTextArea textComponent;

  /**
   * Flag for describing if document is modified, document changed but not saved
   */

  private boolean isModified;

  /**
   * List of {@link SingleDocumentListener}
   */
  private final List<SingleDocumentListener> singleDocumentListenerList;

  /**
   * Constructor for <code>DefaultSingleDocumentModel</code> in which we add new {@link SingleDocumentListener} to collection <code>singleDocumentListenerList</code>
   *
   * @param filePath path to file, it can be null
   * @param textComponent text component for this model
   */

  public DefaultSingleDocumentModel(Path filePath, JTextArea textComponent) {
    this.filePath = filePath;
    this.textComponent = textComponent;
    this.isModified = false;
    this.singleDocumentListenerList = new ArrayList<>();
    textComponent.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setModified(true);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setModified(true);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setModified(true);
      }
    });



  }

  @Override
  public JTextArea getTextComponent() {
    return textComponent;
  }

  @Override
  public Path getFilePath() {
    return filePath;
  }

  @Override
  public void setFilePath(Path path) {
    Objects.requireNonNull(path, "Path can't be null");

    this.filePath = path;

    singleDocumentListenerList.forEach(listener -> listener.documentFilePathUpdated(this));
  }

  @Override
  public boolean isModified() {
    return isModified;
  }

  @Override
  public void setModified(boolean modified) {
    this.isModified = modified;

    singleDocumentListenerList.forEach(listener -> listener.documentModifyStatusUpdated(this));
  }

  @Override
  public void addSingleDocumentListener(SingleDocumentListener l) {
    singleDocumentListenerList.add(l);
  }

  @Override
  public void removeSingleDocumentListener(SingleDocumentListener l) {
    singleDocumentListenerList.remove(l);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultSingleDocumentModel that = (DefaultSingleDocumentModel) o;

    if(filePath == null)
      return textComponent.equals(that.textComponent);

    return filePath.equals(that.filePath) && textComponent.equals(that.textComponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filePath, textComponent);
  }

}
