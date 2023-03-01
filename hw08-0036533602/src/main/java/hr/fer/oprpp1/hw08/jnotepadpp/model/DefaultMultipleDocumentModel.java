package hr.fer.oprpp1.hw08.jnotepadpp.model;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

/**
 * Class <code>DefaultMultipleDocumentModel</code> that represents an implementation of interface {@link MultipleDocumentModel}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class DefaultMultipleDocumentModel  extends JTabbedPane implements MultipleDocumentModel {

  /**
   * Current <code>JFrame</code> in application
   */
  private final JFrame currentFrame;

  /**
   * Localization provider
   */

  private final FormLocalizationProvider flp;

  /**
   * Image icon when file is modified and not saved
   */
  private ImageIcon changedDiskette = new ImageIcon();

  /**
   * Image icon when file is not modified or modified but saved
   */
  private ImageIcon notChangedDiskette = new ImageIcon();

  /**
   * List of {@link SingleDocumentModel} models
   */
  private final List<SingleDocumentModel> singleDocumentModelList;

  /**
   * Currently active {@link SingleDocumentModel}
   */
  private SingleDocumentModel currentDocumentModel;

  /**
   * List of {@link MultipleDocumentListener}
   */

  private final List<MultipleDocumentListener> multipleDocumentListenerList;

  /**
   * Constructor for <code>DefaultMultipleDocumentModel</code
   * >
   * @param currentFrame current application frame
   * @param flp localization provider
   */
  public DefaultMultipleDocumentModel(final JFrame currentFrame, final FormLocalizationProvider flp) {
    super(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);

    this.currentFrame = currentFrame;
    this.flp = flp;
    this.singleDocumentModelList = new ArrayList<>();
    this.currentDocumentModel = null;
    this.multipleDocumentListenerList = new ArrayList<>();

    this.addChangeListener(e -> {
      if(singleDocumentModelList.size() == 0) {
        currentDocumentModel = null;
        return;
      }

      SingleDocumentModel prevDocumentModel = currentDocumentModel;
      currentDocumentModel = singleDocumentModelList.get(DefaultMultipleDocumentModel.this.getSelectedIndex());

      multipleDocumentListenerList.forEach(listener -> listener.currentDocumentChanged(prevDocumentModel,currentDocumentModel));
    });

    String pathToChangedDiskette = "/hr.fer.oprpp1.hw08.jnotepadpp.icons/diskette_changed.png";
    String pathToNotChangedDiskette = "/hr.fer.oprpp1.hw08.jnotepadpp.icons/diskette_not_changed.png";

    try(InputStream isDisketteChanged = this.getClass().getResourceAsStream(pathToChangedDiskette);
        InputStream isDisketteNotChanged = this.getClass().getResourceAsStream(pathToNotChangedDiskette)){

      if(isDisketteChanged == null) changedDiskette = null;
      else setDisketteIcon(changedDiskette, isDisketteChanged);


      if(isDisketteNotChanged == null) notChangedDiskette = null;
      else setDisketteIcon(notChangedDiskette, isDisketteNotChanged);

    }catch (IOException ignored){

    }
  }


  @Override
  public JComponent getVisualComponent() {
    return this;
  }

  @Override
  public SingleDocumentModel createNewDocument() {
    SingleDocumentModel prevDocumentModel = currentDocumentModel;
    SingleDocumentModel newSingleDocumentModel = new DefaultSingleDocumentModel(null,new JTextArea());

    singleDocumentModelList.add(newSingleDocumentModel);
    currentDocumentModel = newSingleDocumentModel;

    this.addTab("(unnamed)", notChangedDiskette,newSingleDocumentModel.getTextComponent(),"(unnamed)");
    this.setSelectedIndex(singleDocumentModelList.indexOf(newSingleDocumentModel));

    createSimpleDocumentListener(newSingleDocumentModel);

    multipleDocumentListenerList.forEach(listener -> listener.currentDocumentChanged(prevDocumentModel, currentDocumentModel));
    multipleDocumentListenerList.forEach(listener -> listener.documentAdded(newSingleDocumentModel));


    return currentDocumentModel;
  }



  @Override
  public SingleDocumentModel getCurrentDocument() {
    return currentDocumentModel;
  }

  @Override
  public SingleDocumentModel loadDocument(Path path) {
    Objects.requireNonNull(path, "Path must not be null");

    if(!Files.isReadable(path)){
      showErrorMessageDialog(String.format("%s %s %s",flp.getString("file") ,path.getFileName().toString(), flp.getString("not_exists")));
      return null;
    }

    for(int i = 0; i < singleDocumentModelList.size(); i++){
      Path modelPath = singleDocumentModelList.get(i).getFilePath();
      if(path.equals(modelPath)){
        SingleDocumentModel prevDocumentModel = currentDocumentModel;

        currentDocumentModel = singleDocumentModelList.get(i);
        setSelectedIndex(i);

        multipleDocumentListenerList.forEach(listener -> listener.currentDocumentChanged(prevDocumentModel, currentDocumentModel));

        return singleDocumentModelList.get(i);
      }
    }

    try {
      String text = Files.readString(path);

      SingleDocumentModel prevDocumentModel = currentDocumentModel;
      SingleDocumentModel newSingleDocumentModel = new DefaultSingleDocumentModel(path,new JTextArea(text));
      singleDocumentModelList.add(newSingleDocumentModel);

      currentDocumentModel = newSingleDocumentModel;

      this.addTab(path.getFileName().toString(), notChangedDiskette,newSingleDocumentModel.getTextComponent(), path.toAbsolutePath().toString());
      this.setSelectedIndex(singleDocumentModelList.size() - 1);

      createSimpleDocumentListener(newSingleDocumentModel);


      multipleDocumentListenerList.forEach(listener -> listener.currentDocumentChanged(prevDocumentModel,currentDocumentModel));
      multipleDocumentListenerList.forEach(listener -> listener.documentAdded(newSingleDocumentModel));

      return newSingleDocumentModel;

    }catch (IOException e){
      showErrorMessageDialog(String.format("%s %s",flp.getString("file_reading_error"), path.getFileName().toString()));
     return null;
    }

  }

  @Override
  public void saveDocument(SingleDocumentModel model, Path newPath) {
    Path pathToSave = newPath == null ? model.getFilePath() : newPath;


    if (pathToSave == null) {
      pathToSave = selectFile();
      if(pathToSave == null) return;
    }

    SingleDocumentModel alreadyExists = findForPath(pathToSave);
    if(alreadyExists != null) {
      showErrorMessageDialog(flp.getString("file")+ " "+ pathToSave.getFileName()+ " " + flp.getString("already_opened"));
      return;
    }

    try {
      Files.writeString(pathToSave, model.getTextComponent().getText());
    }catch (IOException ignored){}

    int index = singleDocumentModelList.indexOf(model);

    if(!pathToSave.equals(model.getFilePath())){
      model.setFilePath(pathToSave);
      setTitleAt(singleDocumentModelList.indexOf(model), pathToSave.getFileName().toString());
    }

    model.setModified(false);
    setIconAt(index, notChangedDiskette);

  }

  @Override
  public void closeDocument(SingleDocumentModel model) {
    int index = singleDocumentModelList.indexOf(model);
    singleDocumentModelList.remove(model);
    this.remove(index);

    if(getSelectedIndex() != -1) {
      currentDocumentModel = singleDocumentModelList.get(getSelectedIndex());
      multipleDocumentListenerList.forEach(listener -> listener.currentDocumentChanged(model, currentDocumentModel));
    }else currentDocumentModel = null;
    multipleDocumentListenerList.forEach(listener -> listener.documentRemoved(model));
  }

  @Override
  public void addMultipleDocumentListener(MultipleDocumentListener l) {
    multipleDocumentListenerList.add(l);
  }

  @Override
  public void removeMultipleDocumentListener(MultipleDocumentListener l) {
    multipleDocumentListenerList.remove(l);
  }

  @Override
  public int getNumberOfDocuments() {
    return singleDocumentModelList.size();
  }

  @Override
  public SingleDocumentModel getDocument(int index) {
    if(index >= singleDocumentModelList.size())
      throw new IllegalArgumentException("Invalid index");

    return singleDocumentModelList.get(index);
  }

  @Override
  public SingleDocumentModel findForPath(Path path) {
    Objects.requireNonNull(path, "Path can't be null");

    for(SingleDocumentModel singleDocumentModel : singleDocumentModelList){
      if(singleDocumentModel.equals(currentDocumentModel)){
        continue;
      }
      Path modelPath = singleDocumentModel.getFilePath();
      if(path.equals(modelPath)) return singleDocumentModel;
    }
    return null;
  }

  @Override
  public int getIndexOfDocument(SingleDocumentModel doc) {
    for(int i = 0; i < singleDocumentModelList.size(); i++)
      if(singleDocumentModelList.get(i).equals(doc)) return i;

    return -1;
  }

  @Override
  public Iterator<SingleDocumentModel> iterator() {
    return new SingleDocumentIterator(singleDocumentModelList);
  }

  /**
   * Creates a new {@link SingleDocumentListener} listener and saves it to <code>newSingleDocumentModel</code>
   * @param newSingleDocumentModel instance of {@link SingleDocumentModel} that is just added to collection <code>singleDocumentModelList</code>
   */

  private void createSimpleDocumentListener(SingleDocumentModel newSingleDocumentModel) {
    Objects.requireNonNull(newSingleDocumentModel);

    SingleDocumentListener newListener = new SingleDocumentListener() {
      @Override
      public void documentModifyStatusUpdated(SingleDocumentModel model) {
        int indexOfModel = singleDocumentModelList.indexOf(model);

        if(model.isModified())  setIconAt(indexOfModel, changedDiskette);
        else  setIconAt(indexOfModel, notChangedDiskette);

      }

      @Override
      public void documentFilePathUpdated(SingleDocumentModel model) {
      }
    };

    newSingleDocumentModel.addSingleDocumentListener(newListener);
  }

  /**
   * Sets scaled image ,that is read from <code>is</code>, to <code>icon</code>
   * @param icon instance of {@link ImageIcon}
   * @param is instance of {@link InputStream} from which we read image
   * @throws IOException if there is problem with reading image
   */

  private void setDisketteIcon(ImageIcon icon, InputStream is) throws IOException {
    Objects.requireNonNull(is);
    Objects.requireNonNull(icon);

    BufferedImage image = ImageIO.read(is);
    Image scaled = image.getScaledInstance(15, 15,Image.SCALE_SMOOTH);
    icon.setImage(scaled);
  }

  /**
   * Shows error dialog with <code>message</code>
   * @param message to display to user
   */
  private void showErrorMessageDialog(String message){
    JOptionPane.showMessageDialog(
        currentFrame,
        message,
        flp.getString("error"),
        JOptionPane.ERROR_MESSAGE
    );
  }

  /**
   * Selects file path
   * @return selected file path, it can be null if user cancels operation
   */
  private Path selectFile() {
    JFileChooser fileChooser = new JFileChooser();

    fileChooser.setDialogTitle(flp.getString("save_file_chooser_title"));

    int returnValue = fileChooser.showSaveDialog(currentFrame);

    if(returnValue == JFileChooser.APPROVE_OPTION){
      File f = fileChooser.getSelectedFile();
      if(f.exists()){
        int res = JOptionPane.showConfirmDialog(
            currentFrame,
            String.format("%s %s %s", flp.getString("start_of_already_exists"),f.getName(), flp.getString("already_exists")),
            flp.getString("already_exists_title"),
            JOptionPane.YES_NO_CANCEL_OPTION
        );

        return switch (res) {
          case JOptionPane.CANCEL_OPTION, JOptionPane.NO_OPTION -> null;
          default -> f.toPath();
        };
      }

      return f.toPath();
    }

    return null;
  }

  /**
   * Creates an iterator so we can iterate over {@link SingleDocumentModel}
   *
   * @author Dean Trkulja
   * @version 1.0
   */

  private static final class SingleDocumentIterator implements Iterator<SingleDocumentModel> {

    private final List<SingleDocumentModel> singleDocumentModelList;

    private int currentIndex;

    private final int size;
    public SingleDocumentIterator(List<SingleDocumentModel> singleDocumentModelList){
      this.singleDocumentModelList = singleDocumentModelList;
      this.currentIndex = 0;
      this.size = singleDocumentModelList.size();
    }
    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
      return currentIndex != size;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public SingleDocumentModel next() {
      if(!hasNext())
        throw new NoSuchElementException();

      return singleDocumentModelList.get(currentIndex++);
    }
  }
}
