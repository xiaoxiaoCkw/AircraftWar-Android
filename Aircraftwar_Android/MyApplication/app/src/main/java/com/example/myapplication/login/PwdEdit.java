package com.example.myapplication.login;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myapplication.R;

//密码框控件
public class PwdEdit extends LinearLayout implements View.OnClickListener {
    private EditText editText;
    private Button showButton;

    //true为隐藏，false为显示
    private boolean mode = true;

    //设置提示文字
    public void setEditTextHint(String hint){
        if(editText!=null){
            editText.setHint(hint);
        }
    }
    //获得输入框文字
    public String getText(){
        return editText.getText().toString();
    }

    public PwdEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_view,this,true);
        editText = findViewById(R.id.et_view);

        //一开始密码是隐藏的，因此一开始使用闭眼图片，并且将EditText的输入类型设置为密码(不可见）
        showButton= findViewById(R.id.bt_show);
        showButton.setBackgroundResource(R.drawable.hide_pas);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //当点击了显示/隐藏按钮，则改变editText的文字显示方式
        switch (v.getId()){
            case R.id.bt_show:
                //从隐藏变显示
                if(mode){
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //为了点击之后输入框光标不变
                    editText.setSelection(editText.getText().length());
                    showButton.setBackgroundResource(R.drawable.show_pas);
                    mode = !mode;
                }else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //为了点击之后输入框光标不变
                    editText.setSelection(editText.getText().length());
                    showButton.setBackgroundResource(R.drawable.hide_pas);
                    mode = !mode;
                }
                break;
            default:
                break;
        }
    }

    //获得输入框内容
    public EditText getEditText(){
        return editText;
    }

}
