package com.mimiczo.configuration.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimiczo.configuration.exception.GlobalException;
import com.mimiczo.configuration.exception.GlobalResult;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * Response Exception handler
	 * class , biz 별로 예외처리 추가구성
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.debug("{}", "handleException Occured:: URL=" + request.getRequestURL()
                + "\n" + ex.getMessage());
		return makeErrorResponse(request, response, "DEF_E400", HttpStatus.BAD_REQUEST);
    }

	/**
     * HttpMediaTypeNotAcceptableException return 형식에 Object 가 정상적인 parse 가 안되는 현상
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { 	HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class })
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleMediaType(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleMediaType Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        //return as JSON
        GlobalResult result = makeErrorResponse(request, response, "DEF_E406", HttpStatus.NOT_ACCEPTABLE);
        String json = "";
        try {json = new ObjectMapper().writeValueAsString(result);} catch (JsonProcessingException e) {}
        return json;
    }

    /**
     * DAO Access exception
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { DataAccessException.class, TransactionException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public GlobalResult handleDao(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleDao Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E550", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Mysql Syntax Query Execute exception
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { MySQLSyntaxErrorException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public GlobalResult handleMysqlSyntax(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleMysqlSyntax Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E552", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * IO exception
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public GlobalResult handleIO(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleIO Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E553", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * CONFLICT exception
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ResponseBody
    public GlobalResult handleConflict(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("handleConflict Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E409", HttpStatus.CONFLICT);
    }

    /**
     * JSON data parse 오류
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { JsonParseException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleJsonParse(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleJsonParse Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E451", HttpStatus.BAD_REQUEST);
    }

    /**
     * 로직에서 임의로 발생시키는 오류 : 400
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { GlobalException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleRestCustom(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleRestCustom Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        String code = "DEF_E900";
        if (ex.getMessage().startsWith("NME_")) {
            GlobalException restException = (GlobalException)ex;
            StringBuilder result = new StringBuilder();
            try{result.append(messageSourceAccessor.getMessage("fail.code."+ex.getMessage(), new String[]{restException.getField()}));}catch(NoSuchMessageException nse) {}
            log.debug("{}", "handleRestCustom result:[" + result + "] , fieldname:["+restException.getField()+"]");
            return makeErrorResponse(request, response, ex.getMessage(), HttpStatus.BAD_REQUEST, result.toString());
        }
        return makeErrorResponse(request, response, code, HttpStatus.BAD_REQUEST);
    }

    /**
     * NullPointerException Exception handler
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { NullPointerException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleNullPointer(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleNullPointer Occured:: URL=" + request.getRequestURL()
                + "\n" + ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E453", HttpStatus.BAD_REQUEST);
    }

    /**
     * Valid 검증 오류
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleBind(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        BindingResult bindingResult = (BindingResult)ex;
        log.debug("{}", "handleBind Occured:: URL="+request.getRequestURL()+", handleBind result:[" + bindingResult.getFieldError().getDefaultMessage() + "] , fieldname:["+bindingResult.getFieldError().getField()+"]");
        return makeErrorResponse(request, response, "DEF_E454", HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
    }

    /**
     * @RequestParam 필수파라미터 null 오류
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResult handleRequestParam(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}", "handleRequestParam Occured:: URL="+request.getRequestURL()
                +"\n"+ex.getMessage());
        return makeErrorResponse(request, response, "DEF_E455", HttpStatus.BAD_REQUEST);
    }

    /**
     * JSON 형태의 Exception Value Object Creation
     * @param request
     * @param code
     * @param status
     * @return
     */
	private GlobalResult makeErrorResponse(HttpServletRequest request, HttpServletResponse response, String code, HttpStatus status) {
		response.setHeader("RESPONSE_NME", code);
		return new GlobalResult(request.getRequestURL().toString(), code, status.value(), status.name());
	}
	private GlobalResult makeErrorResponse(HttpServletRequest request, HttpServletResponse response, String code, HttpStatus status, String message) {
		response.setHeader("RESPONSE_NME", code);
		return new GlobalResult(request.getRequestURL().toString(), code, status.value(), message);
	}
}
