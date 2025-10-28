package resol.DacundaS.dto;

public class EmpleadoDTO {
    private Integer idEmpleado;
    private String  nombreCompleto;
    private Integer antiguedad;
    private Integer bonificacion;
    private String  localidad;

    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public Integer getAntiguedad() { return antiguedad; }
    public void setAntiguedad(Integer antiguedad) { this.antiguedad = antiguedad; }
    public Integer getBonificacion() { return bonificacion; }
    public void setBonificacion(Integer bonificacion) { this.bonificacion = bonificacion; }
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    @Override public String toString() {
        return "EmpleadoDTO{id=%d, nombre='%s', antig=%d, bono=%d%%, loc='%s'}"
                .formatted(idEmpleado, nombreCompleto, antiguedad, bonificacion, localidad);
    }
}