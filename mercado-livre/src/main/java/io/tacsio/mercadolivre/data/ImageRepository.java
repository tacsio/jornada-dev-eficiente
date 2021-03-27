package io.tacsio.mercadolivre.data;

import io.tacsio.mercadolivre.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
