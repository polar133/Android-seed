package com.polar.android_seed.models.dao.impl;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.polar.android_seed.helpers.DBHelper;
import com.polar.android_seed.models.dao.UsuarioDao;
import com.polar.android_seed.models.data.entities.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

    public Usuario getUsuario() {
        RuntimeExceptionDao<Usuario, Integer> usuarioDao = DBHelper.getInstance().getUsuarioDataDao();
        if (usuarioDao.queryForAll().size() >= 1) return usuarioDao.queryForAll().get(0);
        else return new Usuario();
    }

    public Usuario createUsuario(Usuario usuario) {

        RuntimeExceptionDao<Usuario, Integer> usuarioDao = DBHelper.getInstance().getUsuarioDataDao();
        if (!usuarioDao.isTableExists()) {
            DBHelper.getInstance().createAllTables();
        }

        usuarioDao.create(usuario);
        return usuario;
    }

}
