package ru.dyakun.citnis.model.selection;

import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.Validator;

public class Validators {

    private Validators() {
        throw new AssertionError();
    }

    public static Validator<String> onlyDigitsStringValidator(String message) {
        return CustomValidator.forPredicate(
                string -> {
                        for(int i = 0; i < string.length(); i++) {
                            if(!Character.isDigit(string.charAt(i))) {
                                return false;
                            }
                        }
                        return true;
                    },
                message);
    }

    public static Validator<String> onlyLettersStringValidator(String message) {
        return CustomValidator.forPredicate(
                string -> {
                    for(int i = 0; i < string.length(); i++) {
                        if(!Character.isAlphabetic(string.charAt(i))) {
                            return false;
                        }
                    }
                    return true;
                },
                message);
    }

    public static Validator<String> intRangeStringValidator(int min, int max, String message) {
        return CustomValidator.forPredicate(
                string -> {
                    if(string.isEmpty()) {
                        return true;
                    }
                    try {
                        int val = Integer.parseInt(string);
                        return val >= min && val <= max;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                message);
    }

    public static Validator<String> selectionChooseValidator(String message) {
        return CustomValidator.forPredicate(
                string -> string != null && !string.isEmpty() && Selections.isChosen(string),
                message);
    }

}
