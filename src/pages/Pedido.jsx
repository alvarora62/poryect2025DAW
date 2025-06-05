import { Container, Table, Button, Badge, Modal, Form } from 'react-bootstrap';
import styled from 'styled-components';
import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import { useState, useEffect } from 'react';
import config from '../config.json';

const StyledContainer = styled(Container)`
  margin-top: 2rem;
  margin-bottom: 2rem;
`;

const CenteredTable = styled(Table)`
  margin: 0 auto;
  width: 90%;
  background-color: #f8f9fa;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  thead th {
    font-size: 1.2rem;
    text-align: center;
  }

  tbody td {
    text-align: center;
    vertical-align: middle;
  }

  tbody tr:nth-child(even) {
    background-color: #e9ecef;
  }

  tbody tr:nth-child(odd) {
    background-color: #ffffff;
  }
`;

function PedidoPage() {
  const [pedidos, setPedidos] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);

  const [showEmpleadoModal, setShowEmpleadoModal] = useState(false);
  const [showRepartidorModal, setShowRepartidorModal] = useState(false);
  const [empleados, setEmpleados] = useState([]);
  const [repartidores, setRepartidores] = useState([]);
  const [selectedPedidoId, setSelectedPedidoId] = useState(null);
  const [selectedEmpleadoId, setSelectedEmpleadoId] = useState('');
  const [selectedRepartidorNombre, setSelectedRepartidorNombre] = useState('');

  useEffect(() => {
    fetchPedidos();
  }, [page, size]);

  const fetchPedidos = async () => {
    try {
      const url = `${config.apiBaseUrl}/api/pedidos?page=${page}&size=${size}`;
      const response = await fetch(url);
      const data = await response.json();

      setPedidos(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error('Error fetching pedidos:', error);
    }
  };

  const openAsignarEmpleadoModal = async (pedidoId) => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/api/empleados/selectList`);
      const data = await response.json();
      setEmpleados(data);
      setSelectedPedidoId(pedidoId);
      setShowEmpleadoModal(true);
    } catch (error) {
      console.error('Error fetching empleados:', error);
    }
  };

  const openAsignarRepartidorModal = async (pedidoId) => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/api/repartidores/selectList`);
      const data = await response.json();
      setRepartidores(data);
      setSelectedPedidoId(pedidoId);
      setShowRepartidorModal(true);
    } catch (error) {
      console.error('Error fetching repartidores:', error);
    }
  };

  const handleAsignarEmpleado = async () => {
    try {
      const url = `${config.apiBaseUrl}/api/pedidos/${selectedPedidoId}/empleado/${selectedEmpleadoId}`;
      const response = await fetch(url, { method: 'PUT' });

      if (response.ok) {
        alert('Empleado asignado correctamente');
        setShowEmpleadoModal(false);
        setSelectedEmpleadoId('');
        fetchPedidos(); // Refresca pedidos
      } else {
        alert('Error al asignar empleado');
      }
    } catch (error) {
      console.error('Error asignando empleado:', error);
      alert('Error de conexión');
    }
  };

  const handleAsignarRepartidor = async () => {
  try {
    const url = `${config.apiBaseUrl}/api/pedidos/${selectedPedidoId}/repartidor/${selectedRepartidorNombre}`;
    const response = await fetch(url, { method: 'PUT' });

    if (response.ok) {
      alert('Repartidor asignado correctamente');
      setShowRepartidorModal(false);
      setSelectedRepartidorNombre('');
      fetchPedidos();
    } else {
      alert('Error al asignar repartidor');
    }
  } catch (error) {
    console.error('Error asignando repartidor:', error);
    alert('Error de conexión');
  }
};


  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <div style={{ paddingLeft: '5%', marginBottom: '2rem' }}>
          <h2>Lista de Pedidos</h2>
        </div>

        <CenteredTable bordered hover>
          <thead>
            <tr>
              <th>Dirección</th>
              <th>Estado</th>
              <th>Producto</th>
              <th>Cantidad</th>
              <th>Empleado</th>
              <th>Repartidor</th>
              <th>Fecha de Creación</th>
              <th>Fecha de Entrega</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {pedidos.map((pedido) => (
              <tr key={pedido.id}>
                <td>{pedido.direccion}</td>
                <td>
                  <Badge bg="danger">{pedido.estado?.nombre || 'Desconocido'}</Badge>
                </td>
                <td>{pedido.producto?.nombre || 'Sin producto'}</td>
                <td>{pedido.cantidad}</td>
                <td>{pedido.empleado?.nombre || 'No asignado'}</td>
                <td>{pedido.repartidor?.nombre || 'No asignado'}</td>
                <td>{pedido.fechaCreacion}</td>
                <td>{pedido.fechaEntrega}</td>
                <td>
                  {!pedido.empleado && (
                    <Button
                      variant="warning"
                      size="sm"
                      style={{ marginBottom: '5px' }}
                      onClick={() => openAsignarEmpleadoModal(pedido.id)}
                    >
                      Asignar Empleado
                    </Button>
                  )}
                  {!pedido.repartidor && (
                    <Button
                      variant="info"
                      size="sm"
                      onClick={() => openAsignarRepartidorModal(pedido.id)}
                    >
                      Asignar Repartidor
                    </Button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </CenteredTable>

        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '1rem' }}>
          <Button 
            variant="outline-primary" 
            onClick={() => setPage(prev => Math.max(0, prev - 1))}
            disabled={page === 0}
          >
            Anterior
          </Button>
          <span style={{ margin: '0 1rem' }}>
            Página {page + 1} de {totalPages}
          </span>
          <Button 
            variant="outline-primary" 
            onClick={() => setPage(prev => prev + 1)}
            disabled={page >= totalPages - 1}
          >
            Siguiente
          </Button>
        </div>
      </StyledContainer>
      <CustomFooter />

      {/* Modal Asignar Empleado */}
      <Modal show={showEmpleadoModal} onHide={() => setShowEmpleadoModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Asignar Empleado</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="empleadoSelect">
              <Form.Label>Selecciona un empleado:</Form.Label>
              <Form.Select
                value={selectedEmpleadoId}
                onChange={(e) => setSelectedEmpleadoId(e.target.value)}
              >
                <option value="">-- Selecciona --</option>
                {empleados.map((emp) => (
                  <option key={emp.id} value={emp.id}>
                    {emp.nombre}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowEmpleadoModal(false)}>
            Cancelar
          </Button>
          <Button variant="primary" onClick={handleAsignarEmpleado} disabled={!selectedEmpleadoId}>
            Asignar
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showRepartidorModal} onHide={() => setShowRepartidorModal(false)}>
  <Modal.Header closeButton>
    <Modal.Title>Asignar Repartidor</Modal.Title>
  </Modal.Header>
  <Modal.Body>
    <Form>
      <Form.Group controlId="repartidorSelect">
        <Form.Label>Selecciona una empresa repartidora:</Form.Label>
        <Form.Select
          value={selectedRepartidorNombre}
          onChange={(e) => setSelectedRepartidorNombre(e.target.value)}
        >
          <option value="">-- Selecciona --</option>
          {repartidores.map((nombreEmpresa) => (
            <option key={nombreEmpresa} value={nombreEmpresa}>
              {nombreEmpresa}
            </option>
          ))}
        </Form.Select>
      </Form.Group>
    </Form>
  </Modal.Body>
  <Modal.Footer>
    <Button variant="secondary" onClick={() => setShowRepartidorModal(false)}>
      Cancelar
    </Button>
    <Button
      variant="primary"
      onClick={handleAsignarRepartidor}
      disabled={!selectedRepartidorNombre}
    >
      Asignar
    </Button>
  </Modal.Footer>
</Modal>


    </div>
  );
}

export default PedidoPage;
