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
import { getEntity, updateEntity, createEntity, reset } from './otro-obj.reducer';
import { IOtroObj } from 'app/shared/model/otro-obj.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOtroObjUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OtroObjUpdate = (props: IOtroObjUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { otroObjEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/otro-obj' + props.location.search);
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
        ...otroObjEntity,
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
          <h2 id="museoApp.otroObj.home.createOrEditLabel" data-cy="OtroObjCreateUpdateHeading">
            <Translate contentKey="museoApp.otroObj.home.createOrEditLabel">Create or edit a OtroObj</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : otroObjEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="otro-obj-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="otro-obj-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="otro-obj-objArte">
                  <Translate contentKey="museoApp.otroObj.objArte">Obj Arte</Translate>
                </Label>
                <AvInput id="otro-obj-objArte" data-cy="objArte" type="select" className="form-control" name="objArteId">
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
                <Label id="tipoLabel" for="otro-obj-tipo">
                  <Translate contentKey="museoApp.otroObj.tipo">Tipo</Translate>
                </Label>
                <AvField
                  id="otro-obj-tipo"
                  data-cy="tipo"
                  type="text"
                  name="tipo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estiloLabel" for="otro-obj-estilo">
                  <Translate contentKey="museoApp.otroObj.estilo">Estilo</Translate>
                </Label>
                <AvField
                  id="otro-obj-estilo"
                  data-cy="estilo"
                  type="text"
                  name="estilo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/otro-obj" replace color="info">
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
  otroObjEntity: storeState.otroObj.entity,
  loading: storeState.otroObj.loading,
  updating: storeState.otroObj.updating,
  updateSuccess: storeState.otroObj.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(OtroObjUpdate);
