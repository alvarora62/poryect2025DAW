import { Container, Form, Button, Alert, Card, Row, Col } from 'react-bootstrap';
import styled from 'styled-components';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SimpleNavbar from '../components/generic/SimpleNavbar';
import CustomFooter from '../components/generic/Footer';
import config from '../config.json';

const StyledContainer = styled(Container)`
  margin-top: 4rem;
  margin-bottom: 4rem;
`;

const CenteredCard = styled(Card)`
  padding: 2rem;
  background-color: #ffffff;
  color: #000000;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

function AuthForm() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`${config.apiBaseUrl}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      if (response.ok) {
        const data = await response.json();
        setError(false);
        setMessage('Inicio de sesión exitoso');
        console.log('Usuario:', data);
        navigate('/home');
      } else {
        const errorText = await response.text();
        setError(true);
        setMessage(errorText);
      }
    } catch (err) {
      setError(true);
      setMessage('Error al conectar con el servidor');
    }
  };

  return (
    <div>
      <SimpleNavbar />
      <StyledContainer>
        <Row className="justify-content-md-center">
          <Col md={6}>
            <CenteredCard>
              <h3 className="mb-4 text-center">Iniciar Sesión</h3>
              {message && <Alert variant={error ? 'danger' : 'success'}>{message}</Alert>}
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="username">
                  <Form.Label>Nombre de usuario</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Ingrese su nombre de usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password">
                  <Form.Label>Contraseña</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Ingrese su contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </Form.Group>
                <Button variant="primary" type="submit" className="w-100">
                  Iniciar Sesión
                </Button>
              </Form>
            </CenteredCard>
          </Col>
        </Row>
      </StyledContainer>
      <CustomFooter />
    </div>
  );
}

export default AuthForm;
