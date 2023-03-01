package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Interface <code>SingleDocumentListener</code> listens changes on single document
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface SingleDocumentListener {
    /**
     * Sets modified status
     *
     * @param model model that is currently using
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Triggers when file path is updated for <code>model</code>
     *
     * @param model model that is currently using
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
