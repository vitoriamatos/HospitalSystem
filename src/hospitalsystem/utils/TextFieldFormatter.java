package academiccontrolsystem.utils;


import javafx.scene.control.TextField;

import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import java.text.ParseException;

public class TextFieldFormatter extends PlainDocument {

	private static final long serialVersionUID = -9114056903551024225L;
	
	private final MaskFormatter maskFormatter;
    private TextField textField;
    private String validCharacters;
    private String mask;



    public TextFieldFormatter() {
        maskFormatter = new MaskFormatter();
    }



    public void formatter() {
        formatter(this.textField, this.validCharacters, this.mask);
    }

    public void formatter(TextField textField, String validCharacters, String mask) {
        try {
            maskFormatter.setMask(mask);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        maskFormatter.setValidCharacters(validCharacters);
        maskFormatter.setValueContainsLiteralCharacters(false);
        String text = textField.getText().replaceAll("[\\W]", "");

        boolean repeat = true;
        while (repeat) {

            char lastCharacter;
            if (text.equals("")) {
                break;
            } else {
                lastCharacter = text.charAt(text.length() - 1);
            }

            try {
                text = maskFormatter.valueToString(text);
                repeat = false;
            } catch (ParseException ex) {
                text = text.replace(lastCharacter + "", "");
                repeat = true;
            }

        }

        textField.setText(text);

        if (!text.equals("")) {
            for (int i = 0; textField.getText().charAt(i) != ' ' && i < textField.getLength() - 1; i++) {
                textField.forward();
            }
        }
    }


    // Getters and setters

    public String getMask() {
        return mask;
    }

    public TextField getTextField() {
        return textField;
    }

    public String getValidCharacters() {
        return validCharacters;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public void setValidCharacters(String validCharacters) {
        this.validCharacters = validCharacters;
    }

}





