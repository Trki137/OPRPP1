package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.components.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.model.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import java.text.CollationKey;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * <code>JNotepadPP</code> class represents a notepad++ swing application
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class JNotepadPP extends JFrame {

  /**
   * Clock label
   */
  private final JLabel clock = new JLabel();

  /**
   * Lenght label in status bar
   */

  private final JLabel length = new JLabel();

  /**
   * Line label in status bar
   */

  private final JLabel line = new JLabel();

  /**
   * Column label in status bar
   */

  private final JLabel column = new JLabel();

  /**
   * Label selected in status bar
   */

  private final JLabel selected = new JLabel();

  /**
   * Formatter for clock
   */

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

  /**
   * Flag for clock
   */

  private volatile boolean clockRuns = true;

  /**
   * Localization provider
   */
  private final LocalizationProvider provider = LocalizationProvider.getInstance();

  /**
   * Derived from <code>LocalizationProviderBridge</code>, used for connecting and disconnecting
   */
  private final FormLocalizationProvider flp = new FormLocalizationProvider(provider, this);

  /**
   * Derived from <code>JTabbedPane</code>
   */
  private final MultipleDocumentModel mdm = new DefaultMultipleDocumentModel(this,flp);

  /**
   * Text for paste action
   */
  private String copiedText = "";

  public JNotepadPP(){
    WindowListener wl = new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {

        boolean cancelExit = closeAllTabs();

        if (cancelExit) return;

        clockRuns = false;
        dispose();


      }
    };


    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    addWindowListener(wl);
    setTitle("JNotepad++");
    setSize(800,300);
    setLocationRelativeTo(null);
    initGui();
  }

  /**
   * GUI initialization for <code>JNotepadPP</code>
   */
  private void initGui(){

    getContentPane().setLayout(new BorderLayout());

    getContentPane().add(new JScrollPane(mdm.getVisualComponent()), BorderLayout.CENTER);

    initializeActions();
    setUpMultipleDocumentListener();
    createMenus();
    createToolbar();
    createStatusBar();

  }

  /**
   * Creates <code>SingleDocumentListener</code> and <code>MultipleDocumentListener</code>.
   * Adds <code>MultipleDocumentListener</code> instance to <code>mdm</code>
   */
  private void setUpMultipleDocumentListener(){

    SingleDocumentListener singleDocumentListener = new SingleDocumentListener() {
      @Override
      public void documentModifyStatusUpdated(SingleDocumentModel model) {
        saveFileAction.setEnabled(model.isModified());
      }

      @Override
      public void documentFilePathUpdated(SingleDocumentModel model) {
        setTitle(model.getFilePath() == null ? "(unnamed) - JNotepad++" : model.getFilePath().toString() + " - JNotepad++");
        JTabbedPane pane = (JTabbedPane) mdm.getVisualComponent();
        pane.setToolTipTextAt(mdm.getIndexOfDocument(model),model.getFilePath().toAbsolutePath().toString());
      }
    };

    MultipleDocumentListener multipleDocumentListener = new MultipleDocumentListener() {
      @Override
      public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
          if(previousModel == null && currentModel == null)
            throw new IllegalStateException("Previous and current model can't be null");

          if(previousModel != null)
            previousModel.removeSingleDocumentListener(singleDocumentListener);

          if(currentModel != null)
            currentModel.addSingleDocumentListener(singleDocumentListener);

          if(currentModel == null){
            setTitle("JNotepad++");

            disableActions();

            setLengthLabel("-");
            setLineLabel("-");
            setColumnLabel("-");
            setSelectedLabel("-");
          }else{
            setTitle(currentModel.getFilePath() == null ? "(unnamed) - JNotepad++" : currentModel.getFilePath().toAbsolutePath() + " - JNotepad++");

            Caret caret = currentModel.getTextComponent().getCaret();

            int length = Math.abs(caret.getDot() - caret.getMark());

            saveFileAsAction.setEnabled(true);
            closeAllTabsActions.setEnabled(true);
            closeTabAction.setEnabled(true);
            showStatisticsAction.setEnabled(true);
            pasteAction.setEnabled(!copiedText.isBlank());

            setStatusBarLabels(currentModel,caret,length);
          }
      }

      @Override
      public void documentAdded(SingleDocumentModel model) {
        model.getTextComponent().getCaret().addChangeListener(l -> {
          Caret caret = (Caret) l.getSource();

          int length = Math.abs(caret.getDot() - caret.getMark());

          setStatusBarLabels(model,caret,length);

          cutAction.setEnabled(length != 0);
          copyAction.setEnabled(length != 0);
          toUpperCaseAction.setEnabled(length != 0);
          toLowerCaseAction.setEnabled(length != 0);
          invertCaseAction.setEnabled(length != 0);
          sortAscending.setEnabled(length != 0);
          sortDescending.setEnabled(length != 0);
          uniqueAction.setEnabled(length != 0);

          saveFileAction.setEnabled(true);
          saveFileAsAction.setEnabled(true);
          closeAllTabsActions.setEnabled(true);
          closeTabAction.setEnabled(true);
        });
      }

      @Override
      public void documentRemoved(SingleDocumentModel model) {
        if(mdm.getNumberOfDocuments() != 0) return;

        disableActions();

      }
    };
    mdm.addMultipleDocumentListener(multipleDocumentListener);

  }

  /**
   * Disables actions for some actions
   */
  private void disableActions(){
    saveFileAction.setEnabled(false);
    saveFileAction.setEnabled(false);
    saveFileAsAction.setEnabled(false);
    closeTabAction.setEnabled(false);
    closeAllTabsActions.setEnabled(false);
    showStatisticsAction.setEnabled(false);
    toLowerCaseAction.setEnabled(false);
    invertCaseAction.setEnabled(false);
    uniqueAction.setEnabled(false);
    sortAscending.setEnabled(false);
    sortDescending.setEnabled(false);
}

  /**
   * Action for creating new file
   */

  private final Action createNewFileAction = new LocalizableAction(flp,"new") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      mdm.createNewDocument();
    }
  };

  /**
   * Action for opening file from system
   */
  private final Action openFileAction = new LocalizableAction(flp,"open") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Choose a file to open");

      int returnValue = fileChooser.showOpenDialog(JNotepadPP.this);


      if(returnValue != JFileChooser.APPROVE_OPTION)  return;

      File selectedFile = fileChooser.getSelectedFile();
      Path path = selectedFile.toPath();

      SingleDocumentModel singleDocumentModel = mdm.loadDocument(path);

      if(singleDocumentModel == null) {
        JOptionPane.showMessageDialog(
            JNotepadPP.this,
            "An error occurred while reading file " + selectedFile.getName(),
            flp.getString("error"),
            JOptionPane.ERROR_MESSAGE
        );
      }
    }
  };

  /**
   * Action for saving file
   */

  private final Action saveFileAction = new LocalizableAction(flp,"save") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      mdm.saveDocument(mdm.getCurrentDocument(), null);
    }
  };

  /**
   * Action for saving file and rename it
   */
  private final Action saveFileAsAction = new LocalizableAction(flp,"save_as") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();

      fileChooser.setDialogTitle(flp.getString("Specify_a_file_to_save"));

      int returnValue = fileChooser.showSaveDialog(JNotepadPP.this);

      if(returnValue != JFileChooser.APPROVE_OPTION) return;

      File file = fileChooser.getSelectedFile();
      Path path = file.toPath();

      if(file.exists()){
        String[] options = new String[] {flp.getString("yes"), flp.getString("no")};


        int res = JOptionPane.showOptionDialog(
            JNotepadPP.this,
            String.format("%s %s %s", flp.getString("file"), file.getName(), flp.getString("already_exists")),
            flp.getString("close_tab"),
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[0]
        );

        if(res != 0) return;
      }

      mdm.saveDocument(mdm.getCurrentDocument(),path);
    }
  };

  /**
   * Action for closing current(active) tab
   */

  private final Action closeTabAction = new LocalizableAction(flp,"close") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      SingleDocumentModel model = mdm.getCurrentDocument();
      boolean cancel = false;

      if(model.isModified())
        cancel = showWarningDialog(model);

      if(!cancel)
        mdm.closeDocument(mdm.getCurrentDocument());

    }
  };

  /**
   * Action for closing all tabs
   */

  private final Action closeAllTabsActions = new LocalizableAction(flp,"close_all") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      boolean cancel = closeAllTabs();

      if(cancel) return;

      while(mdm.getNumberOfDocuments() != 0) mdm.closeDocument(mdm.getCurrentDocument());
    }
  };

  /**
   * Action for exiting application
   */

  private final Action exitAction = new LocalizableAction(flp,"exit") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      boolean cancelExit = closeAllTabs();

      if (cancelExit) return;

      clockRuns = false;
      dispose();
    }
  };

  /**
   * Action for cutting text
   */

  private final Action cutAction = new LocalizableAction(flp,"cut") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {

      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();
      int[] data = getOffsetAndLength();

      try {
        copiedText = document.getText(data[0],data[1]);
        document.remove(data[0],data[1]);
        pasteAction.setEnabled(true);
      } catch (BadLocationException ex) {
        throw new RuntimeException(ex);
      }
    }
  };

  /**
   * Action for copying text
   */

  private final Action copyAction = new LocalizableAction(flp,"copy") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {

      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();
      int[] data = getOffsetAndLength();

      try {
        copiedText = document.getText(data[0],data[1]);
        pasteAction.setEnabled(true);
      } catch (BadLocationException ex) {
        throw new RuntimeException(ex);
      }

    }
  };

  /**
   * Action for pasting text
   */

  private final Action pasteAction = new LocalizableAction(flp,"paste") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {

      JTextArea currentTextArea = mdm.getCurrentDocument().getTextComponent();
      Document document = currentTextArea.getDocument();
      Caret caret = currentTextArea.getCaret();

      try {
        document.insertString(caret.getDot(), copiedText,document.getDefaultRootElement().getAttributes());
      } catch (BadLocationException ex) {
        throw new RuntimeException(ex);
      }

    }
  };

  /**
   * Action for showing statistics to user
   */
  private final Action showStatisticsAction = new LocalizableAction(flp,"show_statistics") {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();

      int numOfCharacters = 0;
      int numOfNonBlankCharacters = 0;
      int numOfLines = 0;
      String documentText;
      try {
        documentText = document.getText(0,document.getLength());
      } catch (BadLocationException ex) {
        throw new RuntimeException(ex);
      }

      char[] chars = documentText.toCharArray();
      if(chars.length > 0) numOfLines++;
      for(char c : chars){
        if(!Character.isWhitespace(c)){
          numOfCharacters++;
          numOfNonBlankCharacters++;
        }else numOfCharacters++;

        if(c == '\n') numOfLines++;
      }

      JOptionPane.showMessageDialog(
          JNotepadPP.this,
          String.format("%s %d %s, %d %s %s %s %d %s.",flp.getString("document_has"), numOfCharacters,flp.getString("characters"),numOfNonBlankCharacters,flp.getString("non_blank"),flp.getString("characters"),flp.getString("and"),numOfLines,flp.getString("lines")),
          flp.getString("statistical_info"),
          JOptionPane.INFORMATION_MESSAGE
      );


    }
  };

  /**
   * Action for upper-casing selected text
   */

  private final Action toUpperCaseAction = new LocalizableAction(flp,"upper_case") {
    @Override
    public void actionPerformed(ActionEvent e) {
      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();
      Caret caret = mdm.getCurrentDocument().getTextComponent().getCaret();

      int start = Math.min(caret.getDot(),caret.getMark());
      int end = Math.max(caret.getDot(),caret.getMark());
      try {
        for (int i = start; i <= end; i++) {
          String letter = document.getText(i, 1);
          char character = letter.toCharArray()[0];

          if(Character.isUpperCase(character)) continue;

          document.remove(i,1);
          document.insertString(i,letter.toUpperCase(),document.getDefaultRootElement().getAttributes());
        }
      }catch (Exception ignored){}

    }
  };

  /**
   * Action for lower-casing selected text
   */

  private final Action toLowerCaseAction = new LocalizableAction(flp,"lower_case") {
    @Override
    public void actionPerformed(ActionEvent e) {
      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();
      Caret caret = mdm.getCurrentDocument().getTextComponent().getCaret();

      int start = Math.min(caret.getDot(),caret.getMark());
      int end = Math.max(caret.getDot(),caret.getMark());
      try {
        for (int i = start; i <= end; i++) {
          String letter = document.getText(i, 1);
          char character = letter.toCharArray()[0];

          if(Character.isLowerCase(character)) continue;

          document.remove(i,1);
          document.insertString(i,letter.toLowerCase(),document.getDefaultRootElement().getAttributes());
        }
      }catch (Exception ignored){}
    }
  };

  /**
   * Action for inverting case for selected text
   */

  private final Action invertCaseAction = new LocalizableAction(flp,"inverse_case") {
    @Override
    public void actionPerformed(ActionEvent e) {
      Document document = mdm.getCurrentDocument().getTextComponent().getDocument();
      Caret caret = mdm.getCurrentDocument().getTextComponent().getCaret();

      int start = Math.min(caret.getDot(),caret.getMark());
      int end = Math.max(caret.getDot(),caret.getMark());
      try {
        for (int i = start; i <= end; i++) {
          String letter = document.getText(i, 1);
          char character = letter.toCharArray()[0];

          document.remove(i,1);
          document.insertString(i,Character.isLowerCase(character) ? letter.toUpperCase() : letter.toLowerCase(),document.getDefaultRootElement().getAttributes());
        }
      }catch (Exception ignored){}
    }
  };

  /**
   * Action for sorting selected lines ascending
   */

  private final Action sortAscending = new LocalizableAction(flp, "ascending") {
    @Override
    public void actionPerformed(ActionEvent e) {
      sortSelectedText(SortType.ASCENDING);
    }
  };

  /**
   * Action for sorting selected lines descending
   */

  private final Action sortDescending = new LocalizableAction(flp, "descending") {
    @Override
    public void actionPerformed(ActionEvent e) {
      sortSelectedText(SortType.DESCENDING);
    }
  };

  /**
   * Removing lines that are not unique in document
   */

  private final Action uniqueAction = new LocalizableAction(flp,"unique") {
    @Override
    public void actionPerformed(ActionEvent e) {
      JTextComponent textComponent = mdm.getCurrentDocument().getTextComponent();
      Document document = textComponent.getDocument();
      Caret caret = mdm.getCurrentDocument().getTextComponent().getCaret();
      Element root =  document.getDefaultRootElement();

      int minPositionRow = Math.min(caret.getDot(),caret.getMark());
      int maxPositionRow = Math.max(caret.getDot(),caret.getMark());
      int minRow = root.getElementIndex(minPositionRow);
      int maxRow = root.getElementIndex(maxPositionRow);

      List<String> lines = new ArrayList<>();

      for(int i = minRow; i <= maxRow; i++){
        try{
          Element element = root.getElement(i);
          lines.add(document.getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset()));
        }catch (BadLocationException ignored){}
      }

      Locale locale = new Locale(flp.getCurrentLanguage());
      Collator collator = Collator.getInstance(locale);
      List<CollationKey> keys = new ArrayList<>();
      for (String s : lines)
        keys.add(collator.getCollationKey(s));

      List<CollationKey> uniqueKeys = new ArrayList<>();
      for(CollationKey key : keys)
        if(!uniqueKeys.contains(key))
          uniqueKeys.add(key);

      int counter = 0;
      for(int i = minRow; i <= maxRow; i++) {
        try {
          Element element = root.getElement(i);
          int startOffset = element.getStartOffset();
          int endOffset = element.getEndOffset() == document.getEndPosition().getOffset() ? element.getEndOffset() - 1 : element.getEndOffset();

          document.remove(startOffset, endOffset - startOffset);
          if(counter == uniqueKeys.size()){
            maxRow--;
            i--;
          }
          if (counter != uniqueKeys.size())
            document.insertString(startOffset, uniqueKeys.get(counter++).getSourceString(), element.getAttributes());
        } catch (BadLocationException ignored) {}
      }

    }
  };

  /**
   * Action for setting english language for app
   */
  private final Action setLanguageToEnglishAction = new LocalizableAction(flp,"en") {
    @Override
    public void actionPerformed(ActionEvent e) {
      provider.setLanguage("en");
      setLanguagesEnabled();
    }

  };

  /**
   * Action for setting croatian language for app
   */
  private final Action setLanguageToCroatianAction = new LocalizableAction(flp,"hr") {
    @Override
    public void actionPerformed(ActionEvent e) {
      provider.setLanguage("hr");
      setLanguagesEnabled();
    }
  };


  /**
   * Action for setting deutsch language for app
   */

  private final Action setLanguageToDeutschAction = new LocalizableAction(flp,"de") {
    @Override
    public void actionPerformed(ActionEvent e) {
      provider.setLanguage("de");
      setLanguagesEnabled();
    }
  };

  /**
   * Initializes actions
   */
  private void initializeActions() {
    createNewFileAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK)
    );

    createNewFileAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_N
    );

    openFileAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK)
    );

    openFileAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_O
    );

    saveFileAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)
    );

    saveFileAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_S
    );

    saveFileAction.setEnabled(false);

    saveFileAsAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)
    );

    saveFileAsAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_A
    );

    saveFileAsAction.setEnabled(false);

    closeTabAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)
    );

    closeTabAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_C
    );

    closeTabAction.setEnabled(false);

    closeAllTabsActions.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_DOWN_MASK)
    );

    closeAllTabsActions.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_L
    );

    closeAllTabsActions.setEnabled(false);

    exitAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_DOWN_MASK)
    );

    exitAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_E
    );

    cutAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK)
    );

    cutAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_U
    );

    cutAction.setEnabled(false);

    copyAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK)
    );

    copyAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_Y
    );

    copyAction.setEnabled(false);

    pasteAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK)
    );

    pasteAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_P
    );

    pasteAction.setEnabled(false);

    showStatisticsAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK)
    );

    showStatisticsAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_I
    );

    showStatisticsAction.setEnabled(false);

    toUpperCaseAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)
    );

    toUpperCaseAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_U
    );

    toUpperCaseAction.setEnabled(false);

    toLowerCaseAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK)
    );

    toLowerCaseAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_O
    );

    toLowerCaseAction.setEnabled(false);

    invertCaseAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)
    );

    invertCaseAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_R
    );

    invertCaseAction.setEnabled(false);

    sortAscending.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)
    );

    sortAscending.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_D
    );

    sortAscending.setEnabled(false);

    sortDescending.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_DOWN_MASK)
    );

    sortDescending.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_G
    );

    sortDescending.setEnabled(false);

    uniqueAction.putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK)
    );

    uniqueAction.putValue(
        Action.MNEMONIC_KEY,
        KeyEvent.VK_U
    );

    uniqueAction.setEnabled(false);
  }

  /**
   * Creating menus for application
   */
  private void createMenus() {

    JMenuBar menuBar = new JMenuBar();

    LJMenu file = new LJMenu("file",flp);
    LJMenu edit = new LJMenu("edit",flp);
    LJMenu languages = new LJMenu("select_language",flp);
    LJMenu tools = new LJMenu("tools", flp);
    LJMenu changeCase = new LJMenu("change_case", flp);
    LJMenu sort = new LJMenu("sort", flp);

    file.add(new JMenuItem(createNewFileAction));
    file.add(new JMenuItem(openFileAction));
    file.add(new JMenuItem(saveFileAction));
    file.add(new JMenuItem(saveFileAsAction));
    file.add(new JMenuItem(closeTabAction));
    file.add(new JMenuItem(closeAllTabsActions));
    file.add(new JMenuItem(exitAction));

    edit.add(new JMenuItem(cutAction));
    edit.add(new JMenuItem(copyAction));
    edit.add(new JMenuItem(pasteAction));
    edit.add(new JMenuItem(showStatisticsAction));
    edit.add(languages);

    languages.add(new JMenuItem(setLanguageToEnglishAction));
    languages.add(new JMenuItem(setLanguageToCroatianAction));
    languages.add(new JMenuItem(setLanguageToDeutschAction));


    changeCase.add(new JMenuItem(toUpperCaseAction));
    changeCase.add(new JMenuItem(toLowerCaseAction));
    changeCase.add(new JMenuItem(invertCaseAction));

    sort.add(new JMenuItem(sortAscending));
    sort.add(new JMenuItem(sortDescending));

    tools.add(changeCase);
    tools.add(sort);
    tools.add(new JMenuItem(uniqueAction));

    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(tools);

    this.setJMenuBar(menuBar);

    setLanguagesEnabled();
  }

  /**
   * Creating toolbar for application
   */

  private void createToolbar() {
    Container container = getContentPane();
    JToolBar toolBar = new JToolBar();

    toolBar.add(createNewFileAction);
    toolBar.add(saveFileAction);
    toolBar.add(saveFileAsAction);
    toolBar.add(closeTabAction);
    toolBar.add(closeAllTabsActions);
    toolBar.add(exitAction);

    toolBar.addSeparator();

    toolBar.add(cutAction);
    toolBar.add(copyAction);
    toolBar.add(pasteAction);
    toolBar.add(showStatisticsAction);

    toolBar.addSeparator();

    toolBar.add(toUpperCaseAction);
    toolBar.add(toLowerCaseAction);
    toolBar.add(invertCaseAction);
    toolBar.add(sortAscending);
    toolBar.add(sortDescending);
    toolBar.add(uniqueAction);

    container.add(toolBar, BorderLayout.PAGE_START);
  }

  /**
   * Creating status bar for application
   */
  private void createStatusBar(){
    JToolBar statusBar = new JToolBar();
    statusBar.setFloatable(false);
    statusBar.setLayout(new GridLayout(1,2));

    JPanel infoPanel = new JPanel();
    JPanel panel = new JPanel();

    panel.setLayout(new GridLayout(1,3));
    infoPanel.setLayout(new GridLayout(1,2));

    setColumnLabel("-");
    setLineLabel("-");
    setSelectedLabel("-");
    setLengthLabel("-");

    panel.add(line);
    panel.add(column);
    panel.add(selected);

    infoPanel.add(length);
    infoPanel.add(panel);

    statusBar.add(infoPanel);

    clock.setText(formatter.format(LocalDateTime.now()));

    clock.setHorizontalAlignment(SwingUtilities.RIGHT);
    statusBar.add(clock);

    clock();

    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  /**
   * Sets languages enabled or disabled based on current language in menus
   */
  private void setLanguagesEnabled() {
    setLanguageToCroatianAction.setEnabled(!provider.getCurrentLanguage().equals("hr"));
    setLanguageToEnglishAction.setEnabled(!provider.getCurrentLanguage().equals("en"));
    setLanguageToDeutschAction.setEnabled(!provider.getCurrentLanguage().equals("de"));
    JFileChooser.setDefaultLocale(Locale.forLanguageTag(flp.getCurrentLanguage()));
  }

  /**
   * Method that checks if there are not saved files and asks if they want to save it or not or jus cancel closing tabs
   * @return true if user press cancel else false
   */
  private boolean closeAllTabs() {
    boolean cancel = false;

    int numOfDocuments = mdm.getNumberOfDocuments();

    for(int i = 0; i < numOfDocuments && !cancel; i++){
      SingleDocumentModel model = mdm.getDocument(i);
      if(model.isModified()){
        cancel = showWarningDialog(model);
      }
    }

    return cancel;
  }

  /**
   * Shows warning dialog for closing not saved files
   * @param model instance of model that is closing
   * @return true if user presses cancel, else false
   */
  private boolean showWarningDialog(SingleDocumentModel model) {
    Objects.requireNonNull(model);

    boolean cancel = false;
    String fileName = model.getFilePath() == null ? "(unnamed)": model.getFilePath().toString();
    String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};


    int res = JOptionPane.showOptionDialog(
        JNotepadPP.this,
        String.format("%s %s %s", flp.getString("file"), fileName, flp.getString("option_dialog_exit")),
        flp.getString("close_tab"),
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.WARNING_MESSAGE,
        null,
        options,
        options[0]
    );

    switch (res) {
      case 0 -> mdm.saveDocument(model, null);
      case JOptionPane.CLOSED_OPTION, JOptionPane.CANCEL_OPTION -> cancel = true;
    }

    return cancel;
  }

  /**
   * Gets caret offset and length of caret dot and caret mark
   * @return integer array where first element is offset and second is length
   */

  private int[] getOffsetAndLength() {
    JTextArea currentTextArea = mdm.getCurrentDocument().getTextComponent();
    Caret caret = currentTextArea.getCaret();

    int length = Math.abs(caret.getDot() - caret.getMark());
    int offset = Math.min(caret.getDot(), caret.getMark());

    return new int[]{offset, length};
  }

  /**
   * Method for updating clock in status bar
   */
  private void clock(){
    updateTime();

    Thread t = new Thread(() -> {
      while(clockRuns){
        try{
          Thread.sleep(500);
        }catch (InterruptedException ignored){}
        SwingUtilities.invokeLater(this::updateTime);
      }
    });

    t.start();
  }

  /**
   * Updates label <code>clock</code>
   */
  private void updateTime(){
    clock.setText(formatter.format(LocalDateTime.now()));
  }

  /**
   * Calculates and sets status bar infos
   * @param model instance of <code>SingleDocumentModal</code>
   * @param caret instance of <code>Caret</code>
   * @param selectedLength of selected text in text document
   */

  private void setStatusBarLabels(SingleDocumentModel model, Caret caret, int selectedLength) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(caret);

    int textLength = model.getTextComponent().getDocument().getLength();

    int lineNum = 1;
    int columnNum = 1;

    try{
      int caretPosition = caret.getDot();
      lineNum = model.getTextComponent().getLineOfOffset(caretPosition);
      columnNum = caretPosition - model.getTextComponent().getLineStartOffset(lineNum);

      lineNum += 1;
      columnNum += 1;
    }catch (Exception ignored){}

    setSelectedLabel(selectedLength == 0 ? "-" : String.valueOf(selectedLength));
    setLineLabel(String.valueOf(lineNum));
    setColumnLabel(String.valueOf(columnNum));
    setLengthLabel(String.valueOf(textLength));
  }


  /**
   * Sets text to label <code>length</code>
   * @param value to set
   */
  private void setLengthLabel(String value){
    length.setText(flp.getString("length") + " : " + value);
  }

  /**
   * Sets text to label <code>line</code>
   * @param value to set
   */
  private void setLineLabel(String value){
    line.setText(flp.getString("ln") + " : "+ value);
  }

  /**
   * Sets text to label <code>column</code>
   * @param value to set
   */
  private void setColumnLabel(String value){
    column.setText(flp.getString("col") + " : " + value);
  }

  /**
   * Sets text to label <code>selected</code>
   * @param value to set
   */
  private void setSelectedLabel(String value){
    selected.setText(flp.getString("sel") +" : " + value);
  }

  /**
   * Sort selected line in text document by <code>SortType</code>
   * @param sortType enum that represent how to sort
   */
  private void sortSelectedText(SortType sortType) {
    JTextComponent textComponent = mdm.getCurrentDocument().getTextComponent();
    Document document = textComponent.getDocument();
    Caret caret = mdm.getCurrentDocument().getTextComponent().getCaret();
    Element root =  document.getDefaultRootElement();

    int minPositionRow = Math.min(caret.getDot(),caret.getMark());
    int maxPositionRow = Math.max(caret.getDot(),caret.getMark());
    int minRow = root.getElementIndex(minPositionRow);
    int maxRow = root.getElementIndex(maxPositionRow);

    List<String> lines = new ArrayList<>();

    for(int i = minRow; i <= maxRow; i++){
      try{
        Element element = root.getElement(i);
        lines.add(document.getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset()));
      }catch (BadLocationException ignored){}
    }

    Locale locale = new Locale(flp.getCurrentLanguage());
    Collator collator = Collator.getInstance(locale);
    List<CollationKey> keys = new ArrayList<>();

    for (String s : lines) keys.add(collator.getCollationKey(s));

    Comparator<CollationKey> collationKeyComparator = sortType == SortType.ASCENDING ? Comparator.naturalOrder() : Comparator.reverseOrder();

    List<CollationKey> sortedKeys = keys.stream().sorted(collationKeyComparator).toList();

    int counter = 0;
    for(int i = minRow; i <= maxRow; i++){
      try{
        Element element = root.getElement(i);
        int startOffset = element.getStartOffset();
        int endOffset = element.getEndOffset() == document.getEndPosition().getOffset() ? element.getEndOffset() - 1: element.getEndOffset();

        document.remove(startOffset, endOffset - startOffset);
        document.insertString(startOffset, sortedKeys.get(counter++).getSourceString(),element.getAttributes());
      }catch (BadLocationException ignored){}
    }
  }

  /**
   * Enum for sorting
   *
   * @author Dean Trkulja
   * @version 1.0
   */
  private enum SortType {
    ASCENDING,
    DESCENDING
  }

  /**
   * Entry point that starts our swing application
   *
   * @param args an array of command-line arguments.
   */

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
  }



}
