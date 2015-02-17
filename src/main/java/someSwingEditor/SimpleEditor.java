package someSwingEditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public class SimpleEditor extends JFrame {

  private Action openAction = new OpenAction();
  private Action saveAction = new SaveAction();
  private Action newFileAction = new NewFileAction();
  private Action displayConsoleAction = new DisplayConsoleAction();
  private Action lemmatizeAction = new LemmatizeAction();
  private Action validateAction = new ValidateAction();
  private Action modelViewAction = new ModelViewAction();
  private Action unicodeAction = new UnicodeAction();
  private Action helpAction = new HelpAction();
  

  private JTextComponent textComp;
  private Hashtable actionHash = new Hashtable();

  public static void main(String[] args) {
    SimpleEditor editor = new SimpleEditor();
    editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    editor.setVisible(true);
  } 

  // Create an editor.
  public SimpleEditor() {
    super("Nammu");
    textComp = createTextComponent();
    makeActionsPretty();

    Container content = getContentPane();
    content.add(textComp, BorderLayout.CENTER);
    content.add(createToolBar(), BorderLayout.NORTH);
    setJMenuBar(createMenuBar());
    setSize(576, 600);
    
  }

  // Create the JTextComponent subclass.
  protected JTextComponent createTextComponent() {
    JTextArea ta = new JTextArea();
    ta.setLineWrap(true);
    return ta;
  }

  // Add icons and friendly names to actions we care about.
  protected void makeActionsPretty() {
    Action a;
    a = textComp.getActionMap().get(DefaultEditorKit.cutAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/cut-icon-small.png"));
    a.putValue(Action.NAME, "Cut");

    a = textComp.getActionMap().get(DefaultEditorKit.copyAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/copy-icon-small.png"));
    a.putValue(Action.NAME, "Copy");

    a = textComp.getActionMap().get(DefaultEditorKit.pasteAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/paste-icon-small.png"));
    a.putValue(Action.NAME, "Paste");

    a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);
    a.putValue(Action.NAME, "Select All");
  }

  // Create a simple JToolBar with some buttons.
  protected JToolBar createToolBar() {
    JToolBar bar = new JToolBar();

    // Add simple actions for creating, opening & saving.
    bar.add(getNewFileAction()).setText("");
    bar.add(getOpenAction()).setText("");
    bar.add(getSaveAction()).setText("");
    bar.addSeparator();

    // Add cut/copy/paste buttons.
    bar.add(textComp.getActionMap().get(DefaultEditorKit.cutAction)).setText("");
    bar.add(textComp.getActionMap().get(
              DefaultEditorKit.copyAction)).setText("");
    bar.add(textComp.getActionMap().get(
              DefaultEditorKit.pasteAction)).setText("");
    
    bar.addSeparator();
    bar.add(getValidateAction()).setText("");
    bar.add(getLemmatizeAction()).setText("");
    bar.add(getDisplayConsoleAction()).setText("");
    bar.add(getModelViewAction()).setText("");
    bar.add(getUnicodeAction()).setText("");
    
    
    bar.addSeparator();
    bar.add(getHelpAction()).setText("");
    
    return bar;
  }

  // Create a JMenuBar with file & edit menus.
  protected JMenuBar createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu atf = new JMenu("ATF");
    JMenu help = new JMenu("Help");
    menubar.add(file);
    menubar.add(edit);
    menubar.add(atf);
    menubar.add(help);

    file.add(getNewFileAction());
    file.add(getOpenAction());
    file.add(getSaveAction());
    file.add(new ExitAction());
    edit.add(textComp.getActionMap().get(DefaultEditorKit.cutAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.copyAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.selectAllAction));
    atf.add(getValidateAction());
    atf.add(getLemmatizeAction());
    atf.add(getModelViewAction());
    atf.add(getDisplayConsoleAction());
    atf.add(getUnicodeAction());
    help.add(getHelpAction());
    return menubar;
  }

  // Subclass can override to use a different open action.
  protected Action getOpenAction() { return openAction; }

  // Subclass can override to use a different save action.
  protected Action getSaveAction() { return saveAction; }
  
  // Subclass can override to use a different save action.
  protected Action getNewFileAction() { return newFileAction; }
  
//Subclass can override to use a different save action.
 protected Action getValidateAction() { return validateAction; }
 
//Subclass can override to use a different save action.
protected Action getLemmatizeAction() { return lemmatizeAction; }

//Subclass can override to use a different save action.
protected Action getModelViewAction() { return modelViewAction; }

//Subclass can override to use a different save action.
protected Action getDisplayConsoleAction() { return displayConsoleAction; }

//Subclass can override to use a different save action.
protected Action getUnicodeAction() { return unicodeAction; }

//Subclass can override to use a different save action.
protected Action getHelpAction() { return helpAction; }
  
  

  protected JTextComponent getTextComponent() { return textComp; }

  // ********** ACTION INNER CLASSES ********** //

  // A very simple exit action
  public class ExitAction extends AbstractAction {
    public ExitAction() { super("Exit"); }
    public void actionPerformed(ActionEvent ev) { System.exit(0); }
  }

  // An action that opens an existing file
  class OpenAction extends AbstractAction {
    public OpenAction() { 
      super("Open", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/open-file-icon-small.png")); 
    }

    // Query user for a filename and attempt to open and read the file into the
    // text component.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showOpenDialog(SimpleEditor.this) !=
          JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileReader reader = null;
      try {
        reader = new FileReader(file);
        textComp.read(reader, null);
      }
      catch (IOException ex) {
        JOptionPane.showMessageDialog(SimpleEditor.this,
        "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
      finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException x) {}
        }
      }
    }
  }
  
//An action to create a new file
 class NewFileAction extends AbstractAction {
   public NewFileAction() { 
     super("New", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/new-file-icon-small.png")); 
   }

   // Query user for a filename and attempt to open and read the file into the
   // text component.
   public void actionPerformed(ActionEvent ev) {
	   textComp.setText("");
   }
 }

 // An action that saves the document to a file
 class SaveAction extends AbstractAction {
   public SaveAction() {
     super("Save", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/Save-icon-small.png"));
   }

   // Query user for a filename and attempt to open and write the text
   // components content to the file.
   public void actionPerformed(ActionEvent ev) {
     JFileChooser chooser = new JFileChooser();
     if (chooser.showSaveDialog(SimpleEditor.this) !=
         JFileChooser.APPROVE_OPTION)
       return;
     File file = chooser.getSelectedFile();
     if (file == null)
       return;

     FileWriter writer = null;
     try {
       writer = new FileWriter(file);
       textComp.write(writer);
     }
     catch (IOException ex) {
       JOptionPane.showMessageDialog(SimpleEditor.this,
       "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
     }
     finally {
       if (writer != null) {
         try {
           writer.close();
         } catch (IOException x) {}
       }
     }
   }
 }
 
 // An action that changes view to model view
 class ModelViewAction extends AbstractAction {
   public ModelViewAction() {
     super("ModelView", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/modelview-icon-small.png"));
   }

     public void actionPerformed(ActionEvent ev) {
   }
  
 }
 
 // An action that displays/hides console panel
 class DisplayConsoleAction extends AbstractAction {
   public DisplayConsoleAction() {
     super("DisplayConsole", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/terminal-icon-small.png"));
   }
  public void actionPerformed(ActionEvent ev) {
   }
  
 }
 
 // An action that lemmatizes ATF
 class LemmatizeAction extends AbstractAction {
   public LemmatizeAction() {
     super("Lemmatize", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/lemmatize-icon-small.png"));
   }
  public void actionPerformed(ActionEvent ev) {
   }
  
 } 
 
 // An action that validates ATF
 class ValidateAction extends AbstractAction {
   public ValidateAction() {
     super("Validate", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/validate-icon-small.png"));
   }
  public void actionPerformed(ActionEvent ev) {
   }
  
 } 
 
//An action that changes keyboard to unicode/locale
class UnicodeAction extends AbstractAction {
public UnicodeAction() {
  super("Help", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/unicode-icon-small.png"));
}
public void actionPerformed(ActionEvent ev) {
}

} 

//An action that displays help
class HelpAction extends AbstractAction {
 public HelpAction() {
   super("Help", new ImageIcon("/Users/raquelalegre/workspace/ORACC/icons/help-icon-small.png"));
 }
public void actionPerformed(ActionEvent ev) {
 }

} 


 
}