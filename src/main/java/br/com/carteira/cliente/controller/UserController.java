package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.UserDTO;
import br.com.carteira.cliente.domain.model.dto.UserProductDTO;
import br.com.carteira.cliente.domain.model.dto.UserSimpleDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ForgotPasswordRequest;
import br.com.carteira.cliente.request.UserChangePasswordRequest;
import br.com.carteira.cliente.request.UserProductRequest;
import br.com.carteira.cliente.request.UserRequest;
import br.com.carteira.cliente.service.UserProductService;
import br.com.carteira.cliente.service.UserService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserProductService userProductService;

	@GetMapping("/all")
	public ResponseEntity<UserSimpleDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.getAll(), UserSimpleDTO[].class);
	}

	@GetMapping
	public ResponseEntity<UserDTO> getUser() {
		return ReponseUtil.getResponse(userService.getUserInContext(), UserDTO.class);
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.createUser(userRequest), UserDTO.class, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserRequest userRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userService.updateUser(userRequest), UserDTO.class, HttpStatus.ACCEPTED);
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

	@GetMapping("/{userId}/product")
	public ResponseEntity<UserProductDTO[]> getUserProducts(@PathVariable Long userId)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userProductService.getUserProducts(userId), UserProductDTO[].class);
	}

	@PostMapping("/product")
	public ResponseEntity<UserProductDTO[]> createUserProduct(@RequestBody UserProductRequest userProductRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userProductService.createUserProduct(userProductRequest), UserProductDTO[].class,
				HttpStatus.CREATED);
	}

	@DeleteMapping("/product")
	public ResponseEntity<String> deleteUserProduct(@RequestBody UserProductRequest userProductRequest)
			throws RequestBodyInvalidException {
		userProductService.deleteUserProduct(userProductRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}
}
