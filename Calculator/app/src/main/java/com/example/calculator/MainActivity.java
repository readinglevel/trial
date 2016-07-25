package com.example.calculator;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView displayScreen;
    TextView savedScreen;
    String[] operations = {"+","-","/","*","="};
    List<String> numbersPressed = new ArrayList<String>();
    boolean operationDone = false;
    float result = 0;
    boolean addition, subtraction, multiplication, division, error;
    int inRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        displayScreen = (TextView) findViewById(R.id.display_screen);
        savedScreen = (TextView) findViewById(R.id.saved_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onAdd(float integer) {
        result += integer;
    }

    private void onSubtract(float integer) {
        result -= integer;
    }

    private void onMultiply(float integer) {
        result *= integer;
    }

    private void onDivide(float integer) {
        if (integer == 0) {
            savedScreen.setText("Error: Division by zero");
            error = true;
        } else {
            result /= integer;
        }
    }

    @Override
    public void onClick(View clicked) {
        Button pressed = (Button) clicked;
        String buttonValue = pressed.getText().toString();
        List operationsArray = Arrays.asList(operations);
        if ((operationsArray).contains(buttonValue)) {
            if (!(operationDone)) {
                String newOperand = displayScreen.getText().toString();
                numbersPressed.add(newOperand);
                numbersPressed.add(buttonValue);
                savedScreen.append(displayScreen.getText() + buttonValue);
                inRow += 1;
                if (inRow > 1) {
                    error = true;
                    operationDone = true;
                    savedScreen.setText("Error: Operators in row");
                }
                if ("=".equals(buttonValue)) {
                    if (!(error)) {
                        result = Float.parseFloat(numbersPressed.get(0));
                        for (String operand : numbersPressed) {
                            if (operationsArray.contains(operand)) {
                                if ("+".equals(operand)) {
                                    addition = true;
                                } else if ("-".equals(operand)) {
                                    subtraction = true;
                                } else if ("*".equals(operand)) {
                                    multiplication = true;
                                } else if ("/".equals(operand)) {
                                    division = true;
                                }
                                continue;
                            }
                            inRow = 0;
                            float floatVersion = Float.parseFloat(operand);
                            if (addition) {
                                onAdd(floatVersion);
                                addition = false;
                            } else if (subtraction) {
                                onSubtract(floatVersion);
                                subtraction = false;
                            } else if (multiplication) {
                                onMultiply(floatVersion);
                                multiplication = false;
                            } else if (division) {
                                onDivide(floatVersion);
                                division = false;
                            }
                        }
                        if (!error) {
                            savedScreen.append(Float.toString(result));
                        }
                        operationDone = true;
                    }
                }
                displayScreen.setText("");
            }
        } else {
            if ("Clear".equals(buttonValue)) {
                savedScreen.setText("");
                displayScreen.setText("");
            } else {
                if (operationDone) {
                    savedScreen.setText("");
                    numbersPressed.clear();
                    result = 0;
                    addition = false;
                    subtraction = false;
                    multiplication = false;
                    division = false;
                    inRow = 0;
                    error = false;
                }
                displayScreen.append(buttonValue);
                operationDone = false;
                inRow = 0;
            }
        }
    }
}