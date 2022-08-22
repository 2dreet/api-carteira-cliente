package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.UserDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ForgotPasswordRequest;
import br.com.carteira.cliente.request.UserChangePasswordRequest;
import br.com.carteira.cliente.request.UserRequest;
import br.com.carteira.cliente.service.UserService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/all")
	public ResponseEntity<UserDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.getAll(), UserDTO[].class);
	}
	
	@GetMapping
	public ResponseEntity<UserDTO> getUser() {
		return ReponseUtil.getResponse(userService.getUserInContext(), UserDTO.class);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.createUser(userRequest), UserDTO.class);
	}
	
	@PutMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.updateUser(userRequest), UserDTO.class);
	}

	@PutMapping("change-password")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequest changePasswordRequest)
			throws RequestBodyInvalidException {
		userService.changePassword(changePasswordRequest);
		return ResponseEntity.ok("");
	}

	@PutMapping("forgot-password")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
			throws RequestBodyInvalidException {
		userService.resetPassword(forgotPasswordRequest);
		return ResponseEntity.ok("");
	}

}
