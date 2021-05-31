package com.spring.microservices.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

	@Autowired
	DetallePedidoRepository repository;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${upload.path}")
	private String uploadPath;

	public DetallePedido save(DetallePedido detallePedido) {
		return repository.save(detallePedido);
	}

	public List<DetallePedido> findByIdPedido(Long idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		return repository.findByPedido(pedido);
	}

	public Optional<DetallePedido> findByPedidoAndProducto(Pedido pedido, Producto producto) {
		return repository.findByPedidoAndProducto(pedido, producto);
	}

	@Transactional
	public void delete(DetallePedido detallePedido) {
		repository.delete(detallePedido);
	}

	public List<Producto> recogerCarrito(Long idPedido) {

		List<DetallePedido> detalles = findByIdPedido(idPedido);

		/*Con este for compruebo el stock de los productos para bajar o eliminar los
		 * detalles según la cantidad de stock que haya conforme a la cantidad del detallePedido */
		for (DetallePedido detalle : detalles) {
			if (detalle.getProducto().getStock() < detalle.getCantidad()) {
				if (detalle.getProducto().getStock() == 0) {
					delete(detalle);
				} else {
					detalle.setCantidad(detalle.getProducto().getStock());
					save(detalle);
				}
			}
		}

		/* Ahora devuelvo los productos con las modificaciones hechas y lo devuelvo */
		List<DetallePedido> detallesdefinitivo = findByIdPedido(idPedido);
		List<Producto> productos = new ArrayList<Producto>();
		for (DetallePedido detalle : detallesdefinitivo) {
			for (int i = 0; i < detalle.getCantidad(); i++) {
				productos.add(detalle.getProducto());
			}
		}

		return productos;
	}

	public void devolverDetalleProducto(DetallePedido detallePedido) {
		detallePedido.setDevuelto(true); // Cambio a true el detallePedido.devuelto
		repository.save(detallePedido);
	}
	
	public void sendEmail(Usuario usuario, Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage(); //Este me permite con el true del
		//helper abajo insertar cualquier tipo de archivo y aparte mandarlo con formato html
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		// inserto datos necesarios de quien lo envia a quien lo envia y el mensaje contenedor
		helper.setTo(usuario.getEmail());
		helper.setSubject("Compra realizada en Gamepoint");
		helper.setFrom("m.a.bacomorillo@gmail.com");
		String htmlMsg = "<h1>Gracias por tu compra, "+ usuario.getNombre() + "!</h1>"
				+ "<h2>Has realizado una compra a la fecha de " + pedido.getFecha() +" con los siguientes pedidos:</h2><br/>";
		List<DetallePedido> detallesPedido = findByIdPedido(pedido.getId());
		//Recorro los detallesPedidos para insertarlos en el mensaje
		int i = 0;
		for(DetallePedido detalle : detallesPedido) {
			htmlMsg += "<div style='margin-bottom:300px;'>"
	                  + "<img src='cid:imagen" + i + "' style='float:right;width:192px;height:291px;'/>"
	                  + "<div><h3>Nombre del producto: <strong>" + detalle.getProducto().getNombre() + "</strong></h3></div>"
	                  + "<div><h3>Descripcion del producto: <strong>" + detalle.getProducto().getDescripcion() + "</strong></h3></div>"
	                  + "<div><h3>Cantidad: <strong>" + detalle.getCantidad() + "</strong></h3></div>"
                  + "</div>";
			i++;
		}
		htmlMsg += "<div><h2>Gracias por su compra, " + usuario.getNombre() + " " + usuario.getApellidos() + ".</h2></div>";
		helper.setText(htmlMsg, true);
		
		// Vuelvo a recorrer y asigno la imaghen con su correspondiente src (imagen[i])
		i = 0;
		for(DetallePedido detalle : detallesPedido) {
			helper.addInline("imagen" + i,
	                new File(uploadPath + "/" + detalle.getProducto().getImagen()));
			i++;
		}
		
	
		mailSender.send(mimeMessage);
    }
}
