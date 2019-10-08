package com.example.hubProject.Service;

import com.example.hubProject.Commons.ApiResponse;
import com.example.hubProject.DTO.UserEmailDTO;
import com.example.hubProject.DTO.UserDto;
import com.example.hubProject.DTO.UserPasswordDTO;
import com.example.hubProject.Model.ResetPasswordToken;
import com.example.hubProject.Model.User;
import com.example.hubProject.Repository.ResetPasswordTokenRepository;
import com.example.hubProject.Repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {


	private UUID corrId;

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;


	@Autowired
	private UserDao userDao;



	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findUserByActive(username);

		if(user == null){
			throw new UsernameNotFoundException("Invalid Credentials.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getUserType()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(String email) {

		User user= userDao.findByEmail(email);
		user.setStatus(false);
		userDao.save(user);
	}



	public User findOne(String username) {
		return userDao.findUserByActive(username);
	}

	public User getUser(String email) {
		User optionalUser = userDao.findByEmail(email);
		return optionalUser;
	}


	public ApiResponse save(UserDto user) {
		User founduser = userDao.findByEmail(user.getEmail());
		if(founduser == null) {
			User newUser = new User();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1); // to get previous year add -1
			Date nextYear = cal.getTime();
			newUser.setExpiry(nextYear);
			newUser.setEmail(user.getEmail());
			newUser.setActive(true);
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserType(user.getUserType());
			newUser.setStatus(user.getStatus());
			return new ApiResponse<>(HttpStatus.OK.value(), "User information has been saved successfully.",	userDao.save(newUser));//return ;
		}
		else{
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User already exist.",null);//return ;
		}

	}
	public List<User> getAllUsers() {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();

		List<User> list =userDao.findAllExceptCurrentUser(email);
		return list;

	}

	public ResponseEntity<String> forgotPassword(UserEmailDTO userEmailDTO) {
		User user = userDao.findByEmail(userEmailDTO.getEmail());
		ResetPasswordToken resetPasswordToken=new ResetPasswordToken();
		String randomId = UUID.randomUUID().toString();
		if (user == null) {
			return new ResponseEntity<>("\"Incorrect username\"", HttpStatus.NOT_FOUND);
		} else {
			try {
				emailSenderService.send(userEmailDTO.getEmail(),
						"ReachTeach",
						"Your password reset link is: http://localhost:4200/changepassword/" + randomId);
				userDao.save(user);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, 1);
				resetPasswordToken.setCreated(new Date());
				resetPasswordToken.setEmail(user.getEmail());
				resetPasswordToken.setToken(randomId);
				resetPasswordToken.setValid_until(calendar.getTime());
				resetPasswordTokenRepository.save(resetPasswordToken);

			} catch (Exception exception) {
				return new ResponseEntity<>("\"Email incorrect\"", HttpStatus.NOT_FOUND);

			}
			return new ResponseEntity<>("\"Password sent to the mail\"", HttpStatus.OK);
		}
	}

//	public ResponseEntity<String> postNewUser(UserEmailDTO userEmailDTO)
//	{
//		User userObj=userDao.findByEmail(userEmailDTO.getEmail().toLowerCase());
//		if(userObj==null) {
//			String password = UUID.randomUUID().toString();
//			User user=new User();
//			user.setPassword(bcryptEncoder.encode(password));
//			user.setEmail(userEmailDTO.getEmail());
//			user.setUserType("USER");
//			user.setActive(true);
//			try {
//				emailSenderService.send(userEmailDTO.getEmail(),
//						"[DATAHUB] CREDENCIALES DE ACCESO A LA APLICACIÓN",
//						"Bienvenido a DataHub. Has sido registrado en la aplicación. Tus credenciales de acceso son: email: " + userEmailDTO.getEmail() + ", contraseña: " + password);
//				userDao.save(user);
//			}
//			catch(Exception exception) {
//				{
//					return new ResponseEntity<>("\"Email del aplication properties incorrecto\"", HttpStatus.NOT_FOUND);
//
//				}
//
//			}
//			return new ResponseEntity<>("\"Nuevo usuario creado\"", HttpStatus.OK);
//		}
//		else if(userObj.getActive()==false)
//		{
//
//			userObj.setActive(true);
//			String password = UUID.randomUUID().toString();
//
//			userObj.setPassword(bcryptEncoder.encode(password));
//			userObj.setUserType("USER");
//			try {
//				emailSenderService.send(userEmailDTO.getEmail(),
//						"[DATAHUB] CREDENCIALES DE ACCESO A LA APLICACIÓN",
//						"Bienvenido a DataHub. Has sido registrado en la aplicación. Tus credenciales de acceso son: email: " + userEmailDTO.getEmail() + ", contraseña: " + password);
//
//				userDao.save(userObj);
//				return new ResponseEntity<>("\"Nuevo usuario creado\"", HttpStatus.OK);
//			}
//			catch(Exception exception) {
//				{
//					return new ResponseEntity<>("\"Email del aplication properties incorrecto\"", HttpStatus.NOT_FOUND);
//				}
//
//			}
//
//		}
//		else {
//			return new ResponseEntity<>("\"El usuario ya existe en la aplicación\"", HttpStatus.NOT_FOUND); }
//	}

	public ResponseEntity<String> updateUser(String email,UserDto user)
	{
		User responseUser=userDao.findByEmail(email);
		responseUser.setUserType(user.getUserType());
		userDao.save(responseUser);
		return new ResponseEntity<>("\"User information has been updated successfully\"", HttpStatus.OK);

	}

	public ResponseEntity<String> changePasswordById(String token,UserPasswordDTO userPasswordDTO) {
		ResetPasswordToken resetPasswordToken=resetPasswordTokenRepository.findByToken(token);

		if(resetPasswordToken==null)
		{
			return new ResponseEntity<>("\"Invalid Token\"", HttpStatus.NOT_FOUND);
		}
		else {
			User user = userDao.findByEmail(resetPasswordToken.getEmail());

			Boolean check = bcryptEncoder.matches(userPasswordDTO.getCurrentPassword(), user.getPassword());

			if (check && userPasswordDTO.getNewPassword().equals(userPasswordDTO.getConfirmPassword())) {
				user.setPassword(bcryptEncoder.encode(userPasswordDTO.getNewPassword()));
				userDao.save(user);
				return new ResponseEntity<>("\"The password has been updated correctly\"", HttpStatus.OK);
			} else if (!check) {
				return new ResponseEntity<>("\"Wrong current password\"", HttpStatus.NOT_FOUND);
			} else if (!userPasswordDTO.getNewPassword().equals(userPasswordDTO.getConfirmPassword())) {
				return new ResponseEntity<>("\"Passwords do not match\"", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>("\"An error occurred while updating the password\"", HttpStatus.NOT_FOUND);
			}
		}

	}

	public User sharedUserCheck(String email) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		if(username.equalsIgnoreCase(email))
			return null;
		else
		{
			User user=userDao.findUserByActive(email);
			return user!=null?user:null;
		}

	}



}
