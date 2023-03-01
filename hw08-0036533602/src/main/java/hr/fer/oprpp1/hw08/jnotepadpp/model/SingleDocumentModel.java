package hr.fer.oprpp1.hw08.jnotepadpp.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface <code>SingleDocumentModel</code> defines how will we communicate with our single document models
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface SingleDocumentModel {

  /**
   * Getting text component
   * @return instance of <code>JTextArea</code>
   */
  JTextArea getTextComponent();

  /**
   * Path to file, it can be null
   *
   * @return path to file
   */
  Path getFilePath();

  /**
   * Sets new path for a file of this model
   * @param path new path
   */
  void setFilePath(Path path);

  /**
   * Returns if file is modified or not
   * @return true if file is modified else false
   */
  boolean isModified();

  /**
   * Sets modified flag to <code>modified</code>
   * @param modified new modified flag value
   */
  void setModified(boolean modified);

  /**
   * Adds listener to document
   * @param l instance of <code>SingleDocumentListener</code>
   */
  void addSingleDocumentListener(SingleDocumentListener l);

  /**
   * Removes listener to document
   * @param l instance of <code>SingleDocumentListener</code>
   */
  void removeSingleDocumentListener(SingleDocumentListener l);
}