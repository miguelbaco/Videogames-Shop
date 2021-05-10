package com.spring.microservices.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {

	private Object data;
	private List<ErrorDTO> error;
	private Object meta;

	public ResponseDTO() {
		setError(new ArrayList<>());
		setMeta(LocalDateTime.now().toString());
	}

	public Object getData() {

		return data;
	}

	public void setData(Object data) {

		this.data = data;
	}
	
	public List<ErrorDTO> getError() {

		return error;
	}

	public void setError(List<ErrorDTO> error) {

		this.error = error;
	}

	public Object getMeta() {

		return meta;
	}

	public void setMeta(Object meta) {

		this.meta = meta;
	}

}
