package br.com.carteira.cliente.controller;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server")
public class ServerController {

	@GetMapping
	public ResponseEntity<JSONObject> getStatus(){
		JSONObject json = new JSONObject();
		json.put("status", true);
		return ResponseEntity.ok(json);
	}
	
}
