package br.com.deveficiente.bolaoapi.services.user.core;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
}
