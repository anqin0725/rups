package com.itextpdf.rups.view.contextmenu;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

/**
 * Convenience class/factory to get a context menu for a text pane. This context menu contains two actions as of yet:
 * - copy
 * - select all
 *
 * @author Michael Demey
 */
public class StreamPanelContextMenu {

    final static String COPY = "Copy";
    final static String SELECTALL = "Select All";

    /**
     * Creates a context menu (right click menu) with two actions:
     * - copy
     * - select all
     *
     * Copy copies the selected text or when no text is selected, it copies the entire text.
     *
     * @param textPane
     * @return
     */
    public static JPopupMenu getContextMenu(final JTextPane textPane) {
        final JPopupMenu menu = new JPopupMenu();
        final JMenuItem copyItem = new JMenuItem();
        copyItem.setAction(new CopyToClipboardAction(COPY, textPane));
        copyItem.setText(COPY);

        final JMenuItem selectAllItem = new JMenuItem(SELECTALL);
        selectAllItem.setAction(textPane.getActionMap().get(DefaultEditorKit.selectAllAction));
        selectAllItem.setText(SELECTALL);

        JMenuItem saveToFile = new JMenuItem();
        saveToFile.setText("Save to File");
        saveToFile.setAction(new SaveToFileJTextPaneAction("Save to File", textPane));

        menu.add(copyItem);
        menu.add(saveToFile);
        menu.add(new JSeparator());
        menu.add(selectAllItem);

        return menu;
    }
}
