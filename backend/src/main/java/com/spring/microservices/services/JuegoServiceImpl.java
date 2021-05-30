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

		/* Actualizo el juego con los cambios del DTO */
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

	/* Este método es el que coge el archivo de imagen y lo guarda en assets */
	@Override
	public void subirImagen(MultipartFile file) throws FileUploadException {

		try {
			/* uploadPath recoge de la variable del properties la ruta donde guardar la imagen  */
			Path root = Paths.get(uploadPath);
			//Se especifica la ruta que va a tener el archivo con su nombre en el sistema
			Path resolve = root.resolve(file.getOriginalFilename());
			if (resolve.toFile().exists()) { // Aqui comprueba que no se haya subido ya
				throw new FileUploadException("File already exists: " + file.getOriginalFilename());
			}
			// Aquí el método copy de Files se encarga de coger el archivo y guardarlo en la ruta con su nombre
			Files.copy(file.getInputStream(), resolve);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}

	}

}
