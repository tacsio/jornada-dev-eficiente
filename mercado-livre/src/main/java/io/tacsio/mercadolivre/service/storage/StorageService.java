package io.tacsio.mercadolivre.service.storage;

import io.tacsio.mercadolivre.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface StorageService {
    URI retrieve(Image image);

    Image store(MultipartFile image);
}
