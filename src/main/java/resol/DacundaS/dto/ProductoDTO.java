package resol.DacundaS.dto;

import java.math.BigDecimal;

public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal precioUnidad;
    private Integer unidadesEnExistencia;
    private Integer nivelNuevoPedido;
    private Boolean suspendido;

    private Boolean disponible;
    private Boolean necesitaReposicion;

    public ProductoDTO() {}

    public ProductoDTO(Integer idProducto, String nombreProducto, BigDecimal precioUnidad,
                       Integer unidadesEnExistencia, Integer nivelNuevoPedido, Boolean suspendido) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioUnidad = precioUnidad;
        this.unidadesEnExistencia = unidadesEnExistencia;
        this.nivelNuevoPedido = nivelNuevoPedido;
        this.suspendido = suspendido;
        calcularEstado();
    }

    public void calcularEstado() {
        this.disponible = !Boolean.TRUE.equals(suspendido);
        this.necesitaReposicion = unidadesEnExistencia != null && nivelNuevoPedido != null
                && unidadesEnExistencia <= nivelNuevoPedido;
    }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public BigDecimal getPrecioUnidad() { return precioUnidad; }
    public void setPrecioUnidad(BigDecimal precioUnidad) { this.precioUnidad = precioUnidad; }

    public Integer getUnidadesEnExistencia() { return unidadesEnExistencia; }
    public void setUnidadesEnExistencia(Integer unidadesEnExistencia) { this.unidadesEnExistencia = unidadesEnExistencia; }

    public Integer getNivelNuevoPedido() { return nivelNuevoPedido; }
    public void setNivelNuevoPedido(Integer nivelNuevoPedido) { this.nivelNuevoPedido = nivelNuevoPedido; }

    public Boolean getSuspendido() { return suspendido; }
    public void setSuspendido(Boolean suspendido) { this.suspendido = suspendido; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Boolean getNecesitaReposicion() { return necesitaReposicion; }
    public void setNecesitaReposicion(Boolean necesitaReposicion) { this.necesitaReposicion = necesitaReposicion; }

    @Override
    public String toString() {
        return "ProductoDTO{id=" + idProducto +
                ", nombre='" + nombreProducto + '\'' +
                ", precio=" + precioUnidad +
                ", stock=" + unidadesEnExistencia +
                ", disp=" + disponible +
                ", repo=" + necesitaReposicion + '}';
    }
}