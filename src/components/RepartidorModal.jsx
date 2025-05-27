import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

function RepartidorModal({
  show,
  onHide,
  repartidor,
  onChange,
  onSubmit,
  error,
  editMode
}) {
  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>{editMode ? 'Actualizar Repartidor' : 'Añadir Nuevo Repartidor'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && <div className="alert alert-danger">{error}</div>}
        <Form onSubmit={onSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Nombre</Form.Label>
            <Form.Control
              type="text"
              name="nombre"
              value={repartidor.nombre}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>DNI</Form.Label>
            <Form.Control
              type="text"
              name="dni"
              value={repartidor.dni}
              onChange={onChange}
              required
              disabled={editMode}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Teléfono</Form.Label>
            <Form.Control
              type="tel"
              name="telefono"
              value={repartidor.telefono}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              name="email"
              value={repartidor.email}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Nombre de la Empresa</Form.Label>
            <Form.Control
              type="text"
              name="nombreEmpresa"
              value={repartidor.nombreEmpresa}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            {editMode ? 'Actualizar' : 'Guardar'}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
}

export default RepartidorModal;
