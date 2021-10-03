package com.example.experiment.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.experiment.R;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    TextView tv;
    StringBuilder viewStr = new StringBuilder();
    int[] numBt;
    StringBuilder numStr = new StringBuilder();

    Button bt_add;
    Button bt_sub;
    Button bt_mul;
    Button bt_div;
    Button bt_eq;
    Button bt_pt;
    Boolean flag = false;
    Button bt_c;
    Button bt_back;
    Button bt_mc;
    Button bt_mr;
    Button bt_madd;
    Button bt_msub;
    String result;
    String lastRes;
    List<String> bufferList;

    List<String> arrList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        bufferList = new ArrayList<String>();
        bufferList.add("0");
        arrList =new ArrayList<String>();
        initButton();
        buttonListener();
    }

    public void initButton(){
        tv = (TextView)findViewById(R.id.tv);
        bt_add = (Button)findViewById(R.id.add);
        bt_sub = (Button)findViewById(R.id.sub);
        bt_mul =(Button)findViewById(R.id.mul);
        bt_div = (Button)findViewById(R.id.div);
        bt_eq = (Button)findViewById(R.id.eq);
        bt_pt = (Button)findViewById(R.id.pt);
        numBt = new int[]{
                R.id.bt0,R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,
                R.id.bt5,R.id.bt6,R.id.bt7,R.id.bt8,R.id.bt9
        };
        bt_c = (Button)findViewById(R.id.C);
        bt_back = (Button)findViewById(R.id.back);
        bt_mc = (Button)findViewById(R.id.MC);
        bt_mr = (Button)findViewById(R.id.Mr);
        bt_madd = (Button)findViewById(R.id.Madd);
        bt_msub = (Button)findViewById(R.id.Msub);
    }

    public void buttonListener(){
        for (int i = 0; i < numBt.length;i++ ) {
            Button temp = (Button) findViewById(numBt[i]);
            addNumListener(temp,String.valueOf(i));
        }
        addNumListener(bt_pt,".");
        addOpListener(bt_add,"+");
        addOpListener(bt_sub, "-");
        addOpListener(bt_mul, "*");
        addOpListener(bt_div, "/");
        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrList.clear();
                numStr.delete(0,numStr.length());
                viewStr.delete(0,viewStr.length());
                tv.setText(viewStr);
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    arrList.add(numStr.toString());
                    numStr.delete(0, numStr.length());
                    arrList.remove(arrList.size() - 1);
                    viewStr.delete(viewStr.length() - 1, viewStr.length());
                    tv.setText(viewStr);
                }catch (RuntimeException e){
                    Toast.makeText(CalculatorActivity.this, "没有数据,无法退后", Toast.LENGTH_SHORT);
                }
            }
        });
        bt_eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    arrList.add(numStr.toString());
                    numStr.delete(0, numStr.length());
                    tv.setText(getResults(arrList));
                    arrList.clear();
                    viewStr.delete(0, viewStr.length());
                    flag =false;
                }catch (RuntimeException e){
                    Toast.makeText(CalculatorActivity.this, "没有输入数据", Toast.LENGTH_SHORT);
                }
            }
        });
        addMopListener(bt_madd,"+");//M+
        addMopListener(bt_msub,"-");//M-
        bt_mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(bufferList.get(0));
            }
        });
        bt_mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bufferList.clear();
                bufferList.add("0");
            }
        });
    }

    public void addMopListener(Button bt,final  String op){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastRes = result;
                bufferList.add(op);
                bufferList.add(lastRes);
                String temp = getResults(bufferList);
                bufferList.clear();
                bufferList.add(temp);
            }
        });

    }

    public void addNumListener(Button bt,final String num ){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numStr.append(num);
                viewStr.append(num);
                tv.setText(viewStr);
                flag = true;
            }
        });
    }
    public void addOpListener(Button bt,final String nowChar){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrList.isEmpty()&&!flag) {
                    numStr.append(0);
                    flag = true;
                }
                //前面是运算符
                if(!flag){
                    System.out.println("前方运算符");
                    arrList.remove(arrList.size() - 1);
                    arrList.add(nowChar);
                    //替换显示框
                    viewStr.delete(viewStr.length() - 1, viewStr.length());
                    viewStr.append(nowChar);
                    tv.setText(viewStr);
                    flag = false;
                }//前面是数字或.
                else {
                    //前方输入数据入队
                    arrList.add(numStr.toString());
                    System.out.println("数字入队"+numStr);
                    numStr.delete(0,numStr.length());
                    System.out.println(nowChar+"(前方数字)");
                    arrList.add(nowChar);//入队
                    viewStr.append(nowChar);//入显示框
                    tv.setText(viewStr); //显示
                    flag = false;
                }
            }
        });
    }

    public String getResults(List<String> infixList){
        System.out.println("InfixList"+arrList.toString());
        CalculatorResults cr = new CalculatorResults(infixList);
        cr.infixParseToSuffix();
        cr.calculateSuffix();
        result = String.valueOf(cr.getResult());
        if(result.indexOf(".") > 0){
            result = result.replaceAll("0+?$", "");//去掉多余的0
            result = result.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return result;
    }
}