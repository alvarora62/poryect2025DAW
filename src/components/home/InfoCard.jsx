import React, { useEffect, useState } from 'react';
import { Card, Table, Button, Spinner } from 'react-bootstrap';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import config from '../../config.json';

const StyledCard = styled(Card)`
  margin-bottom: 2rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
`;

const CardTitle = styled(Card.Title)`
  font-size: 1.25rem;
  font-weight: 600;
  color: #6a0dad;
`;

const StyledTable = styled(Table)`
  margin: 0;
`;

const FooterButton = styled(Button)`
  margin-top: 1rem;
`;

const InfoCard = ({ title, type }) => {
  const [empleados, setEmpleados] = useState([]);
  const [loading, setLoading] = useState(false);
  const [pedidos, setPedidos] = useState([]);
  const [repartidores, setRepartidores] = useState([]);
  const [auditorias, setAuditorias] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
  const fetchEmpleados = async () => {
    setLoading(true);
    try {
      const url = `${config.apiBaseUrl}/api/empleados?page=0&size=2`;
      const response = await fetch(url);
      const data = await response.json();
      setEmpleados(data.content || []);
    } catch (error) {
      console.error('Error fetching empleados:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchPedidos = async () => {
    setLoading(true);
    try {
      const url = `${config.apiBaseUrl}/api/pedidos?page=0&size=2`;
      const response = await fetch(url);
      const data = await response.json();
      setPedidos(data.content || []);
    } catch (error) {
      console.error('Error fetching pedidos:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchRepartidores = async () => {
    setLoading(true);
    try {
      const url = `${config.apiBaseUrl}/api/repartidores?page=0&size=2`;
      const response = await fetch(url);
      const data = await response.json();
      setRepartidores(data.content || []);
    } catch (error) {
      console.error('Error fetching repartidores:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchAuditorias = async () => {
    setLoading(true);
    try {
      const url = `${config.apiBaseUrl}/api/auditoria/historial?page=0&size=2`;
      const response = await fetch(url);
      const data = await response.json();
      setAuditorias(data.content || []);
    } catch (error) {
      console.error('Error fetching auditorias:', error);
    } finally {
      setLoading(false);
    }
  };

  if (type === 'Empleado') {
    fetchEmpleados();
  } else if (type === 'Pedido') {
    fetchPedidos();
  } else if (type === 'Repartidor') {
    fetchRepartidores();
  } else if (type === 'Auditoria') {
    fetchAuditorias();
  }
}, [type]);


  const renderTableContent = () => {
    if (loading) {
      return (
        <tbody>
          <tr>
            <td colSpan="3" style={{ textAlign: 'center' }}>
              <Spinner animation="border" size="sm" /> Cargando...
            </td>
          </tr>
        </tbody>
      );
    }

    switch (type) {
      case 'Empleado':
        return (
          <>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>Teléfono</th>
              </tr>
            </thead>
            <tbody>
              {empleados.map(emp => (
                <tr key={emp.dni}>
                  <td>{emp.nombre}</td>
                  <td>{emp.email}</td>
                  <td>{emp.telefono}</td>
                </tr>
              ))}
            </tbody>
          </>
        );
      case 'Repartidor':
        return (
          <>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Teléfono</th>
                <th>Empresa</th>
              </tr>
            </thead>
            <tbody>
              {repartidores.map(rep => (
                <tr key={rep.id}>
                  <td>{rep.nombre}</td>
                  <td>{rep.telefono}</td>
                  <td>{rep.nombreEmpresa}</td>
                </tr>
              ))}
            </tbody>
          </>
        );
      case 'Pedido':
        return (
            <>
            <thead>
                <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Estado</th>
                <th>Entrega</th>
                </tr>
            </thead>
            <tbody>
                {pedidos.map(pedido => (
                <tr key={pedido.id}>
                    <td>{pedido.id}</td>
                    <td>{pedido.producto?.nombre || 'Sin producto'}</td>
                    <td>{pedido.estado?.nombre || 'Desconocido'}</td>
                    <td>{pedido.fechaEntrega || '-'}</td>
                </tr>
                ))}
            </tbody>
            </>
        );

      case 'Auditoria':
        return (
          <>
            <thead>
              <tr>
                <th>Usuario</th>
                <th>Acción</th>
                <th>Fecha</th>
              </tr>
            </thead>
            <tbody>
              {auditorias.map(audit => (
                <tr key={audit.id}>
                  <td>{audit.username}</td>
                  <td>{audit.accion}</td>
                  <td>{audit.fecha}</td>
                </tr>
              ))}
            </tbody>
          </>
        );
      default:
        return null;
    }
  };

  const getNavigationPath = () => {
    switch (type) {
      case 'Empleado':
        return '/empleados';
      case 'Repartidor':
        return '/repartidores';
      case 'Pedido':
        return '/pedido';
      case 'Auditoria':
        return '/auditoria';
      default:
        return '/';
    }
  };

  return (
    <StyledCard>
      <Card.Body>
        <CardTitle>{title}</CardTitle>
        <StyledTable striped bordered hover size="sm">
          {renderTableContent()}
        </StyledTable>
        <FooterButton variant="primary" onClick={() => navigate(getNavigationPath())}>
          Ver {title.toLowerCase()}
        </FooterButton>
      </Card.Body>
    </StyledCard>
  );
};

export default InfoCard;