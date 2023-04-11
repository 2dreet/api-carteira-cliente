package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.constants.SearchContants;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.model.UserBind;
import br.com.carteira.cliente.domain.model.dto.UserSimpleDTO;
import br.com.carteira.cliente.domain.repository.UserBindRepository;
import br.com.carteira.cliente.domain.repository.UserRepository;
import br.com.carteira.cliente.enums.PersonTypeEnum;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.BindUserRequest;
import br.com.carteira.cliente.request.ForgotPasswordRequest;
import br.com.carteira.cliente.request.UserChangePasswordRequest;
import br.com.carteira.cliente.request.UserRequest;
import br.com.carteira.cliente.response.SearchUserReponse;
import br.com.carteira.cliente.util.ClassUtil;
import br.com.carteira.cliente.util.SearchUtils;

@Service
public class UserService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	PersonService personService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	UserBindRepository userBindRepository;

	public SearchUserReponse searchDependents(String value, Integer page) {
		User user = getUserInContext();

		Integer count = userRepository.getCountBySearch(value, user.getId());
		SearchUserReponse response = SearchUtils.initSearchUserReponse(count, SearchContants.TOTAL_BY_PAGE);

		List<User> users = userRepository.getBySearch(value, user.getId(), SearchContants.TOTAL_BY_PAGE, page);
		if (users == null) {
			users = new ArrayList<>();
		}

		response.setUsers(ClassUtil.convert(users, UserSimpleDTO[].class));

		return response;
	}

	public List<User> getAllDependents() {
		List<User> users = userRepository.findByUserAdminId(getUserInContext().getId());
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public User createUser(UserRequest userRequest) throws RequestBodyInvalidException {
		if (userRequest == null || StringUtils.isBlank(userRequest.getLogin()) || userRequest.getPerson() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do usuário na requisição");
		} else if (!validateLogin(userRequest.getLogin(), null)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.USER_NAME_IN_USE, "Usuário já em uso");
		}

		String password = createPassword();

		Person person = personService.createPerson(userRequest.getPerson(), PersonTypeEnum.USER);

		User user = new User();
		user.setLogin(userRequest.getLogin());
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setRule(userRequest.getRule().toString());
		user.setPerson(person);

		userRepository.save(user);

		emailService.sendSimpleMessage(person.getEmail(), "Cadastro realizado",
				"Olá seu cadastro foi realizado com sucesso! \nlogin: " + userRequest.getLogin() + "\nsenha: "
						+ password);

		return user;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public User createUserDependent(UserRequest userRequest) throws RequestBodyInvalidException {
		User user = createUser(userRequest);
//		user.setUserAdmin(getUserInContext());
		return userRepository.save(user);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public User updateUserDependent(UserRequest userRequest) throws RequestBodyInvalidException {
		if (userRequest == null || userRequest.getId() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do usuário na requisição");
		}

		User user = userRepository.findByIdAndUserAdminId(userRequest.getId(), getUserInContext().getId());
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuário não encontrado.");
		}

		if (!validateLogin(userRequest.getLogin(), userRequest.getId())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.USER_NAME_IN_USE, "Usuário já em uso");
		}

		personService.updatePerson(userRequest.getPerson(), user.getPerson().getId());

		user.setRule(userRequest.getRule().toString());
		user.setLogin(userRequest.getLogin());

		return userRepository.save(user);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public User updateUser(UserRequest userRequest) throws RequestBodyInvalidException {
		if (userRequest == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do usuário na requisição");
		}

		User user = getUserInContext();
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		personService.updatePerson(userRequest.getPerson(), user.getPerson().getId());

		user.setRule(userRequest.getRule().toString());

		userRepository.save(user);

		return user;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void updateUserPersonStatus(Long id, Boolean status) {

		if (id == null || id <= 0 || status == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id ou o status do usuario na requisição");
		}

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		personService.updatePersonStatus(user.getPerson().getId(), status);

		userRepository.save(user);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void changePassword(UserChangePasswordRequest changePasswordRequest) {
		if (changePasswordRequest == null || StringUtils.isBlank(changePasswordRequest.getNewPassword())
				|| StringUtils.isBlank(changePasswordRequest.getOldPassword())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado as senhas do usuario na requisição");
		}

		User user = getUserInContext();
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Senha atual não é valida");
		}

		user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));

		emailService.sendSimpleMessage(user.getPerson().getEmail(), "Troca de senha",
				"Olá a troca da senha foi realizado com sucesso, nova senha é: "
						+ changePasswordRequest.getNewPassword());

		userRepository.save(user);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void resetPassword(ForgotPasswordRequest forgotPasswordRequest) {

		if (forgotPasswordRequest == null || StringUtils.isBlank(forgotPasswordRequest.getLogin())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o login do usuario na requisição");
		}

		User user = userRepository.findByLogin(forgotPasswordRequest.getLogin());
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		String password = createPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));

		emailService.sendSimpleMessage(user.getPerson().getEmail(), "Esqueci minha senha",
				"Olá sua nova senha é: " + password);

		userRepository.save(user);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void resetPasswordDependent(ForgotPasswordRequest forgotPasswordRequest) {

		if (forgotPasswordRequest == null || StringUtils.isBlank(forgotPasswordRequest.getLogin())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o login do usuario na requisição");
		}

		User user = userRepository.findByLoginAndUserAdminId(forgotPasswordRequest.getLogin(),
				getUserInContext().getId());
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		String password = createPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));

		emailService.sendSimpleMessage(user.getPerson().getEmail(), "Esqueci minha senha",
				"Olá sua nova senha é: " + password);

		userRepository.save(user);
	}

	public User getUserInContext() {
		String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByLogin(login);
		return user;
	}

	public User getUserById(Long userId) {
		User user = userRepository.findByIdAndUserAdminId(userId, getUserInContext().getId());
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}
		return user;
	}

	public List<User> getUsersByIds(List<Long> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<>();
		}

		return userRepository.findByIdIn(ids);
	}

	private boolean validateLogin(String login, Long userId) {
		User user = userRepository.findByLogin(login);
		if (user == null || (userId != null && user.getId() == userId)) {
			return true;
		}
		return false;
	}

	private String createPassword() {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi" + "jklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void bindUserDependent(BindUserRequest userRequest) throws RequestBodyInvalidException {
		if (userRequest == null || userRequest.getUserId() == null || userRequest.getUserId() <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados nescessários na requisição");
		}
		
		User user = userRepository.findByIdAndUserAdminId(userRequest.getUserId(), getUserInContext().getId());
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
		}

		if (userRequest.getUnbindUserIds() != null && userRequest.getUnbindUserIds().size() > 0) {
			userBindRepository.deleteAllUserBindByDependents(userRequest.getUnbindUserIds());
		}

		if (userRequest.getBindUserIds() != null && userRequest.getBindUserIds().size() > 0) {
			for(Long userId : userRequest.getBindUserIds()) {
				User userDependent = userRepository.findByIdAndUserAdminId(userId, getUserInContext().getId());
				if (userDependent == null) {
					throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuario não encontrado");
				}
				
				UserBind userBind = userBindRepository.findByDependentIdAndManagerId(userId, userRequest.getUserId());
				if(userBind == null) {
					userBind = new UserBind();
					userBind.setDependent(userDependent);
					userBind.setManager(user);
					userBindRepository.save(userBind);
				}
			}
		}

	}

}
