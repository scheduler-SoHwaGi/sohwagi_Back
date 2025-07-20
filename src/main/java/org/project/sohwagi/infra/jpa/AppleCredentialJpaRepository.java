package org.project.sohwagi.infra.jpa;

import org.project.sohwagi.domain.AppleCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleCredentialJpaRepository extends JpaRepository<AppleCredential, Long> {

}
