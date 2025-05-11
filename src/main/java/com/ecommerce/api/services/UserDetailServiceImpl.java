package com.ecommerce.api.services;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.PermissionDTO;
import com.ecommerce.api.dto.response.RolesDTO;
import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.persistence.interfaces.CrudUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.api.dto.request.RequestCreateUser;
import com.ecommerce.api.dto.request.RequestLogin;
import com.ecommerce.api.dto.response.Response;
import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.repository.RepositoryRoles;
import com.ecommerce.api.persistence.repository.RepositoryUsers;
import com.ecommerce.api.utils.JWTutils;

@Service
public class UserDetailServiceImpl implements UserDetailsService, CrudUsers {
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private JWTutils jwtUtils;
    @Autowired
    private RepositoryRoles roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userEntity = repositoryUsers.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        userEntity.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(),
                userEntity.isAccountNoExpired(), userEntity.isCredentialNoExpired(), userEntity.isAccountNoLocked(),
                authorityList);
    }

    public Response createUser(RequestCreateUser createRoleRequest) {

        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        String email = createRoleRequest.email();
        List<String> rolesRequest = createRoleRequest.roles().rolesName();
        Set<Roles> roleEntityList = new HashSet<>(roleRepository.findRolesByName(rolesRequest));
        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        Users userEntity = Users.builder().username(username).email(email).password(passwordEncoder.encode(password))
                .roles(roleEntityList).isEnabled(true).accountNoLocked(true).accountNoExpired(true)
                .credentialNoExpired(true).build();

        Users userSaved = repositoryUsers.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        userSaved.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);
        String role = userSaved.getRoles().stream()
                .map(Roles::getName)
                .collect(Collectors.joining(","));

        return new Response(username, "User created successfully", accessToken, true, role);
    }

    public Response loginUser(RequestLogin authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        String[] role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5)) // Remove "ROLE_" prefix
                .toArray(String[]::new);

        return new Response(username, "User logged in successfully", accessToken, true, role[0]);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public UsersDTO getUserById(Long id) {
        UsersDTO usersDTO = repositoryUsers.findById(id).map(user -> UsersDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .isEnabled(user.isEnabled())
                .accountNoExpired(user.isAccountNoExpired())
                .accountNoLocked(user.isAccountNoLocked())
                .address(user.getAddress())
                .credentialNoExpired(user.isCredentialNoExpired())
                .build()).orElse(null);
        if (usersDTO == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        return usersDTO;

    }

    @Override
    public UsersDTO getUserByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }
        if (email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser vacio");
        }

        return repositoryUsers.findByEmail(email).map(user -> UsersDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .isEnabled(user.isEnabled())
                .accountNoExpired(user.isAccountNoExpired())
                .accountNoLocked(user.isAccountNoLocked())
                .address(user.getAddress())
                .credentialNoExpired(user.isCredentialNoExpired())
                .build()).orElse(null);

    }

    @Override
    public UsersDTO getUserIdent(Long userId) {
        return repositoryUsers.findByUserId(userId).map(user -> UsersDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .isEnabled(user.isEnabled())
                .accountNoExpired(user.isAccountNoExpired())
                .accountNoLocked(user.isAccountNoLocked())
                .address(user.getAddress())
                .credentialNoExpired(user.isCredentialNoExpired())
                .build()).orElse(null);
    }

    @Override
    public void updateUser(UserRequest user, Long id) {
        Users existingUser = repositoryUsers.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar campos básicos (como antes)
        if (user.getFullName() != null)
            existingUser.setFullName(user.getFullName());
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getPhone() != null)
            existingUser.setPhone(user.getPhone());
        if (user.getAddress() != null)
            existingUser.setAddress(user.getAddress());
        if (user.getUserId() != null)
            existingUser.setUserId(user.getUserId());

        // Actualizar roles (creándolos si no existen)
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Roles> rolesSet = user.getRoles().stream()
                    .map(roleRequest -> {
                        // Buscar el rol por nombre
                        Optional<Roles> existingRole = roleRepository.findByName(roleRequest.getName());

                        // Si existe, retornarlo
                        if (existingRole.isPresent()) {
                            return existingRole.get();
                        } else {
                            // Si no existe, crear y guardar el nuevo rol
                            Roles newRole = Roles.builder()
                                    .name(roleRequest.getName())
                                    .build();
                            return roleRepository.save(newRole); // Guarda el rol automáticamente
                        }
                    })
                    .collect(Collectors.toSet());

            existingUser.setRoles(rolesSet);
        }

        repositoryUsers.save(existingUser); // Guardar el usuario actualizado
    }

    @Override
    public void deleteUser(Long id) {
        repositoryUsers.deleteById(id);
    }

    @Override
    public List<UsersDTO> getAllUsers() {

        return repositoryUsers.findAll()
                .stream()
                .map(user -> UsersDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .phone(user.getPhone())
                        .isEnabled(user.isEnabled())
                        .accountNoExpired(user.isAccountNoExpired())
                        .accountNoLocked(user.isAccountNoLocked())
                        .address(user.getAddress())
                        .rolesDTOS(
                                user.getRoles().stream().map(role -> RolesDTO.builder()
                                        .id(role.getId())
                                        .name(role.getName())
                                        .permissions(
                                                role.getPermissions().stream().map(permission -> PermissionDTO.builder()
                                                        .id(permission.getId())
                                                        .name(permission.getName())
                                                        .build()).collect(Collectors.toSet()))
                                        .build()).collect(Collectors.toSet()))

                        .credentialNoExpired(user.isCredentialNoExpired())
                        .build())
                .toList();
    }

}
