import { Container, Table, Spinner, Pagination } from 'react-bootstrap';
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

function AuditoriaPage() {
  const [auditoria, setAuditoria] = useState([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const fetchAuditoria = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:8080/api/auditoria/historial?page=${page}&size=10&sort=fecha,desc`);
        const data = await response.json();
        setAuditoria(Array.isArray(data.content) ? data.content : []);
        setTotalPages(data.totalPages || 0);
      } catch (error) {
        console.error('Error fetching auditoría:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchAuditoria();
  }, [page]);

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  const renderPagination = () => (
    <Pagination className="justify-content-center mt-3">
      <Pagination.Prev onClick={() => handlePageChange(page - 1)} disabled={page === 0} />
      {[...Array(totalPages)].map((_, idx) => (
        <Pagination.Item key={idx} active={idx === page} onClick={() => handlePageChange(idx)}>
          {idx + 1}
        </Pagination.Item>
      ))}
      <Pagination.Next onClick={() => handlePageChange(page + 1)} disabled={page >= totalPages - 1} />
    </Pagination>
  );

  return (
    <div>
      <CustomNavbar />
      <StyledContainer>
        <h2 style={{ marginBottom: '2rem', paddingLeft: '5%' }}>Historial de Auditoría</h2>
        {loading ? (
          <div className="text-center">
            <Spinner animation="border" role="status" />
          </div>
        ) : (
          <>
            <CenteredTable bordered hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Usuario</th>
                  <th>Acción</th>
                  <th>Fecha</th>
                </tr>
              </thead>
              <tbody>
                {auditoria.length > 0 ? (
                  auditoria.map((entry) => (
                    <tr key={entry.id}>
                      <td>{entry.id}</td>
                      <td>{entry.username}</td>
                      <td>{entry.accion}</td>
                      <td>{entry.fecha}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4">No hay registros de auditoría.</td>
                  </tr>
                )}
              </tbody>
            </CenteredTable>
            {renderPagination()}
          </>
        )}
      </StyledContainer>
      <CustomFooter />
    </div>
  );
}

export default AuditoriaPage;
