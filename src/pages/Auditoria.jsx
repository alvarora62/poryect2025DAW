import React, { useState, useEffect } from 'react';
import { Container, Table, Button } from 'react-bootstrap';
import styled from 'styled-components';
import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import AuditoriaModal from '../components/AuditoriaModal';
import AuditoriaTable from '../components/AuditoriaTable';
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

function AuditoriaPage() {
  const [historial, setHistorial] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [nuevoRegistro, setNuevoRegistro] = useState({
    username: '',
    accion: ''
  });
  const [error, setError] = useState('');

  const fetchHistorial = async () => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/api/auditoria/historial`);
      if (!response.ok) throw new Error('Error al obtener historial');
      const data = await response.json();
      setHistorial(data.content);
    } catch (err) {
      console.error(err);
      setError('No se pudo cargar el historial');
    }
  };

  useEffect(() => {
    fetchHistorial();
  }, []);

  const handleChange = (e) => {
    setNuevoRegistro({ ...nuevoRegistro, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await fetch(`${config.apiBaseUrl}/api/auditoria/anotar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(nuevoRegistro),
      });

      if (!response.ok) throw new Error('Error al registrar acción');

      setShowModal(false);
      setNuevoRegistro({ username: '', accion: '' });
      fetchHistorial();
    } catch (err) {
      console.error(err);
      setError('No se pudo registrar la acción');
    }
  };

  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem', paddingLeft: '5%', paddingRight: '5%' }}>
          <h2>Historial de Auditoría</h2>
          <Button variant="success" onClick={() => setShowModal(true)}>
            Registrar nueva acción
          </Button>
        </div>
        {error && <div className="alert alert-danger">{error}</div>}
        <AuditoriaTable data={historial} />
        <AuditoriaModal
          show={showModal}
          onHide={() => setShowModal(false)}
          auditoria={nuevoRegistro}
          onChange={handleChange}
          onSubmit={handleSubmit}
          error={error}
        />
      </StyledContainer>
      <CustomFooter />
    </div>
  );
}

export default AuditoriaPage;