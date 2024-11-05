package org.project.sohwagi.user.application.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String fcmToken;

	@Column
	private String nickName;

	public User(String fcmToken, String nickName) {
		this.fcmToken = fcmToken;
		this.nickName = nickName;
	}

}
