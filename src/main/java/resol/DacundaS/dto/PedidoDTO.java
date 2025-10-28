package resol.DacundaS.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {
    private Integer idPedido;
    private Integer idCliente;
    private Integer idEmpleado;
    private LocalDate fechaPedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private String estado;              // "Pendiente" | "Enviado" | "Entregado"
    private BigDecimal montoTotal;

    // Renglones del pedido (para insert/update)
    private final List<Detalle> detalles = new ArrayList<>();

    // ===== Detalle =====
    public static class Detalle {
        private Integer idProducto;
        private Integer cantidad;
        private BigDecimal precioUnidad;
        private BigDecimal descuento;   // 0..1, por ej 0.15

        public Integer getIdProducto()        { return idProducto; }
        public void setIdProducto(Integer v)  { this.idProducto = v; }
        public Integer getCantidad()          { return cantidad; }
        public void setCantidad(Integer v)    { this.cantidad = v; }
        public BigDecimal getPrecioUnidad()   { return precioUnidad; }
        public void setPrecioUnidad(BigDecimal v){ this.precioUnidad = v; }
        public BigDecimal getDescuento()      { return descuento; }
        public void setDescuento(BigDecimal v){ this.descuento = v; }
    }

    // ===== getters / setters =====
    public Integer getIdPedido()                  { return idPedido; }
    public void setIdPedido(Integer idPedido)     { this.idPedido = idPedido; }
    public Integer getIdCliente()                 { return idCliente; }
    public void setIdCliente(Integer idCliente)   { this.idCliente = idCliente; }
    public Integer getIdEmpleado()                { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }
    public LocalDate getFechaPedido()             { return fechaPedido; }
    public void setFechaPedido(LocalDate v)       { this.fechaPedido = v; }
    public LocalDate getFechaEnvio()              { return fechaEnvio; }
    public void setFechaEnvio(LocalDate v)        { this.fechaEnvio = v; }
    public LocalDate getFechaEntrega()            { return fechaEntrega; }
    public void setFechaEntrega(LocalDate v)      { this.fechaEntrega = v; }
    public String getEstado()                     { return estado; }
    public void setEstado(String estado)          { this.estado = estado; }
    public BigDecimal getMontoTotal()             { return montoTotal; }
    public void setMontoTotal(BigDecimal v)       { this.montoTotal = v; }

    public List<Detalle> getDetallesParaInsert()  { return detalles; }
    public void addDetalle(Detalle d)             { this.detalles.add(d); }
}