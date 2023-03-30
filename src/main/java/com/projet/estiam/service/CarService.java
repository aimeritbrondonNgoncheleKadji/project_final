package com.projet.estiam.service;

import com.projet.estiam.model.ByteArrayMultipartFile;
import com.projet.estiam.model.Car;
import com.projet.estiam.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car saveCar(Car car, MultipartFile file) throws IOException {

        saveImage(file, car);
        return carRepository.save(car);
    }

    public Car save(Car car) throws IOException {
        return carRepository.save(car);
    }

    public Optional<Car> findById(long id) {
        return carRepository.findById(id);
    }

    public void delete(Long id){
        carRepository.deleteById(id);
    }
    public Page<Car> findAll(Pageable paging){
        return carRepository.findAll(paging);
    }

    public Car findByMakeAndModel(String make, String model) {
        return carRepository.findByMakeAndModel(make, model);
    }

    private static final String UPLOAD_DIR = "uploads/car";

    public void saveImage(MultipartFile file, Car car) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
            throw new IllegalArgumentException("Invalid file format, only JPG, JPEG, and PNG are allowed");
        }
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File targetFile = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        file.transferTo(targetFile);
        car.setImage(fileName);
    }

    public Resource loadImage(String imageName) throws FileNotFoundException, MalformedURLException {
        File file = new File(UPLOAD_DIR + File.separator + imageName);
        Resource resource = new UrlResource(file.toURI());
        if (!resource.exists()) {
            throw new FileNotFoundException("Image not found: " + imageName);
        }
        return resource;
    }

    public MultipartFile createMultipartFileFromClasspath() throws IOException {
        ClassPathResource resource = new ClassPathResource("car.png");
        byte[] content = StreamUtils.copyToByteArray(resource.getInputStream());
        String contentType = Files.probeContentType(resource.getFile().toPath());
        String originalFilename = resource.getFilename();

        return new ByteArrayMultipartFile(content, "file", originalFilename, MediaType.parseMediaType(contentType));
    }

}
