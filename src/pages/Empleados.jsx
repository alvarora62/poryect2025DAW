import { Container, Table, Button, Form, Badge, Spinner } from 'react-bootstrap';
import styled from 'styled-components';
import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import EmpleadoModal from '../components/EmpleadoModal';
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

function EmpleadoPage() {
  const [empleados, setEmpleados] = useState([]);
  const [filter, setFilter] = useState('all');
  const [refreshData, setRefreshData] = useState(false);
  const [updatingDni, setUpdatingDni] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [selectedEmpleado, setSelectedEmpleado] = useState(null);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [newEmpleado, setNewEmpleado] = useState({
    nombre: '',
    dni: '',
    telefono: '',
    email: ''
  });
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchEmpleados = async () => {
      try {
        const url = `${config.apiBaseUrl}/api/empleados?page=${page}&size=${size}`;
        const response = await fetch(url);
        const data = await response.json();

        let filteredData = data.content;
        if (filter === 'active') {
          filteredData = data.content.filter(empleado => empleado.active);
        } else if (filter === 'inactive') {
          filteredData = data.content.filter(empleado => !empleado.active);
        }

        setEmpleados(filteredData);
        setTotalPages(data.totalPages);
      } catch (error) {
        console.error('Error fetching empleados:', error);
      }
    };

    fetchEmpleados();
  }, [filter, refreshData, page, size]);

  const handleStatusChange = async (empleadoDni, currentStatus) => {
    setUpdatingDni(empleadoDni);
    try {
      const response = await fetch(
        `${config.apiBaseUrl}/api/empleados/status?dniEmpleado=${empleadoDni}&active=${!currentStatus}`,
        { method: 'PATCH' }
      );
      if (response.ok) {
        setRefreshData(prev => !prev);
      } else {
        const errorText = await response.text();
        console.error('Error actualizando estado:', errorText);
      }
    } catch (error) {
      console.error('Error actualizando estado del empleado:', error);
    } finally {
      setUpdatingDni(null);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewEmpleado(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const openCreateModal = () => {
    setNewEmpleado({
      nombre: '',
      dni: '',
      telefono: '',
      email: ''
    });
    setEditMode(false);
    setSelectedEmpleado(null);
    setError('');
    setShowModal(true);
  };

  const openEditModal = (empleado) => {
    setNewEmpleado({
      id: empleado.id,
      nombre: empleado.nombre,
      dni: empleado.dni,
      telefono: empleado.telefono,
      email: empleado.email
    });
    setSelectedEmpleado(empleado);
    setEditMode(true);
    setError('');
    setShowModal(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      let url = `${config.apiBaseUrl}/api/empleados`;
      if (editMode) {
        url = `${config.apiBaseUrl}/api/empleados/${selectedEmpleado.dni}`;
      }
      
      const response = await fetch(url, {
        method: editMode ? 'POST' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newEmpleado)
      });

      if (response.ok) {
        setShowModal(false);
        setEditMode(false);
        setSelectedEmpleado(null);
        setNewEmpleado({
          id: '',
          nombre: '',
          dni: '',
          telefono: '',
          email: ''
        });
        setRefreshData(prev => !prev);
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'Error al guardar empleado');
      }
    } catch (error) {
      console.error('Error guardando empleado:', error);
      setError('Error al guardar empleado');
    }
  };

  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem', paddingLeft: '5%', paddingRight: '5%' }}>
          <h2>Lista de Empleados</h2>
          <Button variant="success" onClick={openCreateModal}>
            Añadir Empleado
          </Button>
        </div>
        <div style={{ marginBottom: '1rem', paddingLeft: '5%' }}>
          <Form.Select value={filter} onChange={(e) => setFilter(e.target.value)} style={{ width: '200px' }}>
            <option value="all">Todos</option>
            <option value="active">Activos</option>
            <option value="inactive">Inactivos</option>
          </Form.Select>
        </div>
        <CenteredTable bordered hover>
          <thead>
            <tr>
              <th>Estado</th>
              <th>DNI</th>
              <th>Nombre</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {empleados.map((empleado) => {
              const isUpdating = updatingDni === empleado.dni;
              const active = empleado.active;

              return (
                <tr key={empleado.dni}>
                  <td>
                    <Badge bg={active ? 'success' : 'secondary'}>
                      {active ? 'Activo' : 'Inactivo'}
                    </Badge>
                  </td>
                  <td>{empleado.dni}</td>
                  <td>{empleado.nombre}</td>
                  <td>{empleado.email}</td>
                  <td>{empleado.telefono}</td>
                  <td>
                    <Button
                      variant="primary"
                      size="sm"
                      style={{ marginRight: '10px' }}
                      onClick={() => openEditModal(empleado)}
                    >
                      Actualizar
                    </Button>
                    <Button
                      variant={active ? 'danger' : 'success'}
                      size="sm"
                      disabled={isUpdating}
                      onClick={() => handleStatusChange(empleado.dni, active)}
                    >
                      {isUpdating ? (
                        <>
                          <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                          {' '}Procesando...
                        </>
                      ) : (
                        active ? 'Desactivar' : 'Activar'
                      )}
                    </Button>
                  </td>
                </tr>
              );
            })}
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

      <EmpleadoModal
        show={showModal}
        onHide={() => {
          setShowModal(false);
          setEditMode(false);
          setSelectedEmpleado(null);
          setNewEmpleado({ nombre: '', dni: '', telefono: '', email: '' });
          setError('');
        }}
        empleado={newEmpleado}
        onChange={handleInputChange}
        onSubmit={handleSubmit}
        error={error}
        editMode={editMode}
      />

      <CustomFooter />
    </div>
  );
}

export default EmpleadoPage;