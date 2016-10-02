// you have to change this to your package name!
package yourPackageName;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class uses deprecated classes for old APIs
 * and the newly introduced classes for the APIs post Honeycomb.
 *
 * @author Michael Yaworski of http://mikeyaworski.com
 * @version February 27, 2014
 */
public class CopyAndPaste {

    /**
     * The Context of the activity that this object is instantiated from
     */
    private Context context = null;

    /**
     * Constructor method.
     *
     * @param  context  the Context of the activity that this object is instantiated from
     */
    public CopyAndPaste(Context context) {
        this.context = context;
    }

    /**
     * Copies the String parameter to the Clipboard.
     * Also used as a helper method for copyOrPaste(EditText).
     *
     * @param  text  the text to be copied to the Clipboard
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void copy(final String text) {
        try {
            if (text.length() > 0) {

                int sdk = android.os.Build.VERSION.SDK_INT;

                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {

                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                    if (clipboard.hasText()) {
                        if (!(clipboard.getText().toString().equals(text))) { // if the last Clip is not the same as the copying text
                            clipboard.setText(text);
                            toast("Copied to clipboard.");
                        } else {
                            toast("Already on clipboard.");
                        }
                    }
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                    android.content.ClipData clip = android.content.ClipData.newPlainText("newDataSet", text); // creates a clip to add to the Clipboard
                    android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0); // item is the most recent Clip from the Clipboard
                    String clipText = item.coerceToText(context).toString(); // gets the Clipboard as text, no matter what type the item is.

                    if (!(clipText.equals(text))) { // if the last Clip is not the same as the copying text
                        clipboard.setPrimaryClip(clip);
                        toast("Copied to clipboard.");
                    } else {
                        toast("Already on clipboard.");
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error!");
        }
    }

    /**
     * Returns the String representation of the most recent Clip on the Clipboard.
     * Also used as a helper method for pasteOption(EditText)
     *
     * @return the String representation of the most recent Clip on the Clipboard
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public String paste() {
        try {
            String clipText = "";

            int sdk = android.os.Build.VERSION.SDK_INT;

            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboard.hasText()) {
                    clipText = clipboard.getText().toString();
                }

            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0); // item is the most recent Clip from the Clipboard
                clipText = item.getText().toString(); // get most recent text Clip
            }
            return clipText;

        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error!");
            return null;
        }
    }

    /**
     * Pastes the String representation of the most recent Clip on the Clipboard
     * directly into the current cursor location in the EditText (parameter).
     * Also used as a helper method for copyOrPaste(EditText).
     *
     * @param  editText  the EditText that the Clipboard text should be pasted to
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void paste(final EditText editText) {
        String clipText = paste();

        if (clipText != null) {
            final int sel = editText.getSelectionStart();
            final String originalText = editText.getText().toString();
            // insert the Clipboard text where the cursor is already and keep the cursor there
            editText.setText(originalText.substring(0, sel) + clipText + originalText.substring(sel, originalText.length()));
            editText.setSelection(sel);
        }
    }

    /**
     * Gives the option (via AlertDialog) to paste the String representation of the most recent Clip on the Clipboard
     * into the current cursor location in the EditText (parameter).
     * Uses helper method paste(EditText).
     *
     * @param  editText  the EditText that the Clipboard text should be pasted to
     */
    public void pasteOption(final EditText editText) {
        try {
            new AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setNeutralButton("Paste", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            paste(editText);
                        }
                    })
                    .show();
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error!");
        }
    }

    /**
     * Gives the option (via AlertDialog) to either copy to the Clipboard, or paste from it.
     * If the EditText is empty, there is only the paste option.
     * Uses helper methods copy(String) and paste(EditText).
     *
     * @param  editText  the EditText to copy the text from or where the Clipboard text should be pasted to
     */
    public void copyOrPaste(final EditText editText) {
        try {
            final String text = editText.getText().toString();

            if (text.length() > 0) {
                new AlertDialog.Builder(context)
                        .setCancelable(true)
                        .setNegativeButton("Copy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                copy(text);
                            }
                        })
                        .setPositiveButton("Paste", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                paste(editText);
                            }
                        })
                        .show();
            } else {
                pasteOption(editText);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error!");
        }
    }

    /**
     * Toasts the text from the parameter to the Context defined in the constructor.
     *
     * @param  text  the text that should be shown in the Toast
     */
    public void toast(String text) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
