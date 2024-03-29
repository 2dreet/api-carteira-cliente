package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.UserBindDTO;
import br.com.carteira.cliente.domain.model.dto.UserDTO;
import br.com.carteira.cliente.domain.model.dto.UserSimpleDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.BindUserRequest;
import br.com.carteira.cliente.request.ForgotPasswordRequest;
import br.com.carteira.cliente.request.UserChangePasswordRequest;
import br.com.carteira.cliente.request.UserRequest;
import br.com.carteira.cliente.response.SearchUserReponse;
import br.com.carteira.cliente.service.UserService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/dependents")
	public ResponseEntity<UserSimpleDTO[]> getAllDependents() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.getAllDependents(), UserSimpleDTO[].class);
	}

	@GetMapping("/dependents/search")
	public ResponseEntity<SearchUserReponse> searchDependents(@RequestParam("searchValue") String searchValue,
			@RequestParam("page") Integer page) throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(userService.searchDependents(searchValue, page), SearchUserReponse.class);
	}

	@GetMapping
	public ResponseEntity<UserDTO> getUser() {
		return ReponseUtil.getResponse(userService.getUserInContext(), UserDTO.class);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
		return ReponseUtil.getResponse(userService.getUserById(userId), UserDTO.class);
	}
	
	@GetMapping("/bind/{userId}")
	public ResponseEntity<UserBindDTO> getUserBindById(@PathVariable Long userId) {
		return ReponseUtil.getResponse(userService.getUserById(userId), UserBindDTO.class);
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.createUser(userRequest), UserDTO.class, HttpStatus.CREATED);
	}

	@PostMapping("/dependent")
	public ResponseEntity<UserDTO> createUserDependent(@RequestBody UserRequest userRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.createUserDependent(userRequest), UserDTO.class, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.updateUser(userRequest), UserDTO.class, HttpStatus.ACCEPTED);
	}

	@PutMapping("/dependent")
	public ResponseEntity<UserDTO> updateUserDependent(@RequestBody UserRequest userRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.updateUserDependent(userRequest), UserDTO.class,
				HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/bind/dependent")
	public ResponseEntity<String> bindUserDependent(@RequestBody BindUserRequest userRequest)
			throws RequestBodyInvalidException {
		userService.bindUserDependent(userRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}

	@PutMapping("change-password")
	public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequest changePasswordRequest)
			throws RequestBodyInvalidException {
		userService.changePassword(changePasswordRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}

	@PutMapping("forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
			throws RequestBodyInvalidException {
		userService.resetPassword(forgotPasswordRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}
	
	@PutMapping("dependent/forgot-password")
	public ResponseEntity<String> forgotDependentPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
			throws RequestBodyInvalidException {
		userService.resetPasswordDependent(forgotPasswordRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}
}
