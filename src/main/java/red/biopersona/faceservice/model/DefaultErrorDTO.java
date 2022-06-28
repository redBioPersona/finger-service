package red.biopersona.faceservice.model;

import java.io.Serializable;

import red.biopersona.faceservice.util.ErrorEnum;


/**
 * @author MINSAIT
 * La clase ErrorBean, usada para indicar los errores que sucedieron durante la ejecucion.
 */
public class DefaultErrorDTO implements Serializable {

    /** 
     * Variable para serializar la clase.
     */
    private static final long serialVersionUID = 1L;

	/**
	 * La variable code.
	 */
    private String code;
    
    /**
     *  La variable message.
     */
    private String message;

    /**
     * La variable level.
     */
    private String level;

    /**
     * La variable description.
     */
    private String description;
    
    /**
     * La variable more info.
     */
    private String moreInfo;
    
    /**
     * Constructor que recibe el objeto del enumerador de mensajes, con la opcion de agregar mas detalle usando el campo moreInfo
     * 
     * @param errorEnum Objeto del enumerador de mensajes
     * @param moreInfo Un link a la documentacion del codigo de error.
     * @param description descripción del error, en el caso que pongan dentro del codigo.
     */
    public DefaultErrorDTO(ErrorEnum errorEnum, String moreInfo, String description) {
		this.code = errorEnum.getCode();
		this.message = moreInfo;
		this.level = errorEnum.getLevel();
		this.description = description;
		this.moreInfo = errorEnum.getMessage();
	}

    /**
     * Constructor que recibe todos los parametros del enumerador de mensajes
     * @param code Un codigo de error unico, el cual pueda ser identificado y localizado para mas detalles. Debe ser human readable, por tanto no deberia ser un codigo numerico, sino alfanumerico.
     * @param message Un mensaje de error claro: Por consideraciones de seguridad estos mensajes de error no deben contener informacion interna que pudiera implicar un riesgo a la seguridad e integridad.
     * @param level  Un nivel de error: info, warning, error.
     * @param description Una descripcion detallada.
     * @param moreInfo Un link a la documentacion del codigo de error.
     */
    public DefaultErrorDTO(String code, String message, String level, 
    		String description, String moreInfo) {
		this.code = code;
		this.message = message;
		this.level = level;
		this.description = description;
		this.moreInfo = moreInfo;
	}

    /**
     * Constructor que recibe el objeto del enumerador de mensajes
     * @param errorEnum Objeto del enumerador de mensajes
     */
    public DefaultErrorDTO(ErrorEnum errorEnum) {
		this.code = errorEnum.getCode();
		this.message = errorEnum.getMessage();
		this.level = errorEnum.getLevel();
		this.description = errorEnum.getDescription();
		this.moreInfo = errorEnum.getMoreInfo();
	}

    /**
     * Constructor que recibe el objeto del enumerador de mensajes, con la opcion de agregar mas detalle usando el campo moreInfo
     * @param errorEnum Objeto del enumerador de mensajes
     * @param moreInfo Un link a la documentacion del codigo de error.
     */
    public DefaultErrorDTO(ErrorEnum errorEnum, String moreInfo) {
		this.code = errorEnum.getCode();
		this.message = errorEnum.getMessage();
		this.level = errorEnum.getLevel();
		this.description = errorEnum.getDescription();
		this.moreInfo = moreInfo;
	}

    /**
     * Obtiene el valor de la variable code.
     * @return el code
     */
    public String getCode() {
        return code;
    }

    /**
     * Obtiene el valor de la variable message.
     * @return el message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtiene el valor de la variable nivel.
     * @return el level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Obtiene el valor de la variable description.
     * @return el description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el valor de la variable more info.
     * @return el more info
     */
    public String getMoreInfo() {
        return moreInfo;
    }
}
