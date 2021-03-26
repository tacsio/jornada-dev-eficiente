package io.tacsio.mercadolivre.service.storage;

import io.tacsio.mercadolivre.model.Image;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
@Profile("prod")
public class S3StorageService implements StorageService {
    @Override
    public URI retrieve(Image image) {
        return null;
    }

    @Override
    public Image store(MultipartFile image) {
        return null;
    }
}
