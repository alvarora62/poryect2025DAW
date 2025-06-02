import { Container, Table, Button, Form, Badge, Spinner } from 'react-bootstrap';
import styled from 'styled-components';
import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import RepartidorModal from '../components/RepartidorModal';
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

function RepartidorPage() {
  const [repartidores, setRepartidores] = useState([]);
  const [filter, setFilter] = useState('all');
  const [refreshData, setRefreshData] = useState(false);
  const [updatingDni, setUpdatingDni] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [selectedRepartidor, setSelectedRepartidor] = useState(null);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [newRepartidor, setNewRepartidor] = useState({
    nombre: '',
    dni: '',
    telefono: '',
    email: '',
    nombreEmpresa: ''
  });
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRepartidores = async () => {
      try {
        const url = `${config.apiBaseUrl}/api/repartidores?page=${page}&size=${size}`;
        const response = await fetch(url);
        const data = await response.json();

        let filteredData = data.content;
        if (filter === 'active') {
          filteredData = data.content.filter(rep => rep.active);
        } else if (filter === 'inactive') {
          filteredData = data.content.filter(rep => !rep.active);
        }

        setRepartidores(filteredData);
        setTotalPages(data.totalPages);
      } catch (error) {
        console.error('Error fetching repartidores:', error);
      }
    };

    fetchRepartidores();
  }, [filter, refreshData, page, size]);

  const handleStatusChange = async (dni, currentStatus) => {
    setUpdatingDni(dni);
    try {
      const response = await fetch(
        `${config.apiBaseUrl}/api/repartidores/status?dni=${dni}&active=${!currentStatus}`,
        { method: 'PATCH' }
      );
      if (response.ok) {
        setRefreshData(prev => !prev);
      } else {
        const errorText = await response.text();
        console.error('Error actualizando estado:', errorText);
      }
    } catch (error) {
      console.error('Error actualizando estado del repartidor:', error);
    } finally {
      setUpdatingDni(null);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewRepartidor(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const openCreateModal = () => {
    setNewRepartidor({
      nombre: '',
      dni: '',
      telefono: '',
      email: '',
      nombreEmpresa: ''
    });
    setEditMode(false);
    setSelectedRepartidor(null);
    setError('');
    setShowModal(true);
  };

  const openEditModal = (repartidor) => {
    setNewRepartidor({
      id: repartidor.id,
      nombre: repartidor.nombre,
      dni: repartidor.dni,
      telefono: repartidor.telefono,
      email: repartidor.email,
      nombreEmpresa: repartidor.nombreEmpresa
    });
    setSelectedRepartidor(repartidor);
    setEditMode(true);
    setError('');
    setShowModal(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      let url = `${config.apiBaseUrl}/api/repartidores`;
      if (editMode) {
        url = `${config.apiBaseUrl}/api/repartidores/${selectedRepartidor.dni}`;
      }

      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newRepartidor)
      });

      if (response.ok) {
        setShowModal(false);
        setEditMode(false);
        setSelectedRepartidor(null);
        setNewRepartidor({
          nombre: '',
          dni: '',
          telefono: '',
          email: '',
          nombreEmpresa: ''
        });
        setRefreshData(prev => !prev);
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'Error al guardar repartidor');
      }
    } catch (error) {
      console.error('Error guardando repartidor:', error);
      setError('Error al guardar repartidor');
    }
  };

  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem', paddingLeft: '5%', paddingRight: '5%' }}>
          <h2>Lista de Repartidores</h2>
          <Button variant="success" onClick={openCreateModal}>
            Añadir Repartidor
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
              <th>Empresa</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {repartidores.map((rep) => {
              const isUpdating = updatingDni === rep.dni;
              const active = rep.active;

              return (
                <tr key={rep.dni}>
                  <td>
                    <Badge bg={active ? 'success' : 'secondary'}>
                      {active ? 'Activo' : 'Inactivo'}
                    </Badge>
                  </td>
                  <td>{rep.dni}</td>
                  <td>{rep.nombre}</td>
                  <td>{rep.email}</td>
                  <td>{rep.telefono}</td>
                  <td>{rep.nombreEmpresa}</td>
                  <td>
                    <Button
                      variant="primary"
                      size="sm"
                      style={{ marginRight: '10px' }}
                      onClick={() => openEditModal(rep)}
                    >
                      Actualizar
                    </Button>
                    <Button
                      variant={active ? 'danger' : 'success'}
                      size="sm"
                      disabled={isUpdating}
                      onClick={() => handleStatusChange(rep.dni, active)}
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

      <RepartidorModal
        show={showModal}
        onHide={() => {
          setShowModal(false);
          setEditMode(false);
          setSelectedRepartidor(null);
          setNewRepartidor({ nombre: '', dni: '', telefono: '', email: '', nombreEmpresa: '' });
          setError('');
        }}
        repartidor={newRepartidor}
        onChange={handleInputChange}
        onSubmit={handleSubmit}
        error={error}
        editMode={editMode}
      />

      <CustomFooter />
    </div>
  );
}

export default RepartidorPage;
