package com.example.gridcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String calculate() {
        String operation = ((TextView) findViewById(R.id.operation_bar)).getText().toString();
        List<String> numeros = new ArrayList<>();
        List<Character> operadores = new ArrayList<>();
        StringBuilder moldeCifra = new StringBuilder();
        Boolean repetido = false;
        for (char numero : operation.toCharArray()){
            if (Character.isDigit(numero)){
                repetido = false;
                moldeCifra.append(numero);
            }
            if (numero == '+' || numero == '-' || numero == '*' || numero == '/'){
                if (repetido){
                    return "Syntax Error";
                }
                operadores.add(numero);
                numeros.add(moldeCifra.toString());
                moldeCifra.setLength(0);
                repetido = true;
            }
        }
        double resultado = 0;
        for (int i=0; i < numeros.size() - 1; i++){
            double num1 = Double.parseDouble(numeros.get(i));
            double num2 = Double.parseDouble(numeros.get(i+1));
            char operador = operadores.get(i);
            switch (operador){
                case '+':
                    num1 = num1 + num2;
                    break;
                case '-':
                    num1 = num1 - num2;
                    break;
                case '*':
                    num1 = num1 * num2;
                    break;
                case '/':
                    num1 = num1 / num2;
                    break;
                default: return "Syntax Error";
            }
            numeros.set(i+1, String.valueOf(num1));
        }
        return numeros.get(numeros.size() - 1);
    }
    public void buttonPressed(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        if (!buttonText.equals("=")) {
            if (buttonText.equals("AC")) {
                ((TextView) findViewById(R.id.operation_bar)).setText("");
                ((TextView) findViewById(R.id.result_bar)).setText("");
                return;
            }
            if (buttonText.equals("←")) {
                StringBuilder operation = new StringBuilder(((TextView) findViewById(R.id.operation_bar)).getText());
                operation.deleteCharAt(operation.length() - 1);
                ((TextView) findViewById(R.id.operation_bar)).setText(operation.toString());
                return;
            }
           ((TextView) findViewById(R.id.operation_bar)).append(buttonText);
        }
        else {
            String result = calculate();
            ((TextView) findViewById(R.id.result_bar)).setText(result);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}