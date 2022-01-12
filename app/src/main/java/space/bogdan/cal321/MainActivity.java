package space.bogdan.cal321;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;import android.content.pm.ActivityInfo;
import android.content.res.Resources;


public class MainActivity extends AppCompatActivity {

    private Button btnComma;
    private Button btnClear;
    private Button btnDivide;
    private Button btnMultiply;
    private Button btnSubtract;
    private Button btnAddition;
    private Button btnEquals;
    private TextView resultView;
    private final Button[] buttons = new Button[10];
    private float oldNumber;
    private char operation = '0';

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        initFields();

        Resources res = getResources();

        for(int i = 0; i < buttons.length; i++){
            String id = "btn" + i;

            buttons[i] = (Button) findViewById(res.getIdentifier(id, "id", getPackageName()));
        }

        for (Button button : buttons) {
            button.setOnClickListener(view -> changeViewText(view.getResources().getResourceName(view.getId())));
        }

        btnComma.setOnClickListener(view -> {
            String result = resultView.getText().toString();
            if( ! result.contains(".")){
                resultView.append(".");
            }
        });

        btnEquals.setOnClickListener(view -> equalsMethod(true));

        btnClear.setOnClickListener(view -> {
            resultView.setText("0");
            operation = '0';
            oldNumber = 0;
            changeButtonState(true);
        });

        btnDivide.setOnClickListener(view -> changeOperation('/'));

        btnMultiply.setOnClickListener(view -> changeOperation('*'));

        btnSubtract.setOnClickListener(view -> changeOperation('-'));

        btnAddition.setOnClickListener(view -> changeOperation('+'));
    }

    private void initFields(){
        btnComma = (Button) findViewById(R.id.btnComma);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnAddition = (Button) findViewById(R.id.btnAdd);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        resultView = (TextView) findViewById(R.id.resultView);
        oldNumber = 0;
    }

    private void changeOperation(char execute){
        if(operation == '0'){
            operation = execute;
            oldNumber = Float.parseFloat(resultView.getText().toString());
            resultView.setText("0");
        } else {
            equalsMethod(false);
            operation = execute;
        }

        if( ! btnComma.isEnabled()){
            changeButtonState(true);
        }
    }

    private void changeViewText(String id){
        float total = Float.parseFloat(resultView.getText().toString());
        id = String.valueOf(id.charAt(id.length() - 1));

        appeandTextResult(id, total);
    }

    private void appeandTextResult(String id, float number){
        if (number == 0) {
            resultView.setText(id);
        } else {
            resultView.append(id);
        }
    }

    public float divide(float x, float y){
        return x / y;
    }

    public float mulitply(float x, float y){
        return x * y;
    }

    public float subtract(float x, float y){
        return x - y;
    }

    public float addition(float x, float y){
        return x + y;
    }

    public void equalsMethod(boolean isFinal){
        if (operation == '0') return;

        float current = Float.parseFloat(resultView.getText().toString());

        if(current == 0) return;

        oldNumber = getResult(current);

        if (isFinal){
            resultView.setText(String.valueOf(oldNumber));
            operation = '0';
            changeButtonState(false);
        } else {
            resultView.setText("0");
        }
    }

    public float getResult(float current){
        switch(operation){
            case '*':
                return mulitply(current, oldNumber);
            case '/':
                return divide(oldNumber, current);
            case '-':
                return subtract(oldNumber, current);
            case '+':
                return addition(current, oldNumber);
            default:
                return oldNumber;
        }
    }

    private void changeButtonState(boolean stateChange){
        for (Button button : buttons) {
            button.setEnabled(stateChange);
        }

        btnComma.setEnabled(stateChange);
    }
}

