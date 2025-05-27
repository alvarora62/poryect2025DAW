import React from 'react';
import { Table } from 'react-bootstrap';
import styled from 'styled-components';

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

function AuditoriaTable({ data }) {
  return (
    <CenteredTable striped bordered hover>
      <thead>
        <tr>
          <th>Usuario</th>
          <th>Acción</th>
          <th>Fecha</th>
        </tr>
      </thead>
      <tbody>
        {data.map((registro, index) => (
          <tr key={index}>
            <td>{registro.username}</td>
            <td>{registro.accion}</td>
            <td>{registro.fecha}</td>
          </tr>
        ))}
      </tbody>
    </CenteredTable>
  );
}

export default AuditoriaTable;