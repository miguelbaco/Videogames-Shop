package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.ProductoDTO;

public interface JuegoService {

	public List<Producto> allJuegos();

	public Producto save(Producto producto);

	public Optional<Producto> findById(Long id);

	public List<Producto> juegosByCategoria(int idcategoria);

	public List<Valoracion> valoracionesJuego(Producto producto);
	
	public Valoracion saveValoracion(Valoracion valoracion);

	public void deleteJuego(Producto producto);

	public void updateJuego(ProductoDTO productoDTO);

	public void subirImagen(MultipartFile file) throws FileUploadException;
}
