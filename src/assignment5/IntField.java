package assignment5;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// helper text field subclass which restricts text input to a given range of natural int numbers
// and exposes the current numeric int value of the edit box as a value property.
class IntField extends TextField {
  final private IntegerProperty value;

  // expose an integer value property for the text field.
  public int  getValue()                 { return value.getValue(); }
  public void setValue(int newValue)     { value.setValue(newValue); }
  public IntegerProperty valueProperty() { return value; }
  
  IntField(int initialValue) {

    // initialize the field values.
    value = new SimpleIntegerProperty(initialValue);
    setText(initialValue + "");

    final IntField intField = this;

    // make sure the value property is clamped to the required range
    // and update the field's text to be in sync with the value.
    value.addListener(new ChangeListener<Number>() {
      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (newValue == null) {
          intField.setText("");
        } else {
          if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
            // no action required, text property is already blank, we don't need to set it to 0.
          } else {
            intField.setText(newValue.toString());
          }
        }
      }
    });
    
    // restrict key input to numerals.
    this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent keyEvent) {
        if (!"0123456789".contains(keyEvent.getCharacter())) {
          keyEvent.consume();
        }
      }
    });

  }
}
