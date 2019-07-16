package com.statuestore.helpers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

public class Validator {

    /*private void validateField(JFXTextField txt, RequiredFieldValidator validator, Void v) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                v.
        });
    }

    private void validateField(JFXPasswordField txt, RequiredFieldValidator validator) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!txt.validate())
                    btnContinuar.setDisable(true);
                else
                    btnContinuar.setDisable(false);
        });
    }

    private void validateField(JFXComboBox cmb, RequiredFieldValidator validator) {
        cmb.getValidators().add(validator);
        cmb.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!cmb.validate())
                    btnContinuar.setDisable(true);
                else
                    btnContinuar.setDisable(false);
        });
    }*/
}
