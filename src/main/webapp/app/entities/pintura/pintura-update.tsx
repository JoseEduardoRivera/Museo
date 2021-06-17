import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IObjArte } from 'app/shared/model/obj-arte.model';
import { getEntities as getObjArtes } from 'app/entities/obj-arte/obj-arte.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pintura.reducer';
import { IPintura } from 'app/shared/model/pintura.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPinturaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PinturaUpdate = (props: IPinturaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { pinturaEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pintura' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getObjArtes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pinturaEntity,
        ...values,
        objArte: objArtes.find(it => it.id.toString() === values.objArteId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="museoApp.pintura.home.createOrEditLabel" data-cy="PinturaCreateUpdateHeading">
            <Translate contentKey="museoApp.pintura.home.createOrEditLabel">Create or edit a Pintura</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pinturaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pintura-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pintura-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="pintura-objArte">
                  <Translate contentKey="museoApp.pintura.objArte">Obj Arte</Translate>
                </Label>
                <AvInput id="pintura-objArte" data-cy="objArte" type="select" className="form-control" name="objArteId">
                  <option value="" key="0" />
                  {objArtes
                    ? objArtes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idObjArt}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="tipoPinturaLabel" for="pintura-tipoPintura">
                  <Translate contentKey="museoApp.pintura.tipoPintura">Tipo Pintura</Translate>
                </Label>
                <AvField
                  id="pintura-tipoPintura"
                  data-cy="tipoPintura"
                  type="text"
                  name="tipoPintura"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="materialPinturaLabel" for="pintura-materialPintura">
                  <Translate contentKey="museoApp.pintura.materialPintura">Material Pintura</Translate>
                </Label>
                <AvField
                  id="pintura-materialPintura"
                  data-cy="materialPintura"
                  type="text"
                  name="materialPintura"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estiloPintLabel" for="pintura-estiloPint">
                  <Translate contentKey="museoApp.pintura.estiloPint">Estilo Pint</Translate>
                </Label>
                <AvField
                  id="pintura-estiloPint"
                  data-cy="estiloPint"
                  type="text"
                  name="estiloPint"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pintura" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  objArtes: storeState.objArte.entities,
  pinturaEntity: storeState.pintura.entity,
  loading: storeState.pintura.loading,
  updating: storeState.pintura.updating,
  updateSuccess: storeState.pintura.updateSuccess,
});

const mapDispatchToProps = {
  getObjArtes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PinturaUpdate);
