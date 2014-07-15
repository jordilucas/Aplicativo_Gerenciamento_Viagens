package com.livrogoogleandroid.jordi.livroandroid.helper;

import android.app.Activity;
import android.widget.EditText;

import com.livrogoogleandroid.jordi.livroandroid.R;
import com.livrogoogleandroid.jordi.livroandroid.bean.Login;

/**
 * Created by jordi on 02/07/14.
 */
public class LoginHelper extends Activity {

    private EditText usuario;
    private EditText senha;

    private Login login;


    public LoginHelper(Activity activity){
        usuario = (EditText) activity.findViewById(R.id.edtUsuario);
        senha = (EditText) activity.findViewById(R.id.edtSenha);

        login = new Login();
    }

    public Login getLogin(){

        login.setUsuario(usuario.getText().toString());
        login.setSenha(senha.getText().toString());

        return login;
    }

}
