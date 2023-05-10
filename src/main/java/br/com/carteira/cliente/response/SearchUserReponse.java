package br.com.carteira.cliente.response;

import br.com.carteira.cliente.domain.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserReponse {
	
	UserDTO[] users;
	Integer total;
	Integer totalByPage;
	Integer totalPage;
	
}
