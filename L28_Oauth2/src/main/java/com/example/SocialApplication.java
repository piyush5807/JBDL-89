/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialApplication {

    /**
     * Oauth 2.0 security paradigm states that authentication should be handled some third party service and access control
     * is the only sole responsibility of this application that you are building
     * @param args
     *
     * User -> implements UserDetails
     * Oauth2User -->
     *
     * SecurityContext.getAuthentication.getPrincipal();
     */

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

}
