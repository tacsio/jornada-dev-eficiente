package io.tacsio.mercadolivre.service;

import io.tacsio.mercadolivre.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.storage.location}")
    private String storageLocation;

    @Override
    public URI retrieve(Image image) {
        return URI.create(buildLink(image.getId().toString()));
    }

    private String buildLink(String hash) {
        var basePath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return String.format("%s/images/%s", basePath, hash);
    }

    @Override
    public Image store(MultipartFile file) {
        var path = Paths.get(storageLocation);
        var name = String.format("%s_%s", System.currentTimeMillis(), file.getOriginalFilename());

        try {
            file.transferTo(path.resolve(name));
            var location = path.resolve(name).toUri().getPath();
            var image = new Image(file.getOriginalFilename(), location);

            var uri = buildLink(image.getId());
            image.setUri(uri);

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
