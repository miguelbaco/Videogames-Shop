package com.spring.microservices.entity.dto;

import org.slf4j.Logger;

public class ErrorDTO {

	private String id;
	private String status;
	private String code;
	private String title;
	private String details;
	public static final String CODE_ERROR_JUEGOS = "ERROR-JUEGOS";
	public static final String CODE_ERROR_JUEGO = "ERROR-JUEGO";

	public ErrorDTO() {

	}

	public ErrorDTO(String id, String status, String code, String title, String details) {

		this.id = id;
		this.status = status;
		this.code = code;
		this.title = title;
		this.details = details;
	}

	public static ErrorDTO creaErrorLogger(String id, int status, String code, String title, String details,
			Logger log) {

		ErrorDTO errorDTO = new ErrorDTO();
		id = id.concat(String.valueOf(System.currentTimeMillis()));
		errorDTO.setId(id);
		errorDTO.setStatus(String.valueOf(status));
		errorDTO.setCode(code);
		errorDTO.setTitle(title);
		errorDTO.setDetails(details);
		log.error("{}-{}", id, details);
		return errorDTO;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getDetails() {

		return details;
	}

	public void setDetails(String details) {

		this.details = details;
	}

}