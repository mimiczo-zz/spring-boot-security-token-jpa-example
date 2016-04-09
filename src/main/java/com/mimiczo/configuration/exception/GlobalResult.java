package com.mimiczo.configuration.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@EqualsAndHashCode(callSuper=false)
public class GlobalResult implements Serializable {

	private int status = 200;
	private String code = "NAP_E999";
	private String message = "success";
	private String url;
	private String result;

	public GlobalResult() {}
    public GlobalResult(String url, String code, int status, String message) {
    	this.url = url;
    	this.code = code;
    	this.status = status;
    	this.message = message;
    }
}
