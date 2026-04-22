import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.undo.*;
import java.awt.print.*;

public class NotepadApp implements ActionListener {

    JFrame f;
    JTabbedPane tp;
    JLabel statusBar;

    JMenuItem newTab, newWindow, open, save, saveAs, pageSetup, print, exit;
    JMenuItem undo, redo, cut, copy, paste, delete, find, findNext, findPrevious, replace, goTo, selectAll, timeDate;
    JMenuItem wordWrap, font;
    JMenuItem zoomIn, zoomOut, zoomRestore;
    JMenuItem about;

    HashMap<JTextArea, UndoManager> undoMap = new HashMap<>();
    HashMap<JTextArea, File> fileMap = new HashMap<>();
    HashMap<JTextArea, Boolean> modifiedMap = new HashMap<>();
    HashMap<JTextArea, String> lastSearchMap = new HashMap<>();
    HashMap<JTextArea, Integer> lastSearchPosMap = new HashMap<>();
    HashMap<JTextArea, Integer> zoomLevelMap = new HashMap<>();

    int untitledCount = 1;
    JFrame findReplaceDialog;
    JTextField findField, replaceField;
    JTextArea currentFindReplaceTA;

    NotepadApp() {
        f = new JFrame("Notepad");

        // ===== MENU BAR =====
        JMenuBar mb = new JMenuBar();

        // File Menu
        JMenu file = new JMenu("File");
        newTab = new JMenuItem("New Tab");
        newWindow = new JMenuItem("New Window");
        open = new JMenuItem("Open...");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save As...");
        pageSetup = new JMenuItem("Page Setup...");
        print = new JMenuItem("Print...");
        exit = new JMenuItem("Exit");

        // Edit Menu
        JMenu edit = new JMenu("Edit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        delete = new JMenuItem("Delete");
        find = new JMenuItem("Find...");
        findNext = new JMenuItem("Find Next");
        findPrevious = new JMenuItem("Find Previous");
        replace = new JMenuItem("Replace...");
        goTo = new JMenuItem("Go To...");
        selectAll = new JMenuItem("Select All");
        timeDate = new JMenuItem("Time/Date");

        // Format Menu
        JMenu format = new JMenu("Format");
        wordWrap = new JCheckBoxMenuItem("Word Wrap");
        font = new JMenuItem("Font...");

        // View Menu
        JMenu view = new JMenu("View");
        JMenu zoom = new JMenu("Zoom");
        zoomIn = new JMenuItem("Zoom In");
        zoomOut = new JMenuItem("Zoom Out");
        zoomRestore = new JMenuItem("Restore Default Zoom");

        // Help Menu
        JMenu help = new JMenu("Help");
        about = new JMenuItem("About Notepad");

        // Add zoom items to zoom submenu
        zoom.add(zoomIn);
        zoom.add(zoomOut);
        zoom.add(zoomRestore);
        view.add(zoom);

        // Add all items to file menu
        file.add(newTab);
        file.add(newWindow);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(pageSetup);
        file.add(print);
        file.addSeparator();
        file.add(exit);

        // Add all items to edit menu
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.addSeparator();
        edit.add(find);
        edit.add(findNext);
        edit.add(findPrevious);
        edit.add(replace);
        edit.add(goTo);
        edit.addSeparator();
        edit.add(selectAll);
        edit.addSeparator();
        edit.add(timeDate);

        // Add to format menu
        format.add(wordWrap);
        format.add(font);

        // Help menu
        help.add(about);

        // Add all menus to bar
        mb.add(file);
        mb.add(edit);
        mb.add(format);
        mb.add(view);
        mb.add(help);
        f.setJMenuBar(mb);

        // Set accelerators (shortcuts)
        // File shortcuts
        newTab.setAccelerator(KeyStroke.getKeyStroke("control N"));
        newWindow.setAccelerator(KeyStroke.getKeyStroke("control shift N"));
        open.setAccelerator(KeyStroke.getKeyStroke("control O"));
        save.setAccelerator(KeyStroke.getKeyStroke("control S"));
        print.setAccelerator(KeyStroke.getKeyStroke("control P"));

        // Edit shortcuts
        undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        cut.setAccelerator(KeyStroke.getKeyStroke("control X"));
        copy.setAccelerator(KeyStroke.getKeyStroke("control C"));
        paste.setAccelerator(KeyStroke.getKeyStroke("control V"));
        find.setAccelerator(KeyStroke.getKeyStroke("control F"));
        findNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
        findPrevious.setAccelerator(KeyStroke.getKeyStroke("shift F3"));
        replace.setAccelerator(KeyStroke.getKeyStroke("control H"));
        goTo.setAccelerator(KeyStroke.getKeyStroke("control G"));
        selectAll.setAccelerator(KeyStroke.getKeyStroke("control A"));
        timeDate.setAccelerator(KeyStroke.getKeyStroke("F5"));

        // Zoom shortcuts
        zoomIn.setAccelerator(KeyStroke.getKeyStroke("control EQUALS"));
        zoomOut.setAccelerator(KeyStroke.getKeyStroke("control MINUS"));
        zoomRestore.setAccelerator(KeyStroke.getKeyStroke("control 0"));

        // Add action listeners
        JMenuItem[] items = {newTab, newWindow, open, save, saveAs, pageSetup, print, exit,
                             undo, redo, cut, copy, paste, delete, find, findNext, findPrevious,
                             replace, goTo, selectAll, timeDate, font, zoomIn, zoomOut, zoomRestore, about};
        for (JMenuItem i : items) i.addActionListener(this);
        wordWrap.addActionListener(this);

        // ===== TABS =====
        tp = new JTabbedPane();
        f.add(tp, BorderLayout.CENTER);

        // ===== STATUS BAR =====
        statusBar = new JLabel("  Line 1, Col 1");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        f.add(statusBar, BorderLayout.SOUTH);

        // Add first tab
        addNewTab();

        // Add caret listener to update status bar
        tp.addChangeListener(e -> {
            JTextArea ta = getCurrentTextArea();
            if (ta != null) {
                updateStatusBar(ta);
                ta.addCaretListener(ce -> updateStatusBar(ta));
            }
        });

        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void updateStatusBar(JTextArea ta) {
        try {
            int caretPos = ta.getCaretPosition();
            int line = ta.getLineOfOffset(caretPos) + 1;
            int column = caretPos - ta.getLineStartOffset(line - 1) + 1;
            statusBar.setText("  Line " + line + ", Col " + column + "  ");
        } catch (Exception e) {
            statusBar.setText("  Line 1, Col 1  ");
        }
    }

    private JTextArea getCurrentTextArea() {
        Component comp = tp.getSelectedComponent();
        if (comp instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) comp;
            JViewport vp = sp.getViewport();
            if (vp.getView() instanceof JTextArea) {
                return (JTextArea) vp.getView();
            }
        }
        return null;
    }

    private void addNewTab() {
        JTextArea ta = new JTextArea();
        ta.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(ta);

        // Initialize zoom level
        zoomLevelMap.put(ta, 14);

        // Undo manager
        UndoManager um = new UndoManager();
        ta.getDocument().addUndoableEditListener(e -> um.addEdit(e.getEdit()));
        undoMap.put(ta, um);

        modifiedMap.put(ta, false);
        lastSearchMap.put(ta, "");
        lastSearchPosMap.put(ta, 0);

        // Document listener for modified mark
        ta.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { markModified(ta); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { markModified(ta); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { markModified(ta); }
        });

        // Caret listener for status bar
        ta.addCaretListener(e -> updateStatusBar(ta));

        String title = "Untitled" + untitledCount++;
        tp.add(title, sp);
        tp.setSelectedComponent(sp);
    }

    private void markModified(JTextArea ta) {
        if (!modifiedMap.get(ta)) {
            modifiedMap.put(ta, true);
            int i = tp.indexOfComponent((JScrollPane) ta.getParent().getParent());
            if (i != -1) {
                String title = tp.getTitleAt(i);
                if (!title.endsWith("*")) {
                    tp.setTitleAt(i, title + "*");
                }
            }
        }
    }

    private void clearModified(JTextArea ta, File file) {
        modifiedMap.put(ta, false);
        int i = tp.indexOfComponent((JScrollPane) ta.getParent().getParent());
        if (i != -1) {
            String title = file != null ? file.getName() : tp.getTitleAt(i).replace("*", "");
            tp.setTitleAt(i, title);
        }
    }

    private void openFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            JTextArea ta = getCurrentTextArea();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                ta.setText("");
                String line;
                while ((line = br.readLine()) != null) {
                    ta.append(line + "\n");
                }
                fileMap.put(ta, file);
                clearModified(ta, file);
                int i = tp.indexOfComponent((JScrollPane) ta.getParent().getParent());
                if (i != -1) tp.setTitleAt(i, file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Error opening file: " + ex.getMessage());
            }
        }
    }

    private void saveFile(JTextArea ta, File file) {
        try (FileWriter fw = new FileWriter(file)) {
            ta.write(fw);
            fileMap.put(ta, file);
            clearModified(ta, file);
            int i = tp.indexOfComponent((JScrollPane) ta.getParent().getParent());
            if (i != -1) tp.setTitleAt(i, file.getName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Error saving file: " + ex.getMessage());
        }
    }

    private void pageSetup() {
        JOptionPane.showMessageDialog(f, 
            "Page Setup Dialog\n\n" +
            "Paper Size: A4 / Letter\n" +
            "Orientation: Portrait / Landscape\n" +
            "Margins: Left, Right, Top, Bottom\n" +
            "Header: &f (filename)\n" +
            "Footer: &p (page number)\n\n" +
            "(Full implementation would use Java Print API)",
            "Page Setup", JOptionPane.INFORMATION_MESSAGE);
    }

    private void printDocument() {
        JTextArea ta = getCurrentTextArea();
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) return NO_SUCH_PAGE;
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());
                ta.print(g2d);
                return PAGE_EXISTS;
            }
        });
        if (job.printDialog()) {
            try {
                job.print();
                JOptionPane.showMessageDialog(f, "Printing...", "Print", JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(f, "Print error: " + ex.getMessage());
            }
        }
    }

    private void showFindReplaceDialog(boolean isReplace) {
        JTextArea ta = getCurrentTextArea();
        currentFindReplaceTA = ta;

        JDialog dialog = new JDialog(f, isReplace ? "Replace" : "Find", true);
        dialog.setSize(400, isReplace ? 150 : 120);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Find label and field
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Find what:"), gbc);
        gbc.gridx = 1;
        findField = new JTextField(20);
        dialog.add(findField, gbc);

        if (isReplace) {
            gbc.gridx = 0; gbc.gridy = 1;
            dialog.add(new JLabel("Replace with:"), gbc);
            gbc.gridx = 1;
            replaceField = new JTextField(20);
            dialog.add(replaceField, gbc);
        }

        JPanel buttonPanel = new JPanel();
        JButton findNextBtn = new JButton("Find Next");
        JButton cancelBtn = new JButton("Cancel");
        buttonPanel.add(findNextBtn);
        buttonPanel.add(cancelBtn);

        if (isReplace) {
            JButton replaceBtn = new JButton("Replace");
            JButton replaceAllBtn = new JButton("Replace All");
            buttonPanel.add(replaceBtn);
            buttonPanel.add(replaceAllBtn);
            replaceBtn.addActionListener(e -> {
                String search = findField.getText();
                String replace = replaceField.getText();
                if (!search.isEmpty()) {
                    String content = ta.getText();
                    if (ta.getSelectedText() != null && ta.getSelectedText().equals(search)) {
                        ta.replaceSelection(replace);
                    } else {
                        int pos = content.indexOf(search, ta.getCaretPosition());
                        if (pos != -1) {
                            ta.setSelectionStart(pos);
                            ta.setSelectionEnd(pos + search.length());
                            ta.replaceSelection(replace);
                        }
                    }
                }
                dialog.dispose();
            });
            replaceAllBtn.addActionListener(e -> {
                String search = findField.getText();
                String replace = replaceField.getText();
                if (!search.isEmpty()) {
                    String content = ta.getText();
                    String newContent = content.replace(search, replace);
                    ta.setText(newContent);
                }
                dialog.dispose();
            });
        }

        findNextBtn.addActionListener(e -> {
            String search = findField.getText();
            if (!search.isEmpty()) {
                String content = ta.getText();
                int pos = content.indexOf(search, ta.getCaretPosition());
                if (pos != -1) {
                    ta.setSelectionStart(pos);
                    ta.setSelectionEnd(pos + search.length());
                    ta.setCaretPosition(pos + search.length());
                } else {
                    JOptionPane.showMessageDialog(dialog, "Text not found");
                }
            }
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        gbc.gridx = 0; gbc.gridy = isReplace ? 2 : 1;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.setLocationRelativeTo(f);
        dialog.setVisible(true);
    }

    private void goToLine() {
        JTextArea ta = getCurrentTextArea();
        String input = JOptionPane.showInputDialog(f, "Line number:");
        if (input != null) {
            try {
                int line = Integer.parseInt(input) - 1;
                int pos = ta.getLineStartOffset(line);
                ta.setCaretPosition(pos);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Invalid line number");
            }
        }
    }

    private void insertTimeDate() {
        JTextArea ta = getCurrentTextArea();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        ta.insert(sdf.format(new Date()), ta.getCaretPosition());
    }

    private void changeFont() {
        JTextArea ta = getCurrentTextArea();
        Font currentFont = ta.getFont();
        JComboBox<String> fonts = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fonts.setSelectedItem(currentFont.getFamily());
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(currentFont.getSize(), 8, 72, 1));
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Font:"));
        panel.add(fonts);
        panel.add(new JLabel("Size:"));
        panel.add(sizeSpinner);
        
        int result = JOptionPane.showConfirmDialog(f, panel, "Font", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            ta.setFont(new Font((String) fonts.getSelectedItem(), Font.PLAIN, (int) sizeSpinner.getValue()));
        }
    }

    private void zoom(int delta) {
        JTextArea ta = getCurrentTextArea();
        int currentSize = zoomLevelMap.get(ta);
        int newSize = currentSize + delta;
        if (newSize >= 8 && newSize <= 72) {
            Font font = ta.getFont();
            ta.setFont(font.deriveFont((float) newSize));
            zoomLevelMap.put(ta, newSize);
        }
    }

    public void actionPerformed(ActionEvent e) {
        JTextArea ta = getCurrentTextArea();
        if (ta == null) return;

        UndoManager um = undoMap.get(ta);

        // File menu actions
        if (e.getSource() == newTab) addNewTab();
        if (e.getSource() == newWindow) new NotepadApp();
        if (e.getSource() == open) openFile();
        if (e.getSource() == save) {
            File file = fileMap.get(ta);
            if (file == null) saveAs.doClick();
            else saveFile(ta, file);
        }
        if (e.getSource() == saveAs) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(f) == JFileChooser.APPROVE_OPTION) {
                saveFile(ta, fc.getSelectedFile());
            }
        }
        if (e.getSource() == pageSetup) pageSetup();
        if (e.getSource() == print) printDocument();
        if (e.getSource() == exit) System.exit(0);

        // Edit menu actions
        if (e.getSource() == undo && um.canUndo()) um.undo();
        if (e.getSource() == redo && um.canRedo()) um.redo();
        if (e.getSource() == cut) ta.cut();
        if (e.getSource() == copy) ta.copy();
        if (e.getSource() == paste) ta.paste();
        if (e.getSource() == delete) ta.replaceSelection("");
        if (e.getSource() == find) showFindReplaceDialog(false);
        if (e.getSource() == findNext) {
            String lastSearch = lastSearchMap.get(ta);
            if (lastSearch.isEmpty()) find.doClick();
            else {
                String content = ta.getText();
                int pos = content.indexOf(lastSearch, ta.getCaretPosition());
                if (pos == -1) pos = content.indexOf(lastSearch);
                if (pos != -1) {
                    ta.setSelectionStart(pos);
                    ta.setSelectionEnd(pos + lastSearch.length());
                    ta.setCaretPosition(pos + lastSearch.length());
                }
            }
        }
        if (e.getSource() == findPrevious) {
            String lastSearch = lastSearchMap.get(ta);
            if (lastSearch.isEmpty()) find.doClick();
            else {
                String content = ta.getText();
                int pos = content.lastIndexOf(lastSearch, ta.getCaretPosition() - 1);
                if (pos == -1) pos = content.lastIndexOf(lastSearch);
                if (pos != -1) {
                    ta.setSelectionStart(pos);
                    ta.setSelectionEnd(pos + lastSearch.length());
                    ta.setCaretPosition(pos);
                }
            }
        }
        if (e.getSource() == replace) showFindReplaceDialog(true);
        if (e.getSource() == goTo) goToLine();
        if (e.getSource() == selectAll) ta.selectAll();
        if (e.getSource() == timeDate) insertTimeDate();

        // Format menu actions
        if (e.getSource() == wordWrap) ta.setLineWrap(wordWrap.isSelected());
        if (e.getSource() == font) changeFont();

        // View menu actions
        if (e.getSource() == zoomIn) zoom(2);
        if (e.getSource() == zoomOut) zoom(-2);
        if (e.getSource() == zoomRestore) {
            Font font = ta.getFont();
            ta.setFont(font.deriveFont(14f));
            zoomLevelMap.put(ta, 14);
        }

        // Help menu
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(f, 
                "Notepad\n\nVersion 2.0\n\n" +
                "Features:\n" +
                "- Multiple tabs & windows\n" +
                "- Find & Replace (Ctrl+H)\n" +
                "- Go To Line (Ctrl+G)\n" +
                "- Time/Date (F5)\n" +
                "- Page Setup & Print\n" +
                "- Zoom (Ctrl+Plus/Ctrl+Minus)\n" +
                "- Font customization\n" +
                "- Status bar with line/column\n" +
                "- Word wrap\n\n" +
                "© 2026",
                "About Notepad", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new NotepadApp();
    }
}