package br.com.carteira.cliente.util;

import br.com.carteira.cliente.constants.SearchContants;
import br.com.carteira.cliente.response.SearchUserReponse;

public class SearchUtils {

	public static SearchUserReponse initSearchUserReponse(Integer count, Integer totalByPage) {
		if(count == null) {
			count = 0;
		}
		
		if(totalByPage == null) {
			totalByPage = 0;
		}
		
		SearchUserReponse response = new SearchUserReponse();
		response.setTotal(count);
		response.setTotalByPage(SearchContants.TOTAL_BY_PAGE);
		
		if(count <= totalByPage) { 
			response.setTotalPage(1);
		} else {
			response.setTotalPage(count / totalByPage);
			if(count % totalByPage != 0) {
				response.setTotalPage(response.getTotalPage() + 1);
			}
		}
		
		return response;
	}
	
}
