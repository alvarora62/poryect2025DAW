import React, { useEffect, useState } from 'react';
import { Table, Button, Modal, Form, Pagination, Nav, Container } from 'react-bootstrap';
import styled from 'styled-components';

const Title = styled.h2`
  margin: 20px 0;
  color: purple;
`;

const EmpleadoPage = () => {
  const [empleados, setEmpleados] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [filter, setFilter] = useState('all'); // 'all', 'active', 'inactive'

  const [showEditModal, setShowEditModal] = useState(false);
  const [showStatusModal, setShowStatusModal] = useState(false);

  const [selectedEmpleado, setSelectedEmpleado] = useState(null);
  const [empleadoData, setEmpleadoData] = useState({ nombre: '', puesto: '', activo: true });

  // Fetch empleados with filter and page
  const fetchEmpleados = async () => {
    let url = `/api/empleados`;
    if (filter === 'active') url += '/active';
    else if (filter === 'inactive') url += '/inactive';

    url += `?page=${page}&size=10`;

    const res = await fetch(url);
    const data = await res.json();
    setEmpleados(data.content);
    setTotalPages(data.totalPages);
  };

  useEffect(() => {
    fetchEmpleados();
  }, [filter, page]);

  // Open create/edit modal
  const handleEditModalOpen = (empleado = null) => {
    if (empleado) {
      setEmpleadoData(empleado);
      setSelectedEmpleado(empleado);
    } else {
      setEmpleadoData({ nombre: '', puesto: '', activo: true });
      setSelectedEmpleado(null);
    }
    setShowEditModal(true);
  };

  // Submit create/update empleado
  const handleSaveEmpleado = async () => {
    await fetch('/api/empleados', {
      method: selectedEmpleado ? 'PUT' : 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(empleadoData),
    });
    setShowEditModal(false);
    fetchEmpleados();
  };

  // Open status modal
  const handleStatusModalOpen = (empleado) => {
    setSelectedEmpleado(empleado);
    setShowStatusModal(true);
  };

  // Change active status
  const handleChangeStatus = async (isActive) => {
    await fetch(`/api/empleados/status?isActive=${isActive}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(selectedEmpleado),
    });
    setShowStatusModal(false);
    fetchEmpleados();
  };

  return (
    <Container>
      <Title>Empleados</Title>

      <Nav variant="tabs" activeKey={filter} onSelect={(k) => { setFilter(k); setPage(0); }}>
        <Nav.Item>
          <Nav.Link eventKey="all">Todos</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="active">Activos</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="inactive">Inactivos</Nav.Link>
        </Nav.Item>
      </Nav>

      <Button className="my-3" onClick={() => handleEditModalOpen(null)}>Nuevo Empleado</Button>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Puesto</th>
            <th>Activo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {empleados.map((emp) => (
            <tr key={emp.id}>
              <td>{emp.nombre}</td>
              <td>{emp.puesto}</td>
              <td>{emp.activo ? 'Sí' : 'No'}</td>
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  onClick={() => handleEditModalOpen(emp)}
                  className="me-2"
                >
                  Editar
                </Button>
                <Button
                  variant={emp.activo ? 'secondary' : 'success'}
                  size="sm"
                  onClick={() => handleStatusModalOpen(emp)}
                >
                  {emp.activo ? 'Desactivar' : 'Activar'}
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Pagination */}
      <Pagination>
        <Pagination.Prev disabled={page === 0} onClick={() => setPage((p) => p - 1)} />
        {[...Array(totalPages).keys()].map((p) => (
          <Pagination.Item key={p} active={p === page} onClick={() => setPage(p)}>
            {p + 1}
          </Pagination.Item>
        ))}
        <Pagination.Next disabled={page === totalPages - 1} onClick={() => setPage((p) => p + 1)} />
      </Pagination>

      {/* Edit Modal */}
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{selectedEmpleado ? 'Editar Empleado' : 'Nuevo Empleado'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="nombre" className="mb-3">
              <Form.Label>Nombre</Form.Label>
              <Form.Control
                type="text"
                value={empleadoData.nombre}
                onChange={(e) => setEmpleadoData({ ...empleadoData, nombre: e.target.value })}
              />
            </Form.Group>
            <Form.Group controlId="puesto" className="mb-3">
              <Form.Label>Puesto</Form.Label>
              <Form.Control
                type="text"
                value={empleadoData.puesto}
                onChange={(e) => setEmpleadoData({ ...empleadoData, puesto: e.target.value })}
              />
            </Form.Group>
            {/* You can add more fields if your Empleado entity has more */}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowEditModal(false)}>
            Cancelar
          </Button>
          <Button variant="primary" onClick={handleSaveEmpleado}>
            Guardar
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Status Modal */}
      <Modal show={showStatusModal} onHide={() => setShowStatusModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Cambiar estado</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          ¿Desea {selectedEmpleado?.activo ? 'desactivar' : 'activar'} al empleado{' '}
          <strong>{selectedEmpleado?.nombre}</strong>?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowStatusModal(false)}>
            Cancelar
          </Button>
          <Button
            variant={selectedEmpleado?.activo ? 'danger' : 'success'}
            onClick={() => handleChangeStatus(!selectedEmpleado.activo)}
          >
            {selectedEmpleado?.activo ? 'Desactivar' : 'Activar'}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default EmpleadoPage;
