package Validacion_email;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Pattern;

public class Validador_email {
    private static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Email Validator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        JTextField emailField = new JTextField();
        JLabel validationLabel = new JLabel();

        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }

            void validate() {
                String email = emailField.getText();
                if (EMAIL_PATTERN.matcher(email).matches()) {
                    validationLabel.setText("Valid email");
                    validationLabel.setForeground(Color.GREEN);
                } else {
                    validationLabel.setText("Invalid email");
                    validationLabel.setForeground(Color.RED);
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(emailField, BorderLayout.NORTH);
        frame.add(validationLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}