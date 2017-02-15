package com.concafras.gestao.util;

import java.sql.Connection;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ReportesDAOJDBC extends JdbcDaoSupport {

	public Connection getConexion() {
		Connection con;
		try {
			con = getDataSource().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return con;
	}

	public void closeConecction(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}