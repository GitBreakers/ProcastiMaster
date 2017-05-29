/**
 * Copyright (C) 2017 Xillio GitBreakers (GitBreakers@xillio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.xillio.gitbreakers.procrastimaster.server.services;

import nl.xillio.gitbreakers.procrastimaster.server.model.entity.User;
import nl.xillio.gitbreakers.procrastimaster.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;

@Service
public class UserService extends AbstractService<User, UserRepository> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User entity, User owner) {
        // This user is a new user, so we encrypt the password.
        if (entity.getId() == null) {
            if(entity.getPassword() == null) {
                throw new IllegalArgumentException("There is no password provided.");
            }

            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        getRepository().save(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getRepository().findByEmailIgnoreCase(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User getUser(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Object userDetails = authentication.getPrincipal();
            if(userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
                return customUserDetails.user;
            }
        }

        throw new IllegalArgumentException();
    }

    private static class CustomUserDetails extends org.springframework.security.core.userdetails.User {

        private final User user;

        public CustomUserDetails(User user) {
            super(user.getEmail(), user.getPassword(), Collections.emptyList());

            this.user = user;
        }

    }

    public User getUser(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Object userDetails = authentication.getPrincipal();
            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
                return customUserDetails.user;
            }
        }

        throw new IllegalArgumentException();
    }

    private static class CustomUserDetails extends org.springframework.security.core.userdetails.User {

        private final User user;

        public CustomUserDetails(User user) {
            super(user.getEmail(), user.getPassword(), Collections.emptyList());

            this.user = user;
        }

    }
}

