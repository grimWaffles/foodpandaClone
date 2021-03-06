package com.example.foodpandaclone.view.fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodpandaclone.R;

public class CustomDialogFragment extends DialogFragment {

    //Interface
    private OnPromptClick mOnPromptClick;

    //Widgets
    TextView tv_message; Button btn_yes; ImageView btn_close_prompt; private String message;

    //Booleans
    private boolean isAPrompt=false;

    public interface OnPromptClick{
        void onPromptClick(String message);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.mOnPromptClick=(OnPromptClick) context;
    }

    public CustomDialogFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public CustomDialogFragment(String message){
        super();

        if (!message.equals("")){
            isAPrompt=true;
            this.message=message;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_custom_dialog,container,false);

        tv_message=view.findViewById(R.id.tv_message);

        btn_close_prompt=view.findViewById(R.id.btn_close_prompt);
        btn_yes=view.findViewById(R.id.btn_yes);

        if(isAPrompt){
            if(this.message.equals("Ask for payment")){
                btn_yes.setText("Payment Received");
            }
            else{
                btn_yes.setText("Okay");
            }
            tv_message.setText(message);
        }

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAPrompt){
                    getDialog().dismiss();
                    mOnPromptClick.onPromptClick(message);

                }
                else{

                    getDialog().dismiss();
                }
            }
        });

        btn_close_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
