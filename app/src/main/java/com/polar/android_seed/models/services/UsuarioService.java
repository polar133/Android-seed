package com.polar.android_seed.models.services;

import android.content.Context;

import com.polar.android_seed.models.dao.UsuarioDao;
import com.polar.android_seed.models.dao.impl.UsuarioDaoImpl;
import com.polar.android_seed.models.data.entities.Usuario;
import com.polar.android_seed.sync.ApiManager;

import java.util.HashMap;

import retrofit.RetrofitError;

public class UsuarioService {

    private static UsuarioService instance;
    UsuarioDao usuarioDao;

    private UsuarioService() {
        usuarioDao = new UsuarioDaoImpl();
    }

    public static UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    public HashMap<String, Object> cargarLogin(Context context, String login, String password) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        try {

            HashMap<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("login", login);
            credentials.put("password", password);


            respuesta = ApiManager.geApiClient().postLogin(credentials);
            if (respuesta.keySet().contains("token")) {
                Usuario usuario = new Usuario();
                usuario.setUsername(login);
                usuario.setNombrecompleto("Ejemplo");
                usuario.setDireccioncorreo(login + "@ejemplo.com");
                usuario.setSexo("M");
                usuario.setEstado("a");
                usuario.setTelefonocelular("+582125555555");
                usuarioDao.createUsuario(usuario);
            }
            return respuesta;

        } catch (RetrofitError e) {
            respuesta.put("Error", "Error de conexión. Intente de nuevo.");
            return respuesta;
        }
    }


    public Usuario cargarUsuario() {
        return usuarioDao.getUsuario();
    }

}
