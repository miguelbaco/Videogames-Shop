package com.spring.microservices.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	PedidoRepository repository;
	
	@Autowired
	DetallePedidoService detallePedidoService;

	@Override
	public Optional<Pedido> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado) {

		return repository.findByIdUsuarioAndComprado(idUsuario, comprado);
	}

	public Pedido save(Long idUsuario) {

		Pedido pedido = new Pedido();
		pedido.setIdUsuario(idUsuario);
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String fecha = date.format(formatter);
		pedido.setFecha(fecha);
		return repository.save(pedido);
	}

	public void realizarCompra(Pedido pedido, Usuario usuario) throws MessagingException {

		pedido.setComprado(true);
		LocalDate date = LocalDate.now(); // Con datetimeformatter digo la estructura del string
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String fecha = date.format(formatter);
		pedido.setFecha(fecha);
		repository.save(pedido);
		
		detallePedidoService.sendEmail(usuario,pedido); // Aqui se recogen los datos y se envían por correo
	}

	public List<Pedido> pedidosEnCamino(Long idUsuario) {

		List<Pedido> pedidos = repository.findByIdUsuario(idUsuario);
		List<Pedido> pedidosfinal = new ArrayList<Pedido>();

		for (Pedido pedido : pedidos) {
			if (pedido.isComprado()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate fechaPedido = LocalDate.parse(pedido.getFecha(), formatter);
				LocalDate fechaActual = LocalDate.now();
				// Con las dos fechas y el siguiente método, recojo la dierencia de días entre las fechas
				long diferenciaDias = ChronoUnit.DAYS.between(fechaPedido, fechaActual);

				if (diferenciaDias < 3) { // El pedido sera recibido el 3er día
					pedidosfinal.add(pedido);
				}
			}
		}
		return pedidosfinal;
	}

	public List<Pedido> pedidosEnDevolucion(Long idUsuario) {

		List<Pedido> pedidos = repository.findByIdUsuario(idUsuario);
		List<Pedido> pedidosfinal = new ArrayList<Pedido>();

		for (Pedido pedido : pedidos) {
			if (pedido.isComprado()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate fechaPedido = LocalDate.parse(pedido.getFecha(), formatter);
				LocalDate fechaActual = LocalDate.now();
				// Con las dos fechas y el siguiente método, recojo la dierencia de días entre las fechas
				long diferenciaDias = ChronoUnit.DAYS.between(fechaPedido, fechaActual);

				if (diferenciaDias > 2 && diferenciaDias <= 15) { // Ha sido recibido y fue hecha antes de los 15 dias
					pedidosfinal.add(pedido);
				}
			}
		}
		return pedidosfinal;
	}

	public List<Pedido> pedidosSinDevolucion(Long idUsuario) {
		List<Pedido> pedidos = repository.findByIdUsuario(idUsuario);
		List<Pedido> pedidosfinal = new ArrayList<Pedido>();

		for (Pedido pedido : pedidos) {
			if (pedido.isComprado()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate fechaPedido = LocalDate.parse(pedido.getFecha(), formatter);
				LocalDate fechaActual = LocalDate.now();
				// Con las dos fechas y el siguiente método, recojo la dierencia de días entre las fechas
				long diferenciaDias = ChronoUnit.DAYS.between(fechaPedido, fechaActual);

				if (diferenciaDias > 15) { // Son 15 pero es para probar
					pedidosfinal.add(pedido);
				}
			}
		}
		return pedidosfinal;
	}

}
