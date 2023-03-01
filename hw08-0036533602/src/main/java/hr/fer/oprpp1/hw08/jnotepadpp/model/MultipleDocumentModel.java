package hr.fer.oprpp1.hw08.jnotepadpp.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface <code>MultipleDocumentModel</code> defines how will we communicate with our multiple document models
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
  /**
   * Returns the graphical component which is responsible for displaying the entire
   * MultipleDocumentModel‘s user interfac
   * @return the graphical component which is responsible for displaying the entire {@link MultipleDocumentModel} user interface
   */
  JComponent getVisualComponent();

  /**
   * Creates new instance of {@link SingleDocumentModel}
   * @return instance of {@link SingleDocumentModel}
   */
  SingleDocumentModel createNewDocument();

  /**
   * Returns active document
   * @return instance of {@link SingleDocumentModel} that is currently active
   */
  SingleDocumentModel getCurrentDocument();

  /**
   * Loads document froy system with <code>path</code>
   * @param path path to file
   * @return instance of {@link SingleDocumentModel}
   */
  SingleDocumentModel loadDocument(Path path);

  /**
   * Saves document with text in <code>model</code> to <code>newPath</code>
   * @param model model that we are saving
   * @param newPath path to which we save file, it can be null
   */
  void saveDocument(SingleDocumentModel model, Path newPath);

  /**
   * Closes current document
   * @param model model to close
   */
  void closeDocument(SingleDocumentModel model);

  /**
   * Adding a {@link MultipleDocumentListener} instance
   * @param l {@link MultipleDocumentListener} instance
   */
  void addMultipleDocumentListener(MultipleDocumentListener l);

  /**
   * Removes a {@link MultipleDocumentListener} instance
   * @param l {@link MultipleDocumentListener} instance
   */
  void removeMultipleDocumentListener(MultipleDocumentListener l);

  /**
   * Return number of documents in {@link MultipleDocumentModel} instance
   *
   * @return number of documents in {@link MultipleDocumentModel} instance
   */
  int getNumberOfDocuments();

  /**
   * Gets {@link SingleDocumentModel} instance from {@link MultipleDocumentModel} with that <code>index</code>
   * @param index index to get {@link SingleDocumentModel} from  {@link MultipleDocumentModel}
   * @return Gets {@link SingleDocumentModel} instance from {@link MultipleDocumentModel} with that <code>index</code>
   * @throws IllegalArgumentException if index is invalid
   */
  SingleDocumentModel getDocument(int index);

  /**
   * Finds model with <code>path</code>
   * @param path path to find file
   * @return null if paths doesn't exists in all {@link SingleDocumentModel} models that are in {@link MultipleDocumentModel}
   */
  SingleDocumentModel findForPath(Path path);

  /**
   * Return index of <code>doc</code> or -1 if doesn't exists
   * @param doc {@link SingleDocumentModel} instance for which we search index
   * @return -1 if it doesn't exist, else it returns an index
   */
  int getIndexOfDocument(SingleDocumentModel doc);
}
