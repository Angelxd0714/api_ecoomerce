package com.ecommerce.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.api.persistence.entities.Permissions;
import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.repository.RepositoryPermissions;
import com.ecommerce.api.persistence.repository.RepositoryRoles;
import com.ecommerce.api.persistence.repository.RepositoryUsers;
import java.util.*;
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	@Bean
    CommandLineRunner init(RepositoryPermissions servicePermission, RepositoryRoles serviceRol, RepositoryUsers serviceUser, PasswordEncoder passwordEncoder,MongoTemplate mongoTemplate) {
        return args -> {
			mongoTemplate.dropCollection("users");  // Elimina la colección si existe
			mongoTemplate.dropCollection("roles");
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
                     


					servicePermission.saveAll(List.of(createPermission,readPermission,updatePermission,deletePermission));

					Roles adminRole = Roles.builder()
					.name("ADMIN")
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();
					Roles client = Roles.builder()
					.name("CLIENT")
					.permissions(Set.of(createPermission, readPermission,updatePermission))
					.build();
					Roles seller = Roles.builder()
					.name("SELLER")
					.permissions(Set.of(createPermission, readPermission, updatePermission,deletePermission))
					.build();
					serviceRol.saveAll(List.of(client,seller,adminRole));
                    String passwordHashed = passwordEncoder.encode("admin");
					Users adminUser = Users.builder()
					.username("admin")
					.password(passwordHashed)
					.roles(Set.of(adminRole))
					.build();


					serviceUser.save(adminUser);
			
		
			


		

        };
    }

}
