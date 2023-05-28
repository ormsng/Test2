package superapp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import superapp.boundaries.user.UserBoundary;
import superapp.boundaries.user.UserId;
import superapp.data.UserEntity;
import superapp.data.UserRole;

import static superapp.utils.Constants.DELIMITER;

@Component
public class UserConverter {
	private String springApplicationName;

	/**
	 * this method injects a configuration value of spring
	 */
	@Value("${spring.application.name:2023b.Liran.Sorokin-Student4U}")
	public void setSpringApplicationName(String springApplicationName) {
		this.springApplicationName = springApplicationName;
	}
	
	/**
	 * Convert user entity to user boundary
	 * 
	 * @param userEntity user entity
	 * @return UserBoundary
	 */
	public UserBoundary entityToBoundary(UserEntity userEntity) {
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setAvatar(userEntity.getAvatar());
		userBoundary.setRole(userEntity.getRole().toString());
		userBoundary.setUserId(this.toBoundaryUserId(userEntity.getUserId()));
		userBoundary.setUsername(userEntity.getUserName());
		return userBoundary;

	}
	
	/**
	 * Convert from String user id to UserId
	 * 
	 * @param userId user id
	 * @return UserId
	 */
	public UserId toBoundaryUserId(String userId) {
		if (userId != null) {
			UserId newUserId = new UserId();
			String[] attr = userId.split(DELIMITER);
			newUserId.setSuperapp(attr[0]);
			newUserId.setEmail(attr[1]);
			return newUserId;
		} else {
			return null;
		}
	}
	
	/**
	 * Convert from user Boundary to user Entity
	 *
	 * @param userBoundary user boundary
	 * @return UserEntity user entity
	 */
	public UserEntity boundaryToEntity(UserBoundary userBoundary) {
		UserEntity userEntity = new UserEntity();
		userEntity.setAvatar(userBoundary.getAvatar());

		try {
			UserRole role = UserRole.valueOf(userBoundary.getRole());
			userEntity.setRole(role);
		} catch (Exception e) {
			throw new RuntimeException("Could not find role: " + userBoundary.getRole());
		}
		userEntity.setUserId(this.userIdToString(userBoundary.getUserId()));
		userEntity.setUserName(userBoundary.getUsername());
		return userEntity;

	}
	/**
	 * Convert from UserId to string with delimiter
	 * 
	 * @param userId user id
	 * @return String application name followed by delimiter and user email
	 */
	public String userIdToString(UserId userId) {
		return springApplicationName + DELIMITER + userId.getEmail();
	}
	
	

}
