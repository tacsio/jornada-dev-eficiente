package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.model.Image;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.model.data.ImageRepository;
import io.tacsio.mercadolivre.model.data.ProductRepository;
import io.tacsio.mercadolivre.service.storage.StorageService;
import io.tacsio.mercadolivre.validation.Exists;
import io.tacsio.mercadolivre.validation.ProductOwner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@RestController
public class ImageController {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public ImageController(ProductRepository productRepository, ImageRepository imageRepository, StorageService storageService) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.storageService = storageService;
    }

    @PutMapping("/products/{id}/images")
    @Transactional
    public ResponseEntity store(@AuthenticationPrincipal User owner,
                                @PathVariable("id") @Exists(entityClass = Product.class)
                                @ProductOwner Long productId,
                                @RequestBody List<MultipartFile> images) {

        var product = productRepository.getOne(productId);

        var productImages = images.stream()
                .map(storageService::store)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        product.setImages(productImages);

        return ResponseEntity.ok().build();
    }

    //from local storage
    @GetMapping(value = "/images/{hash}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    ResponseEntity image(@PathVariable("hash")
                         @Exists(entityClass = Image.class) String id) throws IOException {

        var image = imageRepository.getOne(id.trim());

        var resource = new FileSystemResource(image.getLocation());
        var bytes = StreamUtils.copyToByteArray(resource.getInputStream());

        var headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
