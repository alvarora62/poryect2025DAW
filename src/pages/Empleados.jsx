import { Container, Table, Button, Form, Badge, Spinner } from 'react-bootstrap';
import styled from 'styled-components';
import CustomNavbar from '../components/Navbar';
import CustomFooter from '../components/Footer';
import { useState, useEffect } from 'react';

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

  useEffect(() => {
    const fetchEmpleados = async () => {
      try {
        let url = 'http://localhost:8080/api/empleados';
        if (filter === 'active') {
          url += '/active';
        } else if (filter === 'inactive') {
          url += '/inactive';
        }
        const response = await fetch(url);
        const data = await response.json();
        setEmpleados(Array.isArray(data.content) ? data.content : []);
      } catch (error) {
        console.error('Error fetching empleados:', error);
      }
    };

    fetchEmpleados();
  }, [filter, refreshData]);

  const handleStatusChange = async (empleadoDni, currentStatus) => {
  setUpdatingDni(empleadoDni);
  try {
    const response = await fetch(
      `http://localhost:8080/api/empleados/status?dniEmpleado=${empleadoDni}&isActive=${!currentStatus}`,
      { method: 'PATCH' }
    );
    if (response.ok) {
      console.log(`Empleado ${empleadoDni} actualizado exitosamente`);
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


  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <h2 style={{ marginBottom: '2rem', paddingLeft: '5%' }}>Lista de Empleados</h2>
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
              <th>ID</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {empleados.map((empleado) => {
              const isUpdating = updatingDni === empleado.dni;
              const isActive = empleado.isActive;

              return (
                <tr key={empleado.dni}>
                  <td>
                    <Badge bg={isActive ? 'success' : 'secondary'}>
                      {isActive ? 'Activo' : 'Inactivo'}
                    </Badge>
                  </td>
                  <td>{empleado.dni}</td>
                  <td>{empleado.nombre}</td>
                  <td>{empleado.email}</td>
                  <td>{empleado.telefono}</td>
                  <td>{empleado.idEmpleado}</td>
                  <td>
                    <Button variant="primary" size="sm" style={{ marginRight: '10px' }}>
                      Actualizar
                    </Button>
                    <Button
                      variant={isActive ? 'danger' : 'success'}
                      size="sm"
                      disabled={isUpdating}
                      onClick={() => handleStatusChange(empleado.dni, isActive)}
                    >
                      {isUpdating ? (
                        <>
                          <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                          {' '}Procesando...
                        </>
                      ) : (
                        isActive ? 'Desactivar' : 'Activar'
                      )}
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </CenteredTable>
      </StyledContainer>
      <CustomFooter />
    </div>
  );
}

export default EmpleadoPage;
