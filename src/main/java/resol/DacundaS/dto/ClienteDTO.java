package resol.DacundaS.dto;

public class ClienteDTO {
    private String idCliente;
    private String nombreCompleto;
    private String nombreEmpresa;
    private String tipoEmpresa;         // empresa_tipo.nombre (JOIN)
    private String localidad;           // localidad.nombre (JOIN)

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public String getTipoEmpresa() { return tipoEmpresa; }
    public void setTipoEmpresa(String tipoEmpresa) { this.tipoEmpresa = tipoEmpresa; }
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    @Override public String toString() {
        return ("ClienteDTO{id='%s', nombre='%s', empresa='%s', tipo='%s', loc='%s'}")
                .formatted(idCliente, nombreCompleto, nombreEmpresa, tipoEmpresa, localidad);
    }
}