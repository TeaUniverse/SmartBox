package mvc;

import java.io.Serializable;

public class Model extends Publisher implements Serializable {

    // unsavedChanges flag indicates whether changes to the model have been saved to a file.
    private boolean unsavedChanges = false;
    // fileName = null: model has not been saved to a file
    private String fileName = null;

    public void changed(){
        unsavedChanges = true;
        notifyObservers();
    }

    public void setUnsavedChanges(boolean b) {
        unsavedChanges = b;
    }

    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fName) {
        fileName = fName;
    }
}
