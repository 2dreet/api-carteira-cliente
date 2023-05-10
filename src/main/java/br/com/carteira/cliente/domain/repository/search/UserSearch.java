package br.com.carteira.cliente.domain.repository.search;

public class UserSearch {

	private final static String sqlBase = "" + "inner join persons p on u.person_id = p.id "
			+ "where p.status = true and u.user_admin_id = :userAdminId "
			+ "and ( LOWER(p.name) like (%:value%) or LOWER(u.login) like (%:value%) ) ";

	public final static String sqlCount = "select COUNT(u.*) from users u " + sqlBase;

	public final static String sqlResult = "select u.* from users u " + sqlBase
			+ " LIMIT :totalByPage OFFSET(:page - 1) * :totalByPage ";

}
