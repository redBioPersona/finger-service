package red.biopersona.faceservice.enumeration;

/***
 * Enum con los tipos de error en el formulario de login
 * 
 * @author Omar
 *
 */
public enum GenericDataEnum {
    GENERIC_MSJ_SALIR("Salir"), GENERIC_IMG_ERROR("/imgs/generic_error.svg");

    // Variable para obtener el msg
    private final String titulo;
    
    /**
     * Constructor del enul
     * 
     * @param titulo del modal
     */
    GenericDataEnum(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * Metodo que obtiene el titulo
     * 
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

}