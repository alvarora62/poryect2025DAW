import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Alert, Card, Row, Col } from 'react-bootstrap';
import styled from 'styled-components';
import config from '../config.json';

const StyledContainer = styled(Container)`
  background-color: ${(props) => props.bgColor || '#f8f9fa'};
  min-height: 100vh;
  padding-top: 5rem;
`;

const StyledCard = styled(Card)`
  background-color: ${(props) => props.secondaryColor || '#ffffff'};
  color: ${(props) => props.mainColor || '#000000'};
  padding: 2rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
`;

const AuthForm = () => {
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
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });
  
      if (response.ok) {
        const data = await response.json(); // Only parse as JSON on success
        setError(false);
        setMessage('Inicio de sesión exitoso');
        console.log('Usuario:', data);
        navigate('/home');
      } else {
        const errorText = await response.text(); // Handle plain text errors
        setError(true);
        setMessage(errorText);
      }
    } catch (err) {
      setError(true);
      setMessage('Error al conectar con el servidor');
    }
  };

  return (
    <StyledContainer bgColor="#f0f2f5">
      <Row className="justify-content-md-center">
        <Col md={6}>
          <StyledCard mainColor="#0d6efd" secondaryColor="#ffffff">
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
              <Button variant="primary" type="submit" className="w-100 mb-2">
                Iniciar Sesión
              </Button>
            </Form>
          </StyledCard>
        </Col>
      </Row>
    </StyledContainer>
  );
};

export default AuthForm;