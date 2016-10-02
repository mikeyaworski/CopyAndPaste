# CopyAndPaste

## Overview
This is a Java object that is used in Android applications to copy and paste data to/from the Clipboard. It supports all versions of Android.

## Usage

Just download this class and paste it directly into your Android project (in the src directory). Then instantiate the class and start calling the methods.

## Methods

- `CopyAndPaste(Context context)` Constructor where the parameter is the Context of the Activity this object is being instantiated in.
- `void copy(String text)` Copies the String parameter to the Clipboard.
- `String paste()` Returns the String representation of the most recent Clip on the Clipboard.
- `void paste(EditText editText)` Pastes the String representation of the most recent Clip on the Clipboard directly into the current cursor location in the EditText (parameter).
- `void pasteOption(EditText editText)` Gives the option (via AlertDialog) to paste the String representation of the most recent Clip on the Clipboard into the current cursor location in the EditText (parameter).
- `void copyOrPaste(EditText editText)` Gives the option (via AlertDialog) to either copy to the Clipboard, or paste from it. If the EditText is empty, there is only the paste option.

## Examples

### Concise Examples
```java
CopyAndPaste copyAndPaste = new CopyAndPaste(YourActivity.this);
EditText myEditText = (EditText)findViewById(R.id.myEditText);

copyAndPaste.copy("Hello Clipboard!");
String pastedText = copyAndPaste.paste();
copyAndPaste.paste(myEditText);
copyAndPaste.pasteOption(myEditText);
copyAndPaste.copyOrPaste(myEditText);
```

### Verbose Example
 
```java
public class YourActivity extends Activity {

    // declare it at the class level, but don't initialize it
    // because the instance of CopyAndPaste requires the activity Context
    private CopyAndPaste copyAndPaste;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yourlayout);
                
        // after you have set your layout, initialize the instance of CopyPaste
        // with the activity Context
        copyAndPaste = new CopyAndPaste(YourActivity.this);

        // ...
    }
    // ...
}
```
