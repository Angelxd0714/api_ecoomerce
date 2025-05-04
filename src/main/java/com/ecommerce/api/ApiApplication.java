package com.ecommerce.api;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.api.persistence.entities.Permissions;
import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.repository.RepositoryPermissions;
import com.ecommerce.api.persistence.repository.RepositoryRoles;
import com.ecommerce.api.persistence.repository.RepositoryUsers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RepositoryPermissions servicePermission, RepositoryRoles serviceRol,
			RepositoryUsers serviceUser, PasswordEncoder passwordEncoder) {
		return args -> {
			if (servicePermission.count() > 0 && serviceRol.count() > 0 && serviceUser.count() > 0) {
				return;
			}
			Permissions createPermission = Permissions.builder()
					.name("CREATE")
					.build();

			Permissions readPermission = Permissions.builder()
					.name("READ")
					.build();

			Permissions updatePermission = Permissions.builder()
					.name("UPDATE")
					.build();

			Permissions deletePermission = Permissions.builder()
					.name("DELETE")
					.build();

			servicePermission.saveAll(List.of(createPermission, readPermission, updatePermission, deletePermission));

			// Create roles
			Roles adminRole = Roles.builder()
					.name("ADMIN")
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();
			Roles client = Roles.builder()
					.name("CLIENT")
					.permissions(Set.of(createPermission, readPermission, updatePermission))
					.build();
			Roles seller = Roles.builder()
					.name("SELLER")
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();
			serviceRol.saveAll(List.of(client, seller, adminRole));

			// Create admin user
			String passwordHashed = passwordEncoder.encode("admin");
			Users adminUser = Users.builder()
					.username("admin")
					.password(passwordHashed)
					.email("angelxd0714@gmail.com")
					.userId(1007648218L)
					.roles(Set.of(adminRole))
					.isEnabled(true)
					.build();

			serviceUser.save(adminUser);
		};
	}
	// @PostConstruct
	// public void runScript() {
	// try {
	// Process process = Runtime.getRuntime().exec("./create_s3_bucket.sh");
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(process.getInputStream()));
	// String line;
	// while ((line = reader.readLine()) != null) {
	// System.out.println(line);
	// }
	// process.waitFor();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
