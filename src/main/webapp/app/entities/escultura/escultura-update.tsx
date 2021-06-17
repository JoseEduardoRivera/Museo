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
import { getEntity, updateEntity, createEntity, reset } from './escultura.reducer';
import { IEscultura } from 'app/shared/model/escultura.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEsculturaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EsculturaUpdate = (props: IEsculturaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { esculturaEntity, objArtes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/escultura' + props.location.search);
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
        ...esculturaEntity,
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
          <h2 id="museoApp.escultura.home.createOrEditLabel" data-cy="EsculturaCreateUpdateHeading">
            <Translate contentKey="museoApp.escultura.home.createOrEditLabel">Create or edit a Escultura</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : esculturaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="escultura-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="escultura-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="escultura-objArte">
                  <Translate contentKey="museoApp.escultura.objArte">Obj Arte</Translate>
                </Label>
                <AvInput id="escultura-objArte" data-cy="objArte" type="select" className="form-control" name="objArteId">
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
                <Label id="alturaLabel" for="escultura-altura">
                  <Translate contentKey="museoApp.escultura.altura">Altura</Translate>
                </Label>
                <AvField
                  id="escultura-altura"
                  data-cy="altura"
                  type="text"
                  name="altura"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="materialLabel" for="escultura-material">
                  <Translate contentKey="museoApp.escultura.material">Material</Translate>
                </Label>
                <AvField
                  id="escultura-material"
                  data-cy="material"
                  type="text"
                  name="material"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estiloLabel" for="escultura-estilo">
                  <Translate contentKey="museoApp.escultura.estilo">Estilo</Translate>
                </Label>
                <AvField
                  id="escultura-estilo"
                  data-cy="estilo"
                  type="text"
                  name="estilo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="pesoLabel" for="escultura-peso">
                  <Translate contentKey="museoApp.escultura.peso">Peso</Translate>
                </Label>
                <AvField
                  id="escultura-peso"
                  data-cy="peso"
                  type="text"
                  name="peso"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/escultura" replace color="info">
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
  esculturaEntity: storeState.escultura.entity,
  loading: storeState.escultura.loading,
  updating: storeState.escultura.updating,
  updateSuccess: storeState.escultura.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EsculturaUpdate);
