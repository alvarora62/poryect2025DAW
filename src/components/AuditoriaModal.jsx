import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

function AuditoriaModal({
  show,
  onHide,
  auditoria = {}, // Prevent undefined access
  onChange,
  onSubmit,
  error
}) {
  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Registrar Acción</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && <div className="alert alert-danger">{error}</div>}
        <Form onSubmit={onSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Nombre de Usuario</Form.Label>
            <Form.Control
              type="text"
              name="username"
              value={auditoria.username || ''}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Acción</Form.Label>
            <Form.Control
              type="text"
              name="accion"
              value={auditoria.accion || ''}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            Registrar
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
}

export default AuditoriaModal;
