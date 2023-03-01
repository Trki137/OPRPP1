package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Interface <code>MultipleDocumentListener</code> listens changes on multiple document model
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public interface MultipleDocumentListener {
  /**
   * Triggers when tab is changed
   *
   * @param previousModel model that was active before
   * @param currentModel model that is now active
   * @throws IllegalStateException if <code>previousModel</code> and <code>currentModel</code> are null
   */
  void currentDocumentChanged(SingleDocumentModel previousModel,
                              SingleDocumentModel currentModel);

  /**
   * Triggers when new instance of {@link SingleDocumentModel} to instance of {@link MultipleDocumentModel}
   * @param model new instance of {@link SingleDocumentModel}
   */
  void documentAdded(SingleDocumentModel model);

  /**
   * Triggers when {@link SingleDocumentModel} instance is removed from {@link MultipleDocumentModel} model
   * @param model {@link SingleDocumentModel} instance to be removed
   */
  void documentRemoved(SingleDocumentModel model);
}
