package com.example.calcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView solutionTv,resultV;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonComma,buttonEqual;
    MaterialButton buttonModulo,buttonMultp,buttonPlus,buttonMinus,buttonDivide;
    MaterialButton buttonSin,buttonCos,buttonTan,buttonArcSin,buttonArcCos,buttonArcTan,buttonLog,buttonLn,buttonRacine,buttonExp,
            buttonPi,buttonAbs,buttonPrime,buttonSquare, buttonSquareNbr;

    // for the navigation View
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {//Bundle est le dernier etat sauvgarder de l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        solutionTv=findViewById(R.id.solution_tv);
        resultV=findViewById(R.id.resultTV);
        assignId(button0,R.id.button0);
        assignId(button1,R.id.button1);
        assignId(button2,R.id.button2);
        assignId(button3,R.id.button3);
        assignId(button4,R.id.button4);
        assignId(button5,R.id.button5);
        assignId(button6,R.id.button6);
        assignId(button7,R.id.button7);
        assignId(button8,R.id.button8);
        assignId(button9,R.id.button9);
        assignId(buttonComma,R.id.buttoncomma);
        assignId(buttonEqual,R.id.buttonEqual);
        assignId(buttonC,R.id.buttonC);
        assignId(buttonBrackOpen,R.id.buttonBrackOpen);
        assignId(buttonBrackClose,R.id.buttonBrackClose);
        assignId(buttonModulo,R.id.buttonModulo);
        assignId(buttonMultp,R.id.buttonMultp);
        assignId(buttonPlus,R.id.buttonPlus);
        assignId(buttonMinus,R.id.buttonMinus);
        assignId(buttonDivide,R.id.buttonDivide);
        assignId(buttonDivide,R.id.buttonDivide);
        assignId(buttonSin,R.id.buttonSin);
        assignId(buttonCos,R.id.buttonCos);
        assignId(buttonTan,R.id.buttonTan);
        assignId(buttonArcSin,R.id.buttonArcsin);
        assignId(buttonArcCos,R.id.buttonArccos);
        assignId(buttonArcTan,R.id.buttonArcTan);
        assignId(buttonLog,R.id.buttonLog);
        assignId(buttonLn,R.id.buttonLn);
        assignId(buttonRacine,R.id.buttonRacine);
        assignId(buttonSquare,R.id.buttonsquare);
        assignId(buttonExp,R.id.buttonexp);
        assignId(buttonPi,R.id.buttonPi);
        assignId(buttonAbs,R.id.buttonabs);
        assignId(buttonPrime,R.id.buttonPrime);
        assignId(buttonSquare,R.id.buttonsquare);
        assignId(buttonSquareNbr,R.id.buttonSquareTwo);


        //for the Menu
        drawerLayout =findViewById(R.id.drawer_Layout);
        navigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.close_menu);
       // if(drawerLayout!=null)
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.historique:
                        Log.i("MENU_DRAWER_TAG","Historique is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.time:
                        Log.i("MENU_DRAWER_TAG","time is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);

                    case R.id.sittings:
                        Log.i("MENU_DRAWER_TAG","sittings is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });


    }

    @SuppressLint("SuspiciousIndentation")
    void assignId(MaterialButton btn , int id){
        btn=findViewById(id);
        if(btn!=null)
        btn.setOnClickListener(this);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View view) {
    MaterialButton  button=(MaterialButton) view; //we get the button that is clicked
    String buttonText=button.getText().toString();
    String dataToCalculate=solutionTv.getText().toString();
    if(buttonText.equals("C") ) {
        if (dataToCalculate.length() == 1) {
            solutionTv.setText("0");
            dataToCalculate = "0";
        } else
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
    }


    else if(buttonText.equals("=")){
        solutionTv.setText(resultV.getText());
        return;
    }

    else{
        if(dataToCalculate.equals("0") && !buttonText.equals(","))
            dataToCalculate="";
        dataToCalculate+=buttonText;
    }


    solutionTv.setText(dataToCalculate);


    String finalResult=getResult(dataToCalculate);
    if(!finalResult.equals("Err")){
        resultV.setText(finalResult);
    }
    }

    String getResult(String data){

        try {
            data=data.replace("%","#");
            data=data.replace("log","lg");
            data=data.replace("π","pi");



        Expression exp=new Expression(data);
        String result=String.valueOf(exp.calculate());
        return  result;
        }catch (Exception e){
            return "Err";
        }
    }

}