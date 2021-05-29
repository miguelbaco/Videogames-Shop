package com.spring.microservices.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.ProductoDTO;
import com.spring.microservices.repository.JuegoRepository;
import com.spring.microservices.repository.ValoracionRepository;

@Service
public class JuegoServiceImpl implements JuegoService {

	@Autowired
	JuegoRepository repository;

	@Autowired
	ValoracionRepository valoracionRepository;
	
	@Value("${upload.path}")
    private String uploadPath;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> allJuegos() {

		return repository.findAll();
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {

		return repository.save(producto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {

		return repository.findById(id);
	}

	@Override
	public List<Producto> juegosByCategoria(int idcategoria) {

		return repository.findByIdCategoria(Long.valueOf(idcategoria));
	}

	@Override
	public List<Valoracion> valoracionesJuego(Producto producto) {

		return valoracionRepository.findByProducto(producto);
	}

	@Override
	public Valoracion saveValoracion(Valoracion valoracion) {
		
		return valoracionRepository.save(valoracion);
	}

	@Override
	public void deleteJuego(Producto producto) {
		
		producto.setStock(0);
		repository.save(producto);
	}

	@Override
	public void updateJuego(ProductoDTO productoDTO) {

		Producto producto = repository.findById(Long.valueOf(productoDTO.getId()))
				.orElseThrow(NoSuchElementException::new);
		producto.setDescripcion(productoDTO.getDescripcion());
		producto.setIdcategoria(Long.valueOf(productoDTO.getIdcategoria()));
		producto.setImagen(productoDTO.getImagen());
		producto.setNombre(productoDTO.getNombre());
		producto.setPrecio(productoDTO.getPrecio());
		producto.setStock(productoDTO.getStock());

		repository.save(producto);
	}

	@Override
	public void subirImagen(MultipartFile file) throws FileUploadException {
		
		try {
            Path root = Paths.get(uploadPath);
            Path resolve = root.resolve(file.getOriginalFilename());
            if (resolve.toFile()
                       .exists()) {
                throw new FileUploadException("File already exists: " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
		
	}

}
